package com.sl.v0.views;

/*bug��λtable���չʾ��ͼ*/

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
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
import org.eclipse.swt.layout.FillLayout;
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
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.Activator;
import com.sl.v0.buglocation.DataCreator;
import com.sl.v0.buglocation.DealwithResult;
import com.sl.v0.buglocation.datas.BugModel;
import com.sl.v0.datas.Choice;
import com.sl.v0.datas.GlobalVar;
import com.sl.v0.datas.TableCell;
import com.sl.v0.swt.SWTResourceManager;
import com.sl.v0.editors.*;

public class View2 extends ViewPart {
    
    /*��ͼ����Ϣ����*/
    private ISelection selection;
    ArrayList myListeners = new ArrayList();
    ListViewer listViewer;
    
    public View2(){}
	
    Table table;
	private Choice choice=new Choice();
	
	/*���action*/
	private Action chooseAction;/*ѡ��ť*/
	private Action runAction;/*���а�ť*/
	
    private void createActions() {
    	
    	/*ѡ��ť*/
    	chooseAction = new Action() {
    		public void run() {
				//MessageDialog.openInformation(getSite().getShell(), "Choose", "Choose Dialog");
    			/*�Զ���Ի�������ѡ��bug��λ����*/
    			Shell shell = null;
    			ChooseDialog dialog = new ChooseDialog(shell);
    	        dialog.open();
    	    }
    	};
    	chooseAction.setText("Choose");
    	chooseAction.setToolTipText("Choose Dialog");
    	
    	/*���а�ť*/
    	runAction = new Action() {
    		public void run() {
				MessageDialog.openInformation(getSite().getShell(), "Run", "Bug Location Running����");
    			
				/*���ݹ�ѡ�ķ�������bug��λ ����ȫ�ֱ�����table����*/
				(new DealwithResult()).execute(GlobalVar.isMethod);
				//System.out.println(GlobalVar.objs[1][1]);
				
				/*����������ݵ����*/
				table.removeAll();
		        TableItem item = null;  
		        for (int row = 0; row < GlobalVar.objs.length; row++) {   
		        	item = new TableItem(table, SWT.NONE);   
		        	item.setText(0, row + 1 + "");   
		        	for (int col = 0; col < table.getColumnCount() ; col++) {    
		        		if (GlobalVar.objs[row][col] != null){     
		        			//System.out.println(GlobalVar.objs[row][col]);
		        			item.setText(col, GlobalVar.objs[row][col]);          		
		        		}
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
        /*��ʽ����*/
        RowLayout rowLayout = new RowLayout();
        rowLayout.type=SWT.VERTICAL; 
        topComp.setLayout(new FillLayout());
        
        /*��ӹ���������λ������ѡ*/
        createActions();
        initializeToolBar();
        initializeMenu(); 
               
        /*table��������������*/
        table = new Table(topComp,  SWT.BORDER| SWT.V_SCROLL | SWT.H_SCROLL);  
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new RowData(650, 100));  
        
        /*��ʼ�����*/
        /*��һ�����뿼�ǹ�ѡ���*/
        TableColumn t = new TableColumn(table, SWT.CENTER);
    	t.setText(GlobalVar.columns[0]);
    	t.setWidth(150);
        t.setResizable(false);//�����п��ܸı�       
        /*���ݹ�ѡ�������������*/
        for(int i=1;i<GlobalVar.columns.length;i++){
        	TableColumn tc = new TableColumn(table, SWT.CENTER);
        	tc.setText(GlobalVar.columns[i]);      	
        	/*��ȡbug������ѡ���*/
        	if(choice.methodChoice(i-1)==true)
        		tc.setWidth(100);/*�����п�*/  
        	else
        		tc.setWidth(0);/*����Ϊ��ѡ����*/
            tc.setResizable(false);//�����п��ܸı�
        }
        
        /*��ͼ����Ϣ����(���һ����ͼ����Ϊ�޷���table�ϼ���)*/
    	listViewer = new ListViewer(topComp,SWT.BORDER| SWT.V_SCROLL | SWT.H_SCROLL);
    	listViewer.setLabelProvider(new View3LabelProvider());
    	listViewer.setContentProvider(new View3ContentProvider());
    	listViewer.setInput(new BugModel());    	
    	this.getSite().setSelectionProvider(listViewer);
        
        /*�������*/
        int[] flag={0,0,0,0,0,0};/*��ǵ�ǰ������״̬��1������ 0��δ���� -1������*/               
        table.getColumn(1).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[1];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==1){/*δ�����Ϊ����*/
        			new TableColumnSorter().removeSorter(table, table.getColumn(1));
        			flag[1]=-1;
        		}else{/*Ϊ����*/
        			new TableColumnSorter().addSorter(table, table.getColumn(1));
        			flag[1]=1;
        		}
        	}
        });
        table.getColumn(2).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[2];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==1){
        			new TableColumnSorter().removeSorter(table, table.getColumn(2));
        			flag[2]=-1;
        		}else{
        			new TableColumnSorter().addSorter(table, table.getColumn(2));
        			flag[2]=1;
        		}
        	}
        });
        table.getColumn(3).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[3];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==1){
        			new TableColumnSorter().removeSorter(table, table.getColumn(3));
        			flag[3]=-1;
        		}else{
        			new TableColumnSorter().addSorter(table, table.getColumn(3));
        			flag[3]=1;
        		}
        	}
        });
        table.getColumn(4).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[4];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==1){
        			new TableColumnSorter().removeSorter(table, table.getColumn(4));
        			flag[4]=-1;
        		}else{
        			new TableColumnSorter().addSorter(table, table.getColumn(4));
        			flag[4]=1;
        		}
        	}
        });
        table.getColumn(5).addSelectionListener(new SelectionAdapter(){
        	public void widgetSelected(SelectionEvent e){
        		//���������ļ�����������
        		int cur=flag[5];/*��¼��ǰ�б��*/
        		for(int i=0;i<flag.length;i++)
        			flag[i]=0;/*��������б��*/
        		if(cur==0||cur==1){
        			new TableColumnSorter().removeSorter(table, table.getColumn(5));
        			flag[5]=-1;
        		}else{
        			new TableColumnSorter().addSorter(table, table.getColumn(5));
        			flag[5]=1;
        		}
        	}
        });
        /*�����������*/
        
        /*���˫���¼����༭�����ļ�*/
        table.addMouseListener(new MouseAdapter() {

            public void mouseDoubleClick(MouseEvent e) {
                /* ���ݲ�ͬ�б���õ�����Ӧ��editorInput�����editorID
                 * ����editorIDָ�ñ༭����plugin.xml�ļ�������id��ʶֵ*/
            	
            	//String filename=null;
            	/*��õ�Ԫ���λ��*/
        		TableItem[] items = table.getItems();
        		Point pt = new Point(e.x, e.y);
        		for (int i = 0; i < items.length; i++) {
        			for (int j = 0; j < table.getColumnCount(); j++) {
        				Rectangle rect = items[i].getBounds(j);
        				if (rect.contains(pt)) {
        					/*��ȡ�������ļ�����*/
        					TableItem item = table.getItem(i);
        					GlobalVar.filename = item.getText(0);/*�����������仯������ÿ��ȡָ���е�һ����Ԫ������*/       				
        				}
        			}
        		}
        		
        		//System.out.println(GlobalVar.filename);
                
                /*TODO:�����ļ����ڱ༭���д�
                 * ȫ·�� �ɴ� �ɱ��� ��ʼû����
                 * �������⣺�ļ�����java��ʽ �ظ�����ͬ�ļ�����ʾ*/
        		// ���ݲ�ͬ�б���õ�����Ӧ��editorInput�����editorID������
                // editorIDָ�ñ༭����plugin.xml�ļ�������id��ʶֵ
                String listStr = GlobalVar.filename;
                IEditorInput editorInput = new EditorInput();
                String editorID = "com.sl.v0.editors.Editor";
                
                // ���editorInput��editorIDΪ�����жϷ���
                if (editorInput == null || editorID == null)
                    return;
                // ȡ��IWorkbenchPage��������ʹ��editorInput�����Ӧ�ı༭��
                IWorkbenchPage workbenchPage = getViewSite().getPage();
                IEditorPart editor = workbenchPage.findEditor(editorInput);

                try {
                	workbenchPage.openEditor(editorInput, editorID);
                } catch (PartInitException e2) {
                	e2.printStackTrace();
                }
        		
        		
        		
            }
        });
        
    }
    
    
    @Override
    public void setFocus() {}
    
  
    /*�Զ��年ѡ�� ѡ��bug��λ����*/
    public class ChooseDialog extends Dialog {
    	
        private Button VsmButton, LsiButton, JsmButton, NgdButton, PmiButton;
    	
        public ChooseDialog(Shell parentShell) {
        	super(parentShell);
        }
      
        /*����������ﹹ��Dialog�еĽ�������*/
        @Override
        protected Control createDialogArea(Composite parent) {
          
        	Composite topComp = new Composite(parent, SWT.NONE);
        	topComp.setLayout(new RowLayout());/*Ӧ��RowLayout���*/
          
        	new Label(topComp, SWT.NONE).setText("�빴ѡ��Ҫ��bug��λ������");
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
        
        /* �������ȷ����ť����ֵ���浽Choice������*/
        protected void buttonPressed(int buttonId) {
        	/*�������µ�bug�б�*/
        	listViewer.setInput(new BugModel()); 
        	
            if (buttonId == IDialogConstants.OK_ID) {
                if (choice == null)
                     choice = new Choice();
                
                boolean[] cur={VsmButton.getSelection(),LsiButton.getSelection(),JsmButton.getSelection(),
                		NgdButton.getSelection(),PmiButton.getSelection()};
                choice.setChoose(cur);

            }
            
            /*���±��*/
            /*ɾ��ԭ������ݼ���*/
            table.removeAll();
            int col=table.getColumnCount();
            for(int i=0;i<col;i++)
            	table.getColumns()[table.getColumnOrder()[0]].dispose();
            
            /*��һ�����뿼�ǹ�ѡ���*/
            TableColumn t = new TableColumn(table, SWT.CENTER);
        	t.setText(GlobalVar.columns[0]);
        	t.setWidth(150);
            t.setResizable(false);//�����п��ܸı�            
            /*���ݹ�ѡ�������������*/
            for(int i=1;i<GlobalVar.columns.length;i++){
            	TableColumn tc = new TableColumn(table, SWT.CENTER);
            	tc.setText(GlobalVar.columns[i]);
            	
            	/*��ȡbug������ѡ���*/
            	/*��ѡ������洢Ϊȫ�� ������ʹ��*/
            	if(choice.methodChoice(i-1)==true){
            		GlobalVar.isMethod[i-1]=true;
            		tc.setWidth(100);/*�����п�*/  
            	}else{
            		GlobalVar.isMethod[i-1]=false;
            		tc.setWidth(0);/*����Ϊ��ѡ����*/
            	}
            	
                tc.setResizable(false);//�����п��ܸı�
            }
            
            /*���������������Է�װ���������ǹ���ʧЧ��*/
            int[] flag={0,0,0,0,0,0};
            table.getColumn(1).addSelectionListener(new SelectionAdapter(){
            	public void widgetSelected(SelectionEvent e){
            		//���������ļ�����������
            		int cur=flag[1];/*��¼��ǰ�б��*/
            		for(int i=0;i<flag.length;i++)
            			flag[i]=0;/*��������б��*/
            		if(cur==0||cur==1){
            			new TableColumnSorter().removeSorter(table, table.getColumn(1));
            			flag[1]=-1;
            		}else{
            			new TableColumnSorter().addSorter(table, table.getColumn(1));
            			flag[1]=1;
            		}
            	}
            });
            table.getColumn(2).addSelectionListener(new SelectionAdapter(){
            	public void widgetSelected(SelectionEvent e){
            		//���������ļ�����������
            		int cur=flag[2];/*��¼��ǰ�б��*/
            		for(int i=0;i<flag.length;i++)
            			flag[i]=0;/*��������б��*/
            		if(cur==0||cur==1){
            			new TableColumnSorter().removeSorter(table, table.getColumn(2));
            			flag[2]=-1;
            		}else{
            			new TableColumnSorter().addSorter(table, table.getColumn(2));
            			flag[2]=1;
            		}
            	}
            });
            table.getColumn(3).addSelectionListener(new SelectionAdapter(){
            	public void widgetSelected(SelectionEvent e){
            		//���������ļ�����������
            		int cur=flag[3];/*��¼��ǰ�б��*/
            		for(int i=0;i<flag.length;i++)
            			flag[i]=0;/*��������б��*/
            		if(cur==0||cur==1){
            			new TableColumnSorter().removeSorter(table, table.getColumn(3));
            			flag[3]=-1;
            		}else{
            			new TableColumnSorter().addSorter(table, table.getColumn(3));
            			flag[3]=1;
            		}
            	}
            });
            table.getColumn(4).addSelectionListener(new SelectionAdapter(){
            	public void widgetSelected(SelectionEvent e){
            		//���������ļ�����������
            		int cur=flag[4];/*��¼��ǰ�б��*/
            		for(int i=0;i<flag.length;i++)
            			flag[i]=0;/*��������б��*/
            		if(cur==0||cur==1){
            			new TableColumnSorter().removeSorter(table, table.getColumn(4));
            			flag[4]=-1;
            		}else{
            			new TableColumnSorter().addSorter(table, table.getColumn(4));
            			flag[4]=1;
            		}
            	}
            });
            table.getColumn(5).addSelectionListener(new SelectionAdapter(){
            	public void widgetSelected(SelectionEvent e){
            		//���������ļ�����������
            		int cur=flag[5];/*��¼��ǰ�б��*/
            		for(int i=0;i<flag.length;i++)
            			flag[i]=0;/*��������б��*/
            		if(cur==0||cur==1){
            			new TableColumnSorter().removeSorter(table, table.getColumn(5));
            			flag[5]=-1;
            		}else{
            			new TableColumnSorter().addSorter(table, table.getColumn(5));
            			flag[5]=1;
            		}
            	}
            });
            /*���������ӽ���*/
            
            super.buttonPressed(buttonId);
        }
    }

    
}
