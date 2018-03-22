package com.sl.v0.views;

/*视图消息传递*/

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

import com.sl.v0.buglocation.datas.Bug;

public class View3LabelProvider  implements ILabelProvider{

	   public Image getImage(Object element) {
	        // TODO Auto-generated method stub
	        return null;
	    }

	    public String getText(Object element) {
	        // TODO Auto-generated method stub
	    	return ((Bug) element).getBugId();
	    }

	    public void addListener(ILabelProviderListener listener) {
	        // TODO Auto-generated method stub

	    }

	    public void dispose() {
	        // TODO Auto-generated method stub

	    }

	    public boolean isLabelProperty(Object element, String property) {
	        // TODO Auto-generated method stub
	        return false;
	    }

	    public void removeListener(ILabelProviderListener listener) {
	        // TODO Auto-generated method stub

	    }

	    public void labelProviderChanged(LabelProviderChangedEvent event) {
	        // TODO Auto-generated method stub

	    }
	
}
