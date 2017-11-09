package com.sl.v0.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class View3 extends ViewPart {
	
    public void createPartControl(Composite parent) {
        Composite topComp = new Composite(parent,SWT.NONE);
        topComp.setLayout(new FillLayout());
        Text text = new Text(topComp,SWT.BORDER);
        text.setText("我是bug展示区");
    }
    
    public void setFocus() {}
}
