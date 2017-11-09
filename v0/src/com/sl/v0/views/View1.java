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
    private List list; /*List��org.eclipse.swt.widgets.List;���µ�*/
    
    public void createPartControl(Composite parent) {
        IWorkbenchHelpSystem help = PlatformUI.getWorkbench().getHelpSystem();
        help.setHelp(parent, "com.sl.v0.buttonHelpId");
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new FillLayout());
        list = new List(topComp, SWT.BORDER);
        list.add("��Ŀ�ļ�1");
        list.add("��Ŀ�ļ�2");
        list.add("��Ŀ�ļ�3");
        
        /*������¼�����*/
        list.addMouseListener(new MouseAdapter() {
            private EditorInput EditorInput = new EditorInput();

            public void mouseDoubleClick(MouseEvent e) {
                // ���ݲ�ͬ�б���õ�����Ӧ��editorInput�����editorID������
                // editorIDָ�ñ༭����plugin.xml�ļ�������id��ʶֵ
                List list = (List) e.getSource();// ��MouseEvent�õ��б����
                String listStr = list.getSelection()[0];// �õ���ǰ�б�����ַ�
                IEditorInput editorInput = null;
                String editorID = null;
                editorInput = EditorInput;
                editorID = "com.sl.v0.editors.Editor";
              
                // ���editorInput��editorIDΪ�����жϷ���
                if (editorInput == null || editorID == null)
                    return;
                // ȡ��IWorkbenchPage��������ʹ��editorInput�����Ӧ�ı༭��
                IWorkbenchPage workbenchPage = getViewSite().getPage();
                IEditorPart editor = workbenchPage.findEditor(editorInput);
                // ����˱༭���Ѿ����ڣ�������Ϊ��ǰ�ı༭������ˣ�������
                // ���´�һ���༭��
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
