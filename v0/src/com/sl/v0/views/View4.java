package com.sl.v0.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

public class View4 extends ViewPart {
    private List list; // ���б�д�����ʵ�����������������Ŀɷ��ʷ�Χ
    //ע�����List������java.util���µ�.����org.eclipse.swt.widgets.List;���µ�.
    public void createPartControl(Composite parent) {
        IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new FillLayout());
        list = new List(topComp, SWT.BORDER);
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
    }
    @Override
    public void setFocus() {}
}