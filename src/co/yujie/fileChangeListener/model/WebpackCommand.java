package co.yujie.fileChangeListener.model;

import co.yujie.fileChangeListener.util.ConfigUtil;

public class WebpackCommand {
	
	/**
	 * webpack 安装路径前缀
	 */
	private String webpackPrefix = ConfigUtil.getWebpackPrefix();
	
	/**
	 * 输入文件路径
	 */
	private String entryPath = ConfigUtil.getContext();
	
	/**
	 * 输入文件名
	 */
	private String entryName;
	
	/**
	 * 输出文件路径
	 */
	private String outPath = ConfigUtil.getOutPath();
	
	/**
	 * 输出文件路径
	 */
	private String outName;
	
	/**
	 * 编译模式 开发模式/生成模式
	 */
	private String model = ConfigUtil.getModel();
	
	/**
	 * 生成命令
	 * @return
	 */
	public String getCommand() {
		if(null != webpackPrefix 
				&& null != entryPath 
				&& null != entryName 
				&& null != outName 
				&& null != outPath 
				&& null != model) {
			return "node " + webpackPrefix + "node_modules/webpack/bin/webpack.js --context " + entryPath 
					+ " ./" + entryName + " -o " + outPath + outName + " --mode " + model;
		}
		return null;
	}

}
