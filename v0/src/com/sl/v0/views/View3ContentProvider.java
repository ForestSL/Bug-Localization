package com.sl.v0.views;

/*��ͼ��Ϣ����*/

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
		  String re="δѡ��!";
		  if(((TableCell)element).getMethod()==null||((TableCell)element).getMethod()=="�ļ�")
			  return re;
		  //re=">>>����˴�>>>"+"�鿴"+((TableCell)element).getFile()+"��"+((TableCell)element).getMethod()+"�����¶�λ����ϸbug��Ϣ";
	      re="����";
		  return re;
	  }
	  
	  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		  viewer = (ListViewer) viewer;
		  input = (TableCellModel) newInput;
	    
	  }
	  
	  public void addListener(ILabelProviderListener listener) {
		  // TODO �Զ����ɷ������
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
