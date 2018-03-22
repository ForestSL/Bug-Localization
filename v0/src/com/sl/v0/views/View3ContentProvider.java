package com.sl.v0.views;

/*视图消息传递*/

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Table;

import com.sl.v0.buglocation.datas.Bug;
import com.sl.v0.buglocation.datas.BugModel;
import com.sl.v0.datas.TableCell;

public class View3ContentProvider implements IStructuredContentProvider{

	  BugModel input;
	  ListViewer viewer;
	  
	  public Object[] getElements(Object inputElement) {
		  // TODO Auto-generated method stub
		  return input.elements().toArray();
	  }
	  
	  public String getText(Object element) {
		  return ((Bug) element).getBugId();
	  }
	  
	  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		  viewer = (ListViewer) viewer;
		  input = (BugModel) newInput;
	    
	  }
	  
	  public void addListener(ILabelProviderListener listener) {
		  // TODO 自动生成方法存根
	  }
	  
	  public void add(TableCell p) {
		  viewer.add(p);	  
	  }
	  
	  public void remove(TableCell p) {
		  viewer.remove(p);		  
	  }
	  
	  public void dispose() {
		  // TODO Auto-generated method stub	  
	  }
	
}
