package com.sl.v0.views;

/*��ϸbug���չʾ��ͼ*/

import java.util.ArrayList;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.datas.GlobalVar;
import com.sl.v0.datas.TableCell;

public class View3 extends ViewPart implements ISelectionListener{
	
	Text text;
	
	/*��ͼ����Ϣ����*/
    @SuppressWarnings("unchecked")
    ArrayList myListeners;
    public View3(){
    	super();
    }
	
    public void createPartControl(Composite parent) {
        Composite topComp = new Composite(parent,SWT.NONE);
        topComp.setLayout(new FillLayout());
        text = new Text(topComp,SWT.BORDER);
//        if(GlobalVar.cell.getFile()==null)
//        	text.setText("δѡ����Ҫչʾ��bug�ļ���");
//        else
//        	text.setText("�˴���ϸչʾ"+GlobalVar.cell.getFile()+
//        		"�ļ�ͨ��"+GlobalVar.cell.getMethod()+
//        		"������λ��bug���,bug��Ϊ"+GlobalVar.cell.getCount());
        
        /*��ͼ����Ϣ����*/
        this.getSite().getPage().addSelectionListener("com.sl.v0.views.View2",(ISelectionListener)this);
        
    }
    
    public void setFocus() {}
    
    /*��ͼ����Ϣ����*/
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    	IStructuredSelection structuredSelection = (IStructuredSelection)selection;
    	Object obj = structuredSelection.getFirstElement();
    	TableCell temp = (TableCell)obj;
    	if(temp.getFile() != null&&temp.getFile()!="�ļ�"){
    		text.setText("�˴���ϸչʾ"+temp.getFile()+"�ļ�ͨ��"+temp.getMethod()+"������λ��bug���,bug��Ϊ"+temp.getCount());
    	}
    	else
    		text.setText("δѡ�У�");
    }
    
    
}
