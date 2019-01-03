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
		
		String result = "编译失败！";

		if(null != command) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(command);
				process.waitFor();
			} catch (IOException | InterruptedException e) {
				stopExecute();  //编译完成(退出此次编译任务)
				log.error("编译时抛出异常,自动结束本次编译任务");
			} finally {
				if(process.exitValue() == 0) {
					result = "编译成功！";
				}
				process.destroy();
				process = null;
				log.debug(result);
			}
			
			log.debug("开始执行收尾任务");
			//处理收尾工作  解决tomcat缓存问题
			afterExecute(doc);
		}else {
			stopExecute();  //编译完成(退出此次编译任务)
			log.debug("编译命令为null, " + result);
		}
		
		
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
		String srcPath = doc.getWebAbsoluteOutFile();
		String sourcePath = doc.getAbsoluteOutFile();
		if(null != srcPath && null != sourcePath) {
			File source = new File(sourcePath);
			File src = new File(srcPath);
			if(source.exists()) {
				//复制文件到tomcat中 解决tomcat缓存问题
				try {
					FileUtil.copyFileUsingFileStreams(source, src);
				} catch (IOException e) {
					stopExecute();  //编译完成(退出此次编译任务)
					log.info("sourcePath:" + doc.getAbsoluteOutFile());
					log.info("srcPath:"+ srcPath);
					log.error("复制文件到tomcat中失败了,自动结束此次任务");
				}
			}
		}else {
			log.debug("srcPath:" + srcPath);
			log.debug("sourcePath:" + sourcePath);
		}
	}
	
	/**
	 * 结束此次编译
	 */
	private void stopExecute() {
		Compiler.setStatus(Boolean.FALSE);
	}

}
