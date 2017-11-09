package com.sl.v0.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

public class View2 extends ViewPart {
    private List list;
    private Table table;
    public void createPartControl(Composite parent) {
        IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new FillLayout());

        table=new Table(topComp,SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);/*设置表头可见*/
        table.setLinesVisible(true);/*设置线条可见*/
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        TableColumn column0 = new TableColumn(table,SWT.NULL);
        column0.setText("文件名");
        column0.pack();
        column0.setWidth(150);
        
        TableColumn column1 = new TableColumn(table,SWT.NULL);
        column1.setText("VSM");
        column1.pack();
        column1.setWidth(150);
        
        TableColumn column2 = new TableColumn(table,SWT.NULL);
        column2.setText("LSI");
        column2.pack();
        column2.setWidth(150);
        
        TableColumn column3 = new TableColumn(table,SWT.NULL);
        column3.setText("JSM");
        column3.pack();
        column3.setWidth(150);
        
        TableColumn column4 = new TableColumn(table,SWT.NULL);
        column4.setText("PMI");
        column4.pack();
        column4.setWidth(150);
        
        TableColumn column5 = new TableColumn(table,SWT.NULL);
        column5.setText("NGD");
        column5.pack();
        column5.setWidth(150);
        
        TableItem item0 = new TableItem(table,SWT.NONE);
        item0.setText(new String[]{"文件a"});
        TableItem item1 = new TableItem(table,SWT.NONE);
        item1.setText(new String[]{"文件b"});
        TableItem item2 = new TableItem(table,SWT.NONE);
        item2.setText(new String[]{"文件c"});
        TableItem item3 = new TableItem(table,SWT.NONE);
        item3.setText(new String[]{"文件d"});
        TableItem item4 = new TableItem(table,SWT.NONE);
        item4.setText(new String[]{"文件e"});
    }
    @Override
    public void setFocus() {}
}
