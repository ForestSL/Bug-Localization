package com.sl.v0.views;

/*视图消息传递*/

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

import com.sl.v0.datas.TableCell;

public class View3LabelProvider  implements ILabelProvider{

	   public Image getImage(Object element) {
	        // TODO Auto-generated method stub
	        return null;
	    }

	    public String getText(Object element) {
	        // TODO Auto-generated method stub
	    	String re="未选中!";
	    	if(((TableCell)element).getMethod()==null||((TableCell)element).getMethod()=="文件")
	    		return re;
	    	//re=">>>点击此处>>>"+"查看"+((TableCell)element).getFile()+"在"+((TableCell)element).getMethod()+"方法下定位的详细bug信息";
	    	re="详情";
	    	return re;
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
