package co.yujie.fileChangeListener.iter;

import java.util.Map;

import org.apache.log4j.Logger;

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
		Compiler compiler = (Compiler) data.get("compiler");
		FileChangeInfo fileChangeInfo = (FileChangeInfo) data.get("fileChangeInfo");
		
	}

}
