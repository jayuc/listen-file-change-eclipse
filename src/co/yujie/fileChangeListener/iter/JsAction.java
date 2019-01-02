package co.yujie.fileChangeListener.iter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import co.yujie.fileChangeListener.model.FileChangeInfo;
import co.yujie.fileChangeListener.util.ConfigUtil;
import co.yujie.fileChangeListener.util.FileUtil;
import co.yujie.fileChangeListener.util.LogUtil;

/**
 * 当js变化时执行
 * @author yujie
 *
 */
public class JsAction implements IAction {
	
	private final static Logger log = LogUtil.getLog(JsAction.class);

	@Override
	public void execute(Object obj) {

		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) obj;
		FileChangeInfo fileChangeInfo = (FileChangeInfo) data.get("fileChangeInfo");
		
		DocumentOrganization doc = new DocumentOrganization(fileChangeInfo);
		String command = doc.createWebpackCommand();
		log.debug("webpack命令：" + command);
		
		String result = "编译成功！";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			result = "编译失败！";
		} finally {
			log.debug(result);
		}
		
		//处理收尾工作
		afterExecute(doc);
		
		/**
		 * 刷新文件
		 * 注意：eclipse以外的外部编辑器修改文件后不会使文件在eclipse中刷新
		 */
//		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(doc.getOutFile()));
//		try {
//			file.refreshLocal(IResource.DEPTH_ONE, null);
//		} catch (CoreException e) {
//			log.error("刷新文件失败");
//		}
		
	}
	
	/**
	 * 执行后序收尾任务
	 */
	private void afterExecute(DocumentOrganization doc) {
		File source = new File(doc.getAbsoluteOutFile());
		String srcPath = ConfigUtil.getWorkpath() + 
				".metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/" + 
				ConfigUtil.getRecentProjectName();
		System.out.println(srcPath);
		//复制文件到tomcat中 解决tomcat缓存问题
//		try {
//			FileUtil.copyFileUsingFileChannels(source, null);
//		} catch (IOException e) {
//			log.info("source:" + doc.getAbsoluteOutFile());
//			log.info("src:"+ srcPath);
//			log.warn("复制文件到tomcat中失败了");
//		}
	}

}
