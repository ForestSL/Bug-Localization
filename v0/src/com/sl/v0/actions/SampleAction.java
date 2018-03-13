package com.sl.v0.actions;

import javax.swing.JOptionPane;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import com.sl.v0.actions.panel.DownloadCode;
import com.sl.v0.actions.panel.InputDialog1;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		Object[] options = {"项目代码下载","bug报告下载"};
		int response=JOptionPane.showOptionDialog(null, "请用户选择所需操作：", "操作选择",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		InputDialog1 input1=new InputDialog1();
		
		if(response==0){
			/*仿照流程*/
			String sourcecode=input1.getcode();/*sourcecode为项目源代码网址*/
			//JOptionPane.showMessageDialog(null, "源代码"+sourcecode+"下载成功");
			/*自动在eclipse中打开项目*/
		}
		if(response==1){
			String report=input1.getreport();
			//JOptionPane.showMessageDialog(null, "bug报告"+report+"下载成功");
		}
		/*bug定位按钮单独拎出 不混杂在代码下载中*/
		/*JOptionPane.showMessageDialog(null, "开始bug定位……");*/
		
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}