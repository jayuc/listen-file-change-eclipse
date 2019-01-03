package co.yujie.fileChangeListener.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;

/**
 * 加载项目配置
 * @author yujie
 *
 */
public class ConfigUtil {

	/**
	 * webpack.js 安装位置
	 */
	private static String webpackPrefix = null;
	
	/**
	 * 需要编译项目根目录
	 */
	private static String context = null;
	
	/**
	 * 输出根目录
	 */
	private static String outPath = null;
	
	/**
	 * 需要编译的项目名
	 */
	private static String projectName = null;
	
	/**
	 * 真实的项目名，不包含父项目
	 */
	private static String recentProjectName = null;
	
	/**
	 * 输出文件在tomcat中的位置，不是真实的代码，是为了解决tomcat中的缓存问题
	 */
	private static String webFilePath = null;
	
	/**
	 * 编译模式  开发环境/生产环境,默认开发环境
	 */
	private static String model = "development";
	
	/**
	 * 生效的文件类型
	 */
	private static String fileType = "js";
	
	/**
	 * 生效的文件名
	 */
	private static String fileName = "index.js";
	
	/**
	 * eclipse 工作空间位置
	 */
	private final static String workPath;
	
	/**
	 * 插件工作空间位置
	 */
	private final static String workPlugPath;
	
	/**
	 * 插件安装位置
	 */
	private final static String plugPath;
	
	private final static Logger log = LogUtil.getLog(ConfigUtil.class);
	
	static {
		workPath = Platform.getInstanceLocation().getURL().getPath();
		workPlugPath = Platform.getInstanceLocation().getURL().getPath() + 
				".metadata/.plugins/co.yujie.fileChangeListener/";
		plugPath = Platform.getInstallLocation().getURL().getPath() + 
				"plugins/co.yujie.fileChangeListener/";
		File file = findConfigFile();
		if(null != file) {
			Properties properties = new Properties();
			try {
				FileInputStream fis = new FileInputStream(file);
				properties.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
			webpackPrefix = properties.getProperty("webpackPrefix");
			context = properties.getProperty("context");
			outPath = properties.getProperty("outPath");
			projectName = properties.getProperty("projectName");
			recentProjectName = properties.getProperty("recentProjectName");
			webFilePath = properties.getProperty("webFilePath");
			String outP = properties.getProperty("model");
			if(null != outP) {
				model = outP;
			}
			String fileN = properties.getProperty("fileName");
			if(null != fileN) {
				fileName = fileN;
			}
			String fileT = properties.getProperty("fileType");
			if(null != fileT) {
				//fileType = fileT;
			}
		}
	}
	
	/**
	 * 加载配置
	 */
	public static void loadConfigs() {
		File plugPathFile = new File(plugPath);
		log.debug("插件安装位置：" + plugPath);
		if(!plugPathFile.exists()) {
			plugPathFile.mkdirs();
		}
		File workPathFile = new File(workPlugPath);
		log.debug("插件在工作空间位置：" + workPlugPath);
		if(!workPathFile.exists()) {
			workPathFile.mkdirs();
		}
	}
	
	/**
	 * 寻找配置文件位置
	 * @return
	 */
	private static File findConfigFile() {
		File plugPathFile = new File(plugPath + "config.properties");
		if(plugPathFile.exists()) {
			return plugPathFile;
		}
		File workPathFile = new File(workPlugPath + "config.properties");
		if(workPathFile.exists()) {
			return workPathFile;
		}
		return null;
	}

	public static String getWebpackPrefix() {
		return webpackPrefix;
	}

	public static String getWorkPlugPath() {
		return workPlugPath;
	}

	public static String getContext() {
		return context;
	}

	public static String getPlugpath() {
		return plugPath;
	}

	public static String getModel() {
		return model;
	}

	public static String getOutPath() {
		return outPath;
	}

	public static String getFileType() {
		return fileType;
	}

	public static String getFileName() {
		return fileName;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static String getWorkpath() {
		return workPath;
	}

	public static String getRecentProjectName() {
		return recentProjectName;
	}

	public static String getWebFilePath() {
		return webFilePath;
	}
	
}
