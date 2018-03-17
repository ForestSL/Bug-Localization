package com.sl.v0.views;

/*��ϸbug���չʾ��ͼ*/

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.buglocation.BaseFunction;
import com.sl.v0.buglocation.DataCreator;
import com.sl.v0.buglocation.DealwithResult;
import com.sl.v0.datas.GlobalVar;
import com.sl.v0.datas.TableCell;

public class View3 extends ViewPart implements ISelectionListener{
	
	Text text;
	
	/*��ͼ����Ϣ����*/
    @SuppressWarnings("unchecked")
    ArrayList myListeners;
    public View3(){
    	super();
    }
	
    public void createPartControl(Composite parent) {
        Composite topComp = new Composite(parent,SWT.NONE);
        topComp.setLayout(new FillLayout());
        text = new Text(topComp,SWT.BORDER | SWT.MULTI);/*SWT.MULTI��ǿ��Ի���*/
//        if(GlobalVar.cell.getFile()==null)
//        	text.setText("δѡ����Ҫչʾ��bug�ļ���");
//        else
//        	text.setText("�˴���ϸչʾ"+GlobalVar.cell.getFile()+
//        		"�ļ�ͨ��"+GlobalVar.cell.getMethod()+
//        		"������λ��bug���,bug��Ϊ"+GlobalVar.cell.getCount());
        
        /*��ͼ����Ϣ����*/
        this.getSite().getPage().addSelectionListener("com.sl.v0.views.View2",(ISelectionListener)this);
        
    }
    
    public void setFocus() {}
    
    /*��ͼ����Ϣ����*/
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    	IStructuredSelection structuredSelection = (IStructuredSelection)selection;
    	Object obj = structuredSelection.getFirstElement();
    	TableCell temp = (TableCell)obj;
    	if(temp.getFile() != null&&temp.getFile()!="�ļ�"){
    		//text.setText("�˴���ϸչʾ"+temp.getFile()+"�ļ�ͨ��"+temp.getMethod()+"������λ��bug���,bug��Ϊ"+temp.getCount());
    		String file=temp.getFile();
    		String method=temp.getMethod();
    		/*����VsmResult��ʾ����bug��Ϣ*/
    		ArrayList<String> buglist=new ArrayList<String>();
    		buglist=DealwithResult.VsmResult.get(file);
    		ArrayList<String> buginfo=new ArrayList<String>();
    		buginfo=(new BaseFunction()).ReadAllLines(DataCreator.ReportFolderPath+"Buglist.txt");
    		String content="bug��Ϣ���£�"+"\n";
    		for(int i=0;i<buglist.size();i++){
    			for(int j=0;j<buginfo.size();j++){
    				String[] info=buginfo.get(j).split("#####");
    				if(buglist.get(i).equals(info[0])){
    					String[] con=buginfo.get(i).split("#####");
    					content+="bugID:"+con[0]+"\n";
    					content+="bugSummary:"+con[1]+"\n";
    					content+="bugDescription:"+con[2]+"\n";
    					content+="\n";
    				}
    			}
    		}
    		text.setText(content);
    	}
    	else
    		text.setText("δѡ�У�");
    }
    
    
}
