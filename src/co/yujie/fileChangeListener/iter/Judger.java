package co.yujie.fileChangeListener.iter;

import org.apache.log4j.Logger;

import co.yujie.fileChangeListener.model.FileChangeInfo;
import co.yujie.fileChangeListener.model.FileOperate;
import co.yujie.fileChangeListener.util.ConfigUtil;
import co.yujie.fileChangeListener.util.LogUtil;

/**
 * 做一些业务性质的判断
 * @author yujie
 *
 */
public class Judger {

	private final static Logger log = LogUtil.getLog(Judger.class);

	/**
	 * 判断配置文件是否成功加载
	 * @return
	 */
	public static boolean haveConfig() {
		if(null != ConfigUtil.getWebpackPrefix() 
				&& null != ConfigUtil.getContext() 
				&& null != ConfigUtil.getOutPath() 
				&& null != ConfigUtil.getModel() 
				&& null != ConfigUtil.getProjectName()
				&& "js".equals(ConfigUtil.getFileType())) {
			log.debug("配置文件成功加载，生效的文件类型：" + ConfigUtil.getFileType());
			return true;
		}
		log.debug("配置文件未成功加载");
		return false;
	}
	
	/**
	 * 核对项目名与配置的项目名是否一致
	 * @param info
	 * @return
	 */
	public static boolean checkProject(FileChangeInfo info) {
		String projectName = ConfigUtil.getProjectName();
		if(null != projectName) {
			String[] PNames = projectName.split("/");
			for(int i=0; i<PNames.length; i++) {
				if(info.getProjectName().equals(PNames[i])) {
					return true;
				}
			}
		}
		log.debug("项目名不对");
		return false;
	}
	
	/**
	 * 判断文件变化信息是否匹配配置路径
	 * @return
	 */
	public static boolean mateContext(FileChangeInfo info) {
		if(!"js".equals(info.getType())) {
			log.debug("只能编译js文件，现在文件类型：" + info.getType());
			return false;
		}
		if(null != info 
				&& FileOperate.CHANGE == info.getOpear() 
				&& null != info.getLocation()) {
			if(info.getLocation().startsWith(info.getProjectLocation() + ConfigUtil.getContext())) {
				return true;
			}
			log.debug("变化文件位置：" + info.getLocation());
			log.debug("配置输入文件位置：" + info.getProjectLocation() + ConfigUtil.getContext());
			return false;
		}
		log.debug("没有变化的文件或信息不完整");
		return false;
	}
	
}
