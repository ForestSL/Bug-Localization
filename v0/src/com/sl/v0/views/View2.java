package com.sl.v0.views;

/*bug定位table表格展示视图*/

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.buglocation.DataCreator;
import com.sl.v0.datas.Choice;
import com.sl.v0.datas.GlobalVar;
import com.sl.v0.datas.TableCell;
import com.sl.v0.datas.TableCellModel;
import com.sl.v0.swt.SWTResourceManager;
import com.sl.v0.editors.*;

public class View2 extends ViewPart {
    
    /*视图间消息传递*/
    private ISelection selection;
    ArrayList myListeners = new ArrayList();
    ListViewer listViewer;
    
    public View2(){}
	
    Table table;
	private Choice choice=new Choice();
	
	/*添加action*/
	private Action chooseAction;/*选择按钮*/
	private Action runAction;/*运行按钮*/
	
    private void createActions() {
    	
    	/*选择按钮*/
    	chooseAction = new Action() {
    		public void run() {
				//MessageDialog.openInformation(getSite().getShell(), "Choose", "Choose Dialog");
    			/*自定义对话框用于选择bug定位方法*/
    			Shell shell = null;
    			ChooseDialog dialog = new ChooseDialog(shell);
    	        dialog.open();
    	    }
    	};
    	chooseAction.setText("Choose");
    	chooseAction.setToolTipText("Choose Dialog");
    	
    	/*运行按钮*/
    	runAction = new Action() {
    		public void run() {
				MessageDialog.openInformation(getSite().getShell(), "Run", "Bug Location Running……");
    			
				/*TODO:bug定位运行操作 更新全局变量中table数据*/
				//List<File> fileList=(new DataCreator()).GetFiles("E:\\GithubCode\\cxf.git\\cxf-3.1.0");
				
				/*运行填充数据到表格*/
				table.removeAll();
		        TableItem item = null;  
		        for (int row = 0; row < GlobalVar.objs.length; row++) {   
		        	item = new TableItem(table, SWT.NONE);   
		        	item.setText(0, row + 1 + "");   
		        	for (int col = 0; col < table.getColumnCount() ; col++) {    
		        		if (GlobalVar.objs[row][col] != null)     
		        			item.setText(col, GlobalVar.objs[row][col].toString());          		
		        	}
		        }
    		
    	    }
    	};
    	runAction.setText("Run");
    	runAction.setToolTipText("Run Dialog");
    }
    
    private void initializeToolBar() {
    	IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
    	toolBarManager.add(chooseAction);
    	toolBarManager.add(runAction);
    }
    private void initializeMenu() {
    	IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
        menuManager.add(chooseAction);
        menuManager.add(runAction);
    }
	
    public void createPartControl(Composite parent) {
    	    	
        IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        /*格式布局*/
        RowLayout rowLayout = new RowLayout();
        rowLayout.type=SWT.VERTICAL; 
        topComp.setLayout(rowLayout);
        
        /*添加工具栏：定位方法勾选*/
        createActions();
        initializeToolBar();
        initializeMenu(); 
               
        /*table数据正倒序排序*/
        table = new Table(topComp,  SWT.BORDER);  
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new RowData(600, 100));  
        
        /*初始化表格*/
        /*第一列无须考虑勾选情况*/
        TableColumn t = new TableColumn(table, SWT.CENTER);
    	t.setText(GlobalVar.columns[0]);
    	t.setWidth(150);
        t.setResizable(false);//设置列宽不能改变       
        /*根据勾选情况创建其他列*/
        for(int i=1;i<GlobalVar.columns.length;i++){
        	TableColumn tc = new TableColumn(table, SWT.CENTER);
        	tc.setText(GlobalVar.columns[i]);      	
        	/*获取bug方法勾选情况*/
        	if(choice.methodChoice(i-1)==true)
        		tc.setWidth(100);/*设置列宽*/  
        	else
        		tc.setWidth(0);/*隐藏为勾选的列*/
            tc.setResizable(false);//设置列宽不能改变
        }
        
        /*视图间消息传递(多加一个视图，因为无法在table上监听)*/
    	listViewer = new ListViewer(topComp,SWT.BORDER);
    	listViewer.setLabelProvider(new View3LabelProvider());
    	listViewer.setContentProvider(new View3ContentProvider());
    	listViewer.setInput(new TableCellModel());    	
    	this.getSite().setSelectionProvider(listViewer);
        
        /*排序操作*/
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
        /*排序操作结束*/
        

        /*鼠标单击事件：显示具体bug信息*/
        //final TableCursor cursor = new TableCursor(table, SWT.NONE);
        table.addMouseListener(new MouseAdapter(){
        	@Override
            public void mouseDown(MouseEvent e) {
        		
//        		int i = table.getSelectionIndex();
//        		int j = cursor.getColumn();
//        		//System.out.println(i+" "+j);
        		
        		/*获得单元格的位置*/
        		TableItem[] items = table.getItems();
        		Point pt = new Point(e.x, e.y);
        		for (int i = 0; i < items.length; i++) {
        			for (int j = 0; j < table.getColumnCount(); j++) {
        				Rectangle rect = items[i].getBounds(j);
        				if (rect.contains(pt)) {
        					/*获取列名（方法名）*/
        					String method=GlobalVar.columns[j];
        					GlobalVar.cell.setMethod(method);
        					/*获取行名（文件名）*/
        					TableItem item = table.getItem(i);
        					String file = item.getText(0);/*由于行排序后变化，所以每次取指定行第一个单元格数据*/
        					GlobalVar.cell.setFile(file);
        					String data = item.getText(j);
        					GlobalVar.cell.setCount(data);
        	
        					//System.out.println("此处详细展示"+file+"文件通过"+method+"方法定位的bug结果,bug数为"+data );
        				
        					/*更新选中值*/
        					listViewer.setInput(new TableCellModel());  
        					
        					/*设置选中单元格背景色*/
        					//item.setBackground(j,SWTResourceManager.getColor(SWT.COLOR_BLUE));
        				}
        			}
        		}
             
        	}
        });
        
        /*鼠标双击事件：编辑器打开文件*/
        table.addMouseListener(new MouseAdapter() {

            public void mouseDoubleClick(MouseEvent e) {
                /* 根据不同列表项得到其相应的editorInput对象和editorID
                 * 其中editorID指该编辑器在plugin.xml文件中设置id标识值*/
            	
            	//String filename=null;
            	/*获得单元格的位置*/
        		TableItem[] items = table.getItems();
        		Point pt = new Point(e.x, e.y);
        		for (int i = 0; i < items.length; i++) {
        			for (int j = 0; j < table.getColumnCount(); j++) {
        				Rectangle rect = items[i].getBounds(j);
        				if (rect.contains(pt)) {
        					/*获取行名（文件名）*/
        					TableItem item = table.getItem(i);
        					GlobalVar.filename = item.getText(0);/*由于行排序后变化，所以每次取指定行第一个单元格数据*/       				
        				}
        			}
        		}
        		
        		//System.out.println(GlobalVar.filename);
                
                /*TODO:根据文件名在编辑器中打开*/
        		
        		
            }
        });
        
    }
    
    
    @Override
    public void setFocus() {}
    
  
    /*自定义勾选框 选择bug定位方法*/
    public class ChooseDialog extends Dialog {
    	
        private Button VsmButton, LsiButton, JsmButton, NgdButton, PmiButton;
    	
        public ChooseDialog(Shell parentShell) {
        	super(parentShell);
        }
      
        /*在这个方法里构建Dialog中的界面内容*/
        @Override
        protected Control createDialogArea(Composite parent) {
          
        	Composite topComp = new Composite(parent, SWT.NONE);
        	topComp.setLayout(new RowLayout());/*应用RowLayout面局*/
          
        	new Label(topComp, SWT.NONE).setText("请勾选需要的bug定位方法：");
        	Composite c = new Composite(topComp, SWT.None);
        	c.setLayout(new RowLayout());
        	VsmButton = new Button(c, SWT.CHECK);
        	VsmButton.setText("VSM");
        	LsiButton = new Button(c, SWT.CHECK);
        	LsiButton.setText("LSI");
        	JsmButton = new Button(c, SWT.CHECK);
        	JsmButton.setText("JSM");
        	NgdButton = new Button(c, SWT.CHECK);
        	NgdButton.setText("NGD");
        	PmiButton = new Button(c, SWT.CHECK);
        	PmiButton.setText("PMI");
         
        	return topComp;

        }
        
        /* 如果单击确定按钮，则将值保存到Choice对象中*/
        protected void buttonPressed(int buttonId) {
            if (buttonId == IDialogConstants.OK_ID) {
                if (choice == null)
                     choice = new Choice();
                
                boolean[] cur={VsmButton.getSelection(),LsiButton.getSelection(),JsmButton.getSelection(),
                		NgdButton.getSelection(),PmiButton.getSelection()};
                choice.setChoose(cur);

            }
            
            /*更新表格*/
            /*删除原表格数据及列*/
            table.removeAll();
            int col=table.getColumnCount();
            for(int i=0;i<col;i++)
            	table.getColumns()[table.getColumnOrder()[0]].dispose();
            
            /*第一列无须考虑勾选情况*/
            TableColumn t = new TableColumn(table, SWT.CENTER);
        	t.setText(GlobalVar.columns[0]);
        	t.setWidth(100);
            t.setResizable(false);//设置列宽不能改变            
            /*根据勾选情况创建其他列*/
            for(int i=1;i<GlobalVar.columns.length;i++){
            	TableColumn tc = new TableColumn(table, SWT.CENTER);
            	tc.setText(GlobalVar.columns[i]);
            	
            	/*获取bug方法勾选情况*/
            	/*TODO:勾选方法需存储为全局 供后续使用*/
            	if(choice.methodChoice(i-1)==true)
            		tc.setWidth(100);/*设置列宽*/  
            	else
            		tc.setWidth(0);/*隐藏为勾选的列*/
            	
                tc.setResizable(false);//设置列宽不能改变
            }
            
            /*添加排序操作（尝试封装函数，但是功能失效）*/
            int[] flag={0,0,0,0,0,0};
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
            /*排序操作添加结束*/
            
            super.buttonPressed(buttonId);
        }
    }

    
}
