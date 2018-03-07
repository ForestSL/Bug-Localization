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
    	/*添加工具栏操作*/
    	
    }
    
    private void initializeToolBar() {
        IToolBarManager tbm=getViewSite().getActionBars().getToolBarManager();
    }
    private void initializeMenu() {
        IMenuManager manager =getViewSite().getActionBars().getMenuManager();
    }
	
	
//    private Table table;
//    
//    /*测试数据，模拟bug分析后的数据*/
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
        
        /*添加工具栏：定位方法勾选*/
//        createActions();
//        initializeToolBar();
//        initializeMenu(); 
        
        
        /*新写法3*/
        Table table = new Table(topComp,  SWT.BORDER);  
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        String columns[] = { "文件", "VSM", "LSI", "NGD", "PMI", "JSM" };
        for(int i=0;i<columns.length;i++){
        	TableColumn tc = new TableColumn(table, SWT.CENTER);
        	tc.setText(columns[i]);
        	tc.setWidth(100);//设置列宽  
            tc.setResizable(false);//设置列宽不能改变
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
        
        int[] flag={0,0,0,0,0,0};/*标记当前列排序状态，1：正序 0：未排序 -1：倒序*/
        /*循环写法报错，关于调用排序时i的问题*/
//        for(int i=0;i<columns.length;i++){
//        	table.getColumn(i).addSelectionListener(new SelectionAdapter(){
//            	public void widgetSelected(SelectionEvent e){
//            		//调用排序文件，处理排序
//            		int cur=flag[i];/*记录当前列标记*/
//            		for(int i=0;i<flag.length;i++)
//            			flag[i]=0;/*清空其他列标记*/
//            		if(cur==0||cur==-1){/*未排序或为倒序*/
//            			new TableColumnSorter().addSorter(table, table.getColumn(i));
//            			flag[i]=1;
//            		}else{/*为正序*/
//            			new TableColumnSorter().removeSorter(table, table.getColumn(i));
//            			flag[i]=-1;
//            		}
//            	}
//            });
//        }
        
        
        table.getColumn(0).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//调用排序文件，处理排序
        		int cur=flag[0];/*记录当前列标记*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*清空其他列标记*/
        		if(cur==0||cur==-1){/*未排序或为倒序*/
        			new TableColumnSorter().addSorter(table, table.getColumn(0));
        			flag[0]=1;
        		}else{/*为正序*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(0));
        			flag[0]=-1;
        		}
        	}
        });
        table.getColumn(1).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//调用排序文件，处理排序
        		int cur=flag[1];/*记录当前列标记*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*清空其他列标记*/
        		if(cur==0||cur==-1){/*未排序或为倒序*/
        			new TableColumnSorter().addSorter(table, table.getColumn(1));
        			flag[1]=1;
        		}else{/*为正序*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(1));
        			flag[1]=-1;
        		}
        	}
        });
        table.getColumn(2).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//调用排序文件，处理排序
        		int cur=flag[2];/*记录当前列标记*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*清空其他列标记*/
        		if(cur==0||cur==-1){/*未排序或为倒序*/
        			new TableColumnSorter().addSorter(table, table.getColumn(2));
        			flag[2]=1;
        		}else{/*为正序*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(2));
        			flag[2]=-1;
        		}
        	}
        });
        table.getColumn(3).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//调用排序文件，处理排序
        		int cur=flag[3];/*记录当前列标记*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*清空其他列标记*/
        		if(cur==0||cur==-1){/*未排序或为倒序*/
        			new TableColumnSorter().addSorter(table, table.getColumn(3));
        			flag[3]=1;
        		}else{/*为正序*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(3));
        			flag[3]=-1;
        		}
        	}
        });
        table.getColumn(4).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//调用排序文件，处理排序
        		int cur=flag[4];/*记录当前列标记*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*清空其他列标记*/
        		if(cur==0||cur==-1){/*未排序或为倒序*/
        			new TableColumnSorter().addSorter(table, table.getColumn(4));
        			flag[4]=1;
        		}else{/*为正序*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(4));
        			flag[4]=-1;
        		}
        	}
        });
        table.getColumn(5).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//调用排序文件，处理排序
        		int cur=flag[5];/*记录当前列标记*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*清空其他列标记*/
        		if(cur==0||cur==-1){/*未排序或为倒序*/
        			new TableColumnSorter().addSorter(table, table.getColumn(5));
        			flag[5]=1;
        		}else{/*为正序*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(5));
        			flag[5]=-1;
        		}
        	}
        });
        
        
        /*新写法2*/
//        JFrame jf = new JFrame("排序测试");
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        /*表格中显示的数据*/
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
//        String columns[] = { "文件", "VSM", "LSI", "NGD", "PMI", "JSM" };
//        TableModel model = new DefaultTableModel(rows, columns);
//        JTable table = new JTable(model);
//        RowSorter sorter = new TableRowSorter(model);
//        table.setRowSorter(sorter);
//        JScrollPane pane = new JScrollPane(table);
//        jf.add(pane, BorderLayout.CENTER);
//        jf.setSize(300, 150);
//        jf.setVisible(true);
        

//        table=new Table(topComp,SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
//        table.setHeaderVisible(true);/*设置表头可见*/
//        table.setLinesVisible(true);/*设置线条可见*/
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
//        /*点击列头排序*/
//        
//        
//        /*鼠标点击事件监听*/
//        table.addMouseListener(new MouseAdapter() {
//            private EditorInput EditorInput = new EditorInput();
//
//            public void mouseDoubleClick(MouseEvent e) {
//                /* 根据不同列表项得到其相应的editorInput对象和editorID
//                 * 其中editorID指该编辑器在plugin.xml文件中设置id标识值*/
//                Table table = (Table) e.getSource();/*由MouseEvent得到列表对象*/
//                int row=table.getSelectionIndex();/*获取行*/
//                String fileName=table.getItem(row).getText(0);/*获取行的第一个元素，即文件名*/
//                /*System.out.println(fileName);*/
//                
//                IEditorInput editorInput = null;
//                String editorID = null;
//                editorInput = EditorInput;
//                editorID = "com.sl.v0.editors.Editor";
//              
//                /* 如果editorInput或editorID为空则中断返回*/
//                if (editorInput == null || editorID == null)
//                    return;
//                /* 取得IWorkbenchPage，并搜索使用editorInput对象对应的编辑器*/
//                IWorkbenchPage workbenchPage = getViewSite().getPage();
//                IEditorPart editor = workbenchPage.findEditor(editorInput);
//                // 如果此编辑器已经存在，则将它设为当前的编辑器（最顶端），否则
//                // 重新打开一个编辑器
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
