package co.yujie.fileChangeListener.iter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import co.yujie.fileChangeListener.model.FileChangeInfo;
import co.yujie.fileChangeListener.util.LogUtil;

/**
 * ������
 * @author yujie
 *
 */
public final class Compiler {
	
	private final static ExecutorService executor = Executors.newFixedThreadPool(1);
	
	private final static Object statusLock = new Object();
	
	private final static Object remainLock = new Object();
	
	private final static Logger log = LogUtil.getLog(Compiler.class);
	
	private Compiler() {
		
	}
	
	/**
	 * ������״̬��true -> ���ڱ���
	 */
	private static boolean status = Boolean.FALSE;
	
	/**
	 * �Ƿ���ʣ��δ����
	 */
	private static boolean remain = Boolean.FALSE;
	
	/**
	 * ����
	 * @param info �ļ��仯��Ϣ����
	 */
	public static void compile(FileChangeInfo info) {
		if(shouldCompile()) {
			executor.submit(() -> {
				log.debug("��ʼ����");
				setStatus(Boolean.TRUE);
				IAction action = new JsAction();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("fileChangeInfo", info);
				action.execute(data);
				log.debug("��������");
				setStatus(Boolean.FALSE);
			});
		}
	}
	
	/**
	 * �ж��Ƿ�Ӧ�ý��б���
	 * @return
	 */
	private static boolean shouldCompile() {
		if(!isStatus()) {
			return true;
		}else {
			if(!isRemain()) {
				setRemain(Boolean.TRUE);
			}
			log.debug("�Ƿ���Ԥ����������" + isRemain());
		}
		log.debug("�Ƿ����ڱ��룺" + isStatus());
		return false;
	}

	public static boolean isStatus() {
		synchronized (statusLock) {
			return status;
		}
	}

	public static void setStatus(boolean status) {
		synchronized (statusLock) {
			Compiler.status = status;
		}
	}

	public static boolean isRemain() {
		synchronized (remainLock) {
			return remain;
		}
	}

	public static void setRemain(boolean remain) {
		synchronized (remainLock) {
			Compiler.remain = remain;
		}
	}

}
