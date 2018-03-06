package com.sl.v0.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RootPaneContainer;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.editors.EditorInput;
import com.sl.v0.views.ColumnSort.MyTableModel;

public class View2 extends ViewPart {
	
	      
//    private Table table;
//    
//    /*�������ݣ�ģ��bug�����������*/
//    private String[][] test={
//    		{"a.java","60","1","103","25","55"},
//    		{"b.java","30","2","102","35","56"},
//    		{"c.java","50","4","101","14","67"},
//    		{"d.java","10","3","100","34","66"}};
	
    public void createPartControl(Composite parent) {
        //IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        //help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new FillLayout());
        
        /*��д������*/
        JFrame f = new JFrame("ColumnModelTest");
        f.setLocation(500, 500);
        f.setSize(100,10);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new ColumnSort(), BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);


//        table=new Table(topComp,SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
//        table.setHeaderVisible(true);/*���ñ�ͷ�ɼ�*/
//        table.setLinesVisible(true);/*���������ɼ�*/
//        table.setLayoutData(new GridData(GridData.FILL_BOTH));
//        
//        TableColumn column0 = new TableColumn(table,SWT.NULL);
//        column0.setText("");
//        column0.pack();
//        column0.setWidth(150);
//        
//        TableColumn column1 = new TableColumn(table,SWT.NULL);
//        column1.setText("VSM");
//        column1.pack();
//        column1.setWidth(150);
//        
//        TableColumn column2 = new TableColumn(table,SWT.NULL);
//        column2.setText("LSI");
//        column2.pack();
//        column2.setWidth(150);
//        
//        TableColumn column3 = new TableColumn(table,SWT.NULL);
//        column3.setText("JSM");
//        column3.pack();
//        column3.setWidth(150);
//        
//        TableColumn column4 = new TableColumn(table,SWT.NULL);
//        column4.setText("PMI");
//        column4.pack();
//        column4.setWidth(150);
//        
//        TableColumn column5 = new TableColumn(table,SWT.NULL);
//        column5.setText("NGD");
//        column5.pack();
//        column5.setWidth(150);
//        
//        for(int i=0;i<test.length;i++){
//        	TableItem item = new TableItem(table,SWT.NONE);
//        	item.setText(test[i]);
//        }
//        
//        /*�����ͷ����*/
//        
//        
//        /*������¼�����*/
//        table.addMouseListener(new MouseAdapter() {
//            private EditorInput EditorInput = new EditorInput();
//
//            public void mouseDoubleClick(MouseEvent e) {
//                /* ���ݲ�ͬ�б���õ�����Ӧ��editorInput�����editorID
//                 * ����editorIDָ�ñ༭����plugin.xml�ļ�������id��ʶֵ*/
//                Table table = (Table) e.getSource();/*��MouseEvent�õ��б����*/
//                int row=table.getSelectionIndex();/*��ȡ��*/
//                String fileName=table.getItem(row).getText(0);/*��ȡ�еĵ�һ��Ԫ�أ����ļ���*/
//                /*System.out.println(fileName);*/
//                
//                IEditorInput editorInput = null;
//                String editorID = null;
//                editorInput = EditorInput;
//                editorID = "com.sl.v0.editors.Editor";
//              
//                /* ���editorInput��editorIDΪ�����жϷ���*/
//                if (editorInput == null || editorID == null)
//                    return;
//                /* ȡ��IWorkbenchPage��������ʹ��editorInput�����Ӧ�ı༭��*/
//                IWorkbenchPage workbenchPage = getViewSite().getPage();
//                IEditorPart editor = workbenchPage.findEditor(editorInput);
//                // ����˱༭���Ѿ����ڣ�������Ϊ��ǰ�ı༭������ˣ�������
//                // ���´�һ���༭��
//                if (editor != null) {
//                    workbenchPage.bringToTop(editor);
//                } else {
//                    try {
//                        workbenchPage.openEditor(editorInput, editorID);
//                    } catch (PartInitException e2) {
//                        e2.printStackTrace();
//                    }
//                }
//            }
//        });
        
    }
    @Override
    public void setFocus() {}
}
