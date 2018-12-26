package co.yujie.fileChangeListener.iter;

import java.util.Map;

import co.yujie.fileChangeListener.model.FileChangeInfo;

/**
 * 当js变化时执行
 * @author yujie
 *
 */
public class JsAction implements IAction {

	@Override
	public void execute(Object obj) {

		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) obj;
		Compiler compiler = (Compiler) data.get("compiler");
		FileChangeInfo fileChangeInfo = (FileChangeInfo) data.get("fileChangeInfo");
		
	}

}
