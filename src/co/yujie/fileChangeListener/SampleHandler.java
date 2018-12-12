package co.yujie.fileChangeListener;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.events.ResourceDelta;
import org.eclipse.core.internal.runtime.Log;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

import org.eclipse.ui.part.FileEditorInput;

/**
 * 开发插件时走过的弯路
 * @author yujie
 *
 */
public class SampleHandler extends AbstractHandler {
	
	
	public SampleHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        MessageDialog.openInformation(window.getShell(),"EventListen","Trying event listen");
        

        //earlyStartup();
        //earlyStartup1();
        //listen();
        
		
		return null;
	}
	
	public void listenDocument() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        System.out.println("------page: " + page.getLabel());
        IEditorPart editor = page.getActiveEditor();
        System.out.println("-----editor: " + editor.getTitle());
        IEditorInput input = editor.getEditorInput();  
        System.out.println("-------input: " + input.getName());
        IDocument document=(((ITextEditor)editor).getDocumentProvider()).getDocument(IDocument.class);
        System.out.println("--------document--length: " + document);
        document.addDocumentListener(new IDocumentListener() //**this is line 45**
                {
                    @Override
                    public void documentAboutToBeChanged(DocumentEvent event) {
                        // TODO Auto-generated method stub
                        System.out.println("Hello");
                    }

                    @Override
                    public void documentChanged(DocumentEvent event) {
                        // TODO Auto-generated method stub
                        System.out.println("Hello second");

                    }
                });
	}
	
	//@Override
	public void earlyStartup() {
	    IWorkbench wb = PlatformUI.getWorkbench();
	    wb.addWindowListener(generateWindowListener());
	}
	
	//@Override
	public void earlyStartup1() {
	    Display display = Display.getDefault();
	    System.out.println("-------display: " + display);
	    display.addFilter(SWT.KeyUp, new Listener() {
	        @Override
	        public void handleEvent(Event event) {
	            //do stuff here.  Be careful, this may cause lag
	        	System.out.println("=======event: " + event.toString());
	        }
	     });
	}
	
	private void listen() {
		 JavaCore.addElementChangedListener(new IElementChangedListener() {
	
		        @Override
		        public void elementChanged(ElementChangedEvent event)
		        {
		            //do stuff with the event
		        	
		        	System.out.println("--------- java-core: " + event.getDelta());
		        	System.out.println("-------- type: "+ event.getType());
		        	convertSourece(event.getDelta().getAffectedChildren());
		        }
		    });
	}
	
	private IWindowListener generateWindowListener() 
	{
	    return new IWindowListener() {
	        @Override
	        public void windowOpened(IWorkbenchWindow window) {
	            IWorkbenchPage activePage = window.getActivePage(); 
	            System.out.println("----------open: " + window.toString());
	            activePage.addPartListener(generateIPartListener2());
	        }

	        @Override
	        public void windowDeactivated(IWorkbenchWindow window) {
	        	System.out.println("-------eactivate: " + window.toString());
	        }

	        @Override
	        public void windowClosed(IWorkbenchWindow window) {
	        	System.out.println("-------close: " + window.toString());
	        }

	        @Override
	        public void windowActivated(IWorkbenchWindow window) {
	        	System.out.println("-------activated: " + window.toString());
	        }
	    };
	}
	
	private IPartListener2 generateIPartListener2() 
	{
	    return new IPartListener2() {

	        private void checkPart(IWorkbenchPartReference partRef) {
	        IWorkbenchPart part = partRef.getPart(false);
	            if (part instanceof IEditorPart)
	            {
	                IEditorPart editor = (IEditorPart) part;
	                IEditorInput input = editor.getEditorInput();
	                if (editor instanceof ITextEditor && input instanceof FileEditorInput)  //double check.  Error Editors can also bring up this call
	                {
	                    IDocument document=(((ITextEditor)editor).getDocumentProvider()).getDocument(input);
	                    document.addDocumentListener(new IDocumentListener() {

							@Override
							public void documentAboutToBeChanged(DocumentEvent arg0) {
								// TODO Auto-generated method stub
								 System.out.println("----------Hello");
							}

							@Override
							public void documentChanged(DocumentEvent arg0) {
								// TODO Auto-generated method stub
								 System.out.println("------------changed");
							}
	                    	
	                    });
	                }
	            }
	        }

	        @Override
	        public void partOpened(IWorkbenchPartReference partRef) {
	            checkPart(partRef);
	        }

	        @Override
	        public void partInputChanged(IWorkbenchPartReference partRef) 
	        {
	            checkPart(partRef);
	        }           

	        @Override
	        public void partVisible(IWorkbenchPartReference partRef){}

	        @Override
	        public void partHidden(IWorkbenchPartReference partRef) {}

	        @Override
	        public void partDeactivated(IWorkbenchPartReference partRef)  {}

	        @Override
	        public void partClosed(IWorkbenchPartReference partRef) {}

	        @Override
	        public void partBroughtToTop(IWorkbenchPartReference partRef) {}

	        @Override
	        public void partActivated(IWorkbenchPartReference partRef) {}
	    };
	}
	
	private void convertSourece(IJavaElementDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			IJavaElementDelta item = deltas[i];
			IJavaElementDelta[] list = item.getAffectedChildren();
			if(list.length > 0) {
				convertSourece(list);
			}else if(item != null) {
				System.out.println("------sorceData: " + item.getResourceDeltas());
				if(item.getElement() != null) {
					System.out.println("------element: " + item.getElement());
					System.out.println("------elementType: " + item.getElement().getElementType());
					System.out.println("------elementName: " + item.getElement().getElementName());
				}
				if(item.getResourceDeltas() != null) {
					System.out.println("----path: " + item.getResourceDeltas()[0].getFullPath());
					System.out.println("----status: " + getStatus(item.getResourceDeltas()[0].getKind()));
					System.out.println("------dataInfo: " + item.getResourceDeltas()[0].getResource());
				}
				
			}
		}
	}
	
	private String getStatus(int code) {
		String result = null;
		switch(code) {
			case ResourceDelta.ADDED:
				result = "+";
				break;
			case ResourceDelta.CHANGED:
				result = "*";
				break;
			case ResourceDelta.REMOVED:
				result = "-";
				break;
		}
		return result;
	}

}
