package co.yujie.fileChangeListener;

import org.eclipse.core.internal.events.ResourceDelta;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.apache.log4j.Logger;

import co.yujie.fileChangeListener.iter.Compiler;
import co.yujie.fileChangeListener.iter.Judger;
import co.yujie.fileChangeListener.model.ExtendResourceDelta;
import co.yujie.fileChangeListener.model.FileChangeInfo;
import co.yujie.fileChangeListener.model.FileOperate;
import co.yujie.fileChangeListener.util.ConfigUtil;
import co.yujie.fileChangeListener.util.LogUtil;

/**
 * 项目入口文件
 * @author yujie
 *
 */
public class ListenFileChange implements IStartup {
	
	private static Logger log = LogUtil.getLog(ListenFileChange.class);

	/**
	 * 当eclipse启动时，插件激活后立即执行此方法，
	 * 添加文档修改的监听
	 */
	@Override
	public void earlyStartup() {

		log.debug("开始加载插件");
		
		beforeEarlyStartup();
		
		JavaCore.addElementChangedListener(new IElementChangedListener() {
			
			@Override
			public void elementChanged(ElementChangedEvent event) {
				
				if(event != null && null != PlatformUI.getWorkbench().getActiveWorkbenchWindow()) {
					//FileChangeInfo info = convert(event);
					FileChangeInfo info = convertByWindow(event);
					if(null != info) {
						log.info(info);
						
						/**
						 * //在这里监听文件入口
						 * dothing...
						 */
						
						/**
						 * 执行一个js编译器
						 */
						if(Judger.haveConfig() 
								&& Judger.checkProject(info)
								&& Judger.mateContext(info)) {
							Compiler.compile(info);   //开始编译js文件
						}else {
							log.debug("配置不匹配，不进行编译");
						}
						
					}
				}
			}
		}, 1);
		
		afterEarlyStartup();
		
		log.debug("插件加载完成");
	}
	
	/**
	 * earlyStartup 执行前执行
	 */
	private void beforeEarlyStartup() {
		
		log.debug("开始  earlyStartup 执行前执行");
		
		ConfigUtil.loadConfigs();
		
		log.debug("结束  earlyStartup 执行前执行");
		
	}
	
	/**
	 * earlyStartup 执行后执行
	 */
	private void afterEarlyStartup() {
		
		log.debug("开始  earlyStartup 执行后执行");
		
		log.debug("结束  earlyStartup 执行后执行");
		
	}
	
	/**
	 * 转化为文件修改描述信息类
	 * @param event
	 * @return
	 */
	private FileChangeInfo convertByWindow(ElementChangedEvent event) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorPart editor = page.getActiveEditor();
        if(null != editor) {
        	FileEditorInput input = (FileEditorInput) editor.getEditorInput();
        	IFile file = input.getFile();
        	return new FileChangeInfo(file.getFullPath().toString(), getKind(event.getDelta().getKind()), 
        			file.getFileExtension(), file.getName(), input.getStorage().toString(), file.getLocation().toString(), 
        			file.getProject().getName(), file.getProject().getLocation().toString());
        }
        return null;
	}
	
	/**
	 * 转化为文件修改描述信息类
	 * @param event
	 * @return
	 */
	@SuppressWarnings("unused")
	private FileChangeInfo convert(ElementChangedEvent event) {
		IJavaElementDelta[] deltas = event.getDelta().getAffectedChildren();
		IResourceDelta resource = null;
		if(deltas.length > 0) {
			resource = convertSourece(deltas);
		}else {
			resource = event.getDelta().getResourceDeltas()[0];
		}
		if(resource != null) {
			IPath path = resource.getFullPath();
			FileChangeInfo info = new FileChangeInfo(path.toString(), getKind(resource.getKind()), path.getFileExtension());
			return info;
		}
		return null;
	}
	
	private IResourceDelta convertSourece(IJavaElementDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			IJavaElementDelta item = deltas[i];
			IJavaElementDelta[] list = item.getAffectedChildren();
			if(list.length > 0) {
				return convertSourece(list);
			}else if(item != null) {
				if(item.getResourceDeltas() != null) {
					return item.getResourceDeltas()[0];
				}else {
					return new ExtendResourceDelta(item.getElement().getPath(), item.getKind());
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("restriction")
	private FileOperate getKind(int code) {
		switch(code) {
			case ResourceDelta.ADDED:
				return FileOperate.ADD;
			case ResourceDelta.CHANGED:
				return FileOperate.CHANGE;
			case ResourceDelta.REMOVED:
				return FileOperate.REMOVE;
		}
		return null;
	}

}
