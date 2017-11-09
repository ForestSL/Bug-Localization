package com.sl.v0.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

public class View4 extends ViewPart {
    private List list; // 将列表写成类的实例变量，以扩大它的可访问范围
    //注意这个List并不是java.util包下的.而是org.eclipse.swt.widgets.List;包下的.
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