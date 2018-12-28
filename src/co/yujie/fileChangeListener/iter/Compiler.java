package co.yujie.fileChangeListener.iter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import co.yujie.fileChangeListener.model.FileChangeInfo;
import co.yujie.fileChangeListener.util.LogUtil;

/**
 * 编译器
 * @author yujie
 *
 */
public final class Compiler {
	
	private final static Compiler compiler = new Compiler();
	
	private final static ExecutorService executor = Executors.newFixedThreadPool(1);
	
	private final static Object statusLock = new Object();
	
	private final static Object remainLock = new Object();
	
	private final static Logger log = LogUtil.getLog(Compiler.class);
	
	private Compiler() {
		
	}
	
	/**
	 * 编译器状态；true -> 正在编译
	 */
	private static boolean status = Boolean.FALSE;
	
	/**
	 * 是否有剩余未编译
	 */
	private static boolean remain = Boolean.FALSE;
	
	/**
	 * 编译
	 * @param info 文件变化信息对象
	 */
	public static void compile(FileChangeInfo info) {
		if(shouldCompile()) {
			executor.submit(() -> {
				log.debug("开始编译");
				setStatus(Boolean.TRUE);
				IAction action = new JsAction();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("fileChangeInfo", info);
				action.execute(data);
				log.debug("结束编译");
				setStatus(Boolean.FALSE);
			});
		}
	}
	
	/**
	 * 判断是否应该进行编译
	 * @return
	 */
	private static boolean shouldCompile() {
		if(!isStatus()) {
			return true;
		}else {
			if(!isRemain()) {
				setRemain(Boolean.TRUE);
			}
			log.debug("是否有预留编译任务：" + isRemain());
		}
		log.debug("是否正在编译：" + isStatus());
		return false;
	}

	private static boolean isStatus() {
		synchronized (statusLock) {
			return status;
		}
	}

	private static void setStatus(boolean status) {
		synchronized (statusLock) {
			Compiler.status = status;
		}
	}

	private static boolean isRemain() {
		synchronized (remainLock) {
			return remain;
		}
	}

	private static void setRemain(boolean remain) {
		synchronized (remainLock) {
			Compiler.remain = remain;
		}
	}

}
