package com.sl.v0.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

public class View2 extends ViewPart {
	
    private void createActions() {
    	/*��ӹ���������*/
    	
    }
    
    private void initializeToolBar() {
        IToolBarManager tbm=getViewSite().getActionBars().getToolBarManager();
    }
    private void initializeMenu() {
        IMenuManager manager =getViewSite().getActionBars().getMenuManager();
    }
	
	
//    private Table table;
//    
//    /*�������ݣ�ģ��bug�����������*/
//    private String[][] test={
//    		{"a.java","60","1","103","25","55"},
//    		{"b.java","30","2","102","35","56"},
//    		{"c.java","50","4","101","14","67"},
//    		{"d.java","10","3","100","34","66"}};
	
    public void createPartControl(Composite parent) {
        IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new FillLayout());
        
        /*��ӹ���������λ������ѡ*/
//        createActions();
//        initializeToolBar();
//        initializeMenu(); 
        
        
        /*��д��3*/
        Table table = new Table(topComp,  SWT.BORDER);  
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        String columns[] = { "�ļ�", "VSM", "LSI", "NGD", "PMI", "JSM" };
        for(int i=0;i<columns.length;i++){
        	TableColumn tc = new TableColumn(table, SWT.CENTER);
        	tc.setText(columns[i]);
        	tc.setWidth(100);//�����п�  
            tc.setResizable(false);//�����п��ܸı�
        }
        
        Object objs[][] = {
        	{
        		"a.java", "60", "1", "103", "25", "55"
        	}, {
        		"b.java", "30", "2", "102", "35", "56"
        	}, {
        		"c.java", "50", "4", "101", "14", "67"
        	}, {
        		"d.java", "10", "3", "100", "34", "66"
        	} 
        };
        TableItem item = null;  
        for (int row = 0; row < objs.length; row++) {   
        	item = new TableItem(table, SWT.NONE);   
        	item.setText(0, row + 1 + "");   
        	for (int col = 0; col < table.getColumnCount() ; col++) {    
        		if (objs[row][col] != null)     
        			item.setText(col, objs[row][col].toString());          		
        	}
        }
        
        int[] flag={0,0,0,0,0,0};/*��ǵ�ǰ������״̬��1������ 0��δ���� -1������*/
        /*ѭ��д���������ڵ�������ʱi������*/
//        for(int i=0;i<columns.length;i++){
//        	table.getColumn(i).addSelectionListener(new SelectionAdapter(){
//            	public void widgetSelected(SelectionEvent e){
//            		//���������ļ�����������
//            		int cur=flag[i];/*��¼��ǰ�б��*/
//            		for(int i=0;i<flag.length;i++)
//            			flag[i]=0;/*��������б��*/
//            		if(cur==0||cur==-1){/*δ�����Ϊ����*/
//            			new TableColumnSorter().addSorter(table, table.getColumn(i));
//            			flag[i]=1;
//            		}else{/*Ϊ����*/
//            			new TableColumnSorter().removeSorter(table, table.getColumn(i));
//            			flag[i]=-1;
//            		}
//            	}
//            });
//        }
        
        
        table.getColumn(0).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[0];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==-1){/*δ�����Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(0));
        			flag[0]=1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(0));
        			flag[0]=-1;
        		}
        	}
        });
        table.getColumn(1).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[1];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==-1){/*δ�����Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(1));
        			flag[1]=1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(1));
        			flag[1]=-1;
        		}
        	}
        });
        table.getColumn(2).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[2];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==-1){/*δ�����Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(2));
        			flag[2]=1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(2));
        			flag[2]=-1;
        		}
        	}
        });
        table.getColumn(3).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[3];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==-1){/*δ�����Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(3));
        			flag[3]=1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(3));
        			flag[3]=-1;
        		}
        	}
        });
        table.getColumn(4).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[4];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==-1){/*δ�����Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(4));
        			flag[4]=1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(4));
        			flag[4]=-1;
        		}
        	}
        });
        table.getColumn(5).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[5];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==-1){/*δ�����Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(5));
        			flag[5]=1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(5));
        			flag[5]=-1;
        		}
        	}
        });
        
        
        /*��д��2*/
//        JFrame jf = new JFrame("�������");
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        /*�������ʾ������*/
//        Object rows[][] = {
//        		{
//        			"a.java", "60", "1", "103", "25", "55"
//        		}, {
//        			"b.java", "30", "2", "102", "35", "56"
//        		}, {
//        			"c.java", "50", "4", "101", "14", "67"
//        		}, {
//        			"d.java", "10", "3", "100", "34", "66"
//        		} 
//        };
//        String columns[] = { "�ļ�", "VSM", "LSI", "NGD", "PMI", "JSM" };
//        TableModel model = new DefaultTableModel(rows, columns);
//        JTable table = new JTable(model);
//        RowSorter sorter = new TableRowSorter(model);
//        table.setRowSorter(sorter);
//        JScrollPane pane = new JScrollPane(table);
//        jf.add(pane, BorderLayout.CENTER);
//        jf.setSize(300, 150);
//        jf.setVisible(true);
        

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
