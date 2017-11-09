package com.sl.v0.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.part.ViewPart;

import com.sl.v0.editors.EditorInput;

public class View1 extends ViewPart {
    private List list; /*List是org.eclipse.swt.widgets.List;包下的*/
    
    public void createPartControl(Composite parent) {
        IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new FillLayout());
        list = new List(topComp, SWT.BORDER);
        list.add("项目文件1");
        list.add("项目文件2");
        list.add("项目文件3");
        
        /*鼠标点击事件监听*/
        list.addMouseListener(new MouseAdapter() {
            private EditorInput EditorInput = new EditorInput();

            public void mouseDoubleClick(MouseEvent e) {
                // 根据不同列表项得到其相应的editorInput对象和editorID，其中
                // editorID指该编辑器在plugin.xml文件中设置id标识值
                List list = (List) e.getSource();// 由MouseEvent得到列表对象
                String listStr = list.getSelection()[0];// 得到当前列表项的字符
                IEditorInput editorInput = null;
                String editorID = null;
                editorInput = EditorInput;
                editorID = "com.sl.v0.editors.Editor";
              
                // 如果editorInput或editorID为空则中断返回
                if (editorInput == null || editorID == null)
                    return;
                // 取得IWorkbenchPage，并搜索使用editorInput对象对应的编辑器
                IWorkbenchPage workbenchPage = getViewSite().getPage();
                IEditorPart editor = workbenchPage.findEditor(editorInput);
                // 如果此编辑器已经存在，则将它设为当前的编辑器（最顶端），否则
                // 重新打开一个编辑器
                if (editor != null) {
                    workbenchPage.bringToTop(editor);
                } else {
                    try {
                        workbenchPage.openEditor(editorInput, editorID);
                    } catch (PartInitException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        
    }
    @Override
    public void setFocus() {}
}
