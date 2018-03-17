package com.sl.v0.views;

/*详细bug结果展示视图*/

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
	
	/*视图间消息传递*/
    @SuppressWarnings("unchecked")
    ArrayList myListeners;
    public View3(){
    	super();
    }
	
    public void createPartControl(Composite parent) {
        Composite topComp = new Composite(parent,SWT.NONE);
        topComp.setLayout(new FillLayout());
        text = new Text(topComp,SWT.BORDER | SWT.MULTI);/*SWT.MULTI标记可以换行*/
//        if(GlobalVar.cell.getFile()==null)
//        	text.setText("未选中需要展示的bug文件！");
//        else
//        	text.setText("此处详细展示"+GlobalVar.cell.getFile()+
//        		"文件通过"+GlobalVar.cell.getMethod()+
//        		"方法定位的bug结果,bug数为"+GlobalVar.cell.getCount());
        
        /*视图间消息传递*/
        this.getSite().getPage().addSelectionListener("com.sl.v0.views.View2",(ISelectionListener)this);
        
    }
    
    public void setFocus() {}
    
    /*视图间消息传递*/
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    	IStructuredSelection structuredSelection = (IStructuredSelection)selection;
    	Object obj = structuredSelection.getFirstElement();
    	TableCell temp = (TableCell)obj;
    	if(temp.getFile() != null&&temp.getFile()!="文件"){
    		//text.setText("此处详细展示"+temp.getFile()+"文件通过"+temp.getMethod()+"方法定位的bug结果,bug数为"+temp.getCount());
    		String file=temp.getFile();
    		String method=temp.getMethod();
    		/*根据VsmResult显示具体bug信息*/
    		ArrayList<String> buglist=new ArrayList<String>();
    		buglist=DealwithResult.VsmResult.get(file);
    		ArrayList<String> buginfo=new ArrayList<String>();
    		buginfo=(new BaseFunction()).ReadAllLines(DataCreator.ReportFolderPath+"Buglist.txt");
    		String content="bug信息如下："+"\n";
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
    		text.setText("未选中！");
    }
    
    
}
