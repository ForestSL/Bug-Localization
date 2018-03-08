package com.sl.v0.views;

/*详细bug结果展示视图*/

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
        	text.setText("未选中需要展示的bug文件！");
        else
        	text.setText("此处详细展示"+GlobalVar.cell.getFile()+
        		"文件通过"+GlobalVar.cell.getMethod()+
        		"方法定位的bug结果,bug数为"+GlobalVar.cell.getCount());
    }
    
    public void setFocus() {}
    
}
