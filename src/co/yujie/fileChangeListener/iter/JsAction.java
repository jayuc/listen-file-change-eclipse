package co.yujie.fileChangeListener.iter;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import co.yujie.fileChangeListener.model.FileChangeInfo;
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
		
		/**
		 * 刷新文件
		 * 注意：eclipse以外的外部编辑器修改文件后不会使文件在eclipse中刷新
		 */
//		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("a/src/html"));
//		try {
//			System.out.println(file.exists());
//			file.refreshLocal(IResource.DEPTH_ONE, null);
//		} catch (CoreException e) {
//			log.error("刷新文件失败");
//		}
		
	}

}
