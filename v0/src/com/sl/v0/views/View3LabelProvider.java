package com.sl.v0.views;

/*��ͼ��Ϣ����*/

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
	    	String re="δѡ��!";
	    	if(((TableCell)element).getMethod()==null||((TableCell)element).getMethod()=="�ļ�")
	    		return re;
	    	//re=">>>����˴�>>>"+"�鿴"+((TableCell)element).getFile()+"��"+((TableCell)element).getMethod()+"�����¶�λ����ϸbug��Ϣ";
	    	re="����";
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
