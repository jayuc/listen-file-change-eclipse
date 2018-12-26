package co.yujie.fileChangeListener.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
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
	 * 编译模式  开发环境/生产环境
	 */
	private static String model = null;
	
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
			model = properties.getProperty("model");
			outPath = properties.getProperty("outPath");
		}
	}
	
	/**
	 * 加载配置
	 */
	public static void loadConfigs() {
		log.debug("插件工作空间位置：" + workPlugPath);
		log.debug("插件安装位置：" + plugPath);
		File plugPathFile = new File(plugPath);
		if(!plugPathFile.exists()) {
			plugPathFile.mkdirs();
		}
		File workPathFile = new File(workPlugPath);
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
	
}
