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
import com.sl.v0.buglocation.datas.Bug;
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
    	Bug temp = (Bug)obj;
    	if(temp.getBugId()!=null){
    		GlobalVar.bugID=temp.getBugId();
    		//text.setText("此处详细展示"+temp.getFile()+"文件通过"+temp.getMethod()+"方法定位的bug结果,bug数为"+temp.getCount());
    		String content="bug信息如下："+"\n";
    		content+=temp.getBugId()+"\n";
    		content+=temp.getSummary()+"\n";
    		content+=temp.getDescription();
    		text.setText(content);
    	}
    	else
    		text.setText("未选中！");
    }
    
    
}
