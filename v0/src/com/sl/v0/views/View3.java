package com.sl.v0.views;

/*��ϸbug���չʾ��ͼ*/

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.datas.GlobalVar;
import com.sl.v0.datas.TableCell;

public class View3 extends ViewPart {
	
    public void createPartControl(Composite parent) {
        Composite topComp = new Composite(parent,SWT.NONE);
        topComp.setLayout(new FillLayout());
        Text text = new Text(topComp,SWT.BORDER);
        if(GlobalVar.cell.getFile()==null)
        	text.setText("δѡ����Ҫչʾ��bug�ļ���");
        else
        	text.setText("�˴���ϸչʾ"+GlobalVar.cell.getFile()+
        		"�ļ�ͨ��"+GlobalVar.cell.getMethod()+
        		"������λ��bug���,bug��Ϊ"+GlobalVar.cell.getCount());
    }
    
    public void setFocus() {}
    
}
