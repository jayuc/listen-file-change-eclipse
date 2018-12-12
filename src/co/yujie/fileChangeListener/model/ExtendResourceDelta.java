package co.yujie.fileChangeListener.model;

import org.eclipse.core.internal.events.ResourceDelta;
import org.eclipse.core.internal.events.ResourceDeltaInfo;
import org.eclipse.core.runtime.IPath;

public class ExtendResourceDelta extends ResourceDelta {

	protected ExtendResourceDelta(IPath path, ResourceDeltaInfo deltaInfo) {
		super(path, deltaInfo);
	}
	
	public ExtendResourceDelta(IPath path, int status) {
		super(path, null);
		super.setStatus(status);
	}

}
