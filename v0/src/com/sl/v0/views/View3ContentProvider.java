package com.sl.v0.views;

/*视图消息传递*/

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Table;

import com.sl.v0.datas.TableCell;
import com.sl.v0.datas.TableCellModel;

public class View3ContentProvider implements IStructuredContentProvider{

	  TableCellModel input;
	  ListViewer viewer;
	  
	  public Object[] getElements(Object inputElement) {
		  // TODO Auto-generated method stub
		  return input.elements().toArray();
	  }
	  
	  public String getText(Object element) {
		  String re="未选中!";
		  if(((TableCell)element).getMethod()==null||((TableCell)element).getMethod()=="文件")
			  return re;
		  //re=">>>点击此处>>>"+"查看"+((TableCell)element).getFile()+"在"+((TableCell)element).getMethod()+"方法下定位的详细bug信息";
	      re="详情";
		  return re;
	  }
	  
	  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		  viewer = (ListViewer) viewer;
		  input = (TableCellModel) newInput;
	    
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
