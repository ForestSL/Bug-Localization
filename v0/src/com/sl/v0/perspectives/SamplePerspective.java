package com.sl.v0.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.sl.v0.datas.GlobalVar;
import com.sl.v0.views.View2;
import com.sl.v0.views.View3;

public class SamplePerspective implements IPerspectiveFactory {
	
    /*����IPageLayout������͸��ͼ�Ĳ��ֹ�����*/
    public void createInitialLayout(IPageLayout layout) {
    	
        /*�õ���͸��ͼ�ı༭�ռ��ʶ*/
        String editorArea = layout.getEditorArea();
        //GlobalVar.editorArea=editorArea;
        
        /*��͸��ͼ�󲿴���һ���ռ䣬����PackageExplorer�������С�*/
        IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.15f, editorArea);
        left.addView("org.eclipse.jdt.ui.PackageExplorer");
        
        /*������ͼ2����ͼ3�����뵽͸��ͼ�ĵײ�*/
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.5f, editorArea);
        bottom.addView("com.sl.v0.views.View2");/*�������ǰ���ͼ��idȡ�ɺ���ȫ��һ��������Ҳ����������д��*/
        IFolderLayout bottomFolder = layout.createFolder("bottom2", 
        		IPageLayout.BOTTOM, 0.5f,"bottom");
        bottomFolder.addView("com.sl.v0.views.View3");
        
        /*�������ͼOutline����͸��ͼ�ұ�*/
        IFolderLayout right = layout.createFolder(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.8f, editorArea);
        right.addView("org.eclipse.ui.views.ContentOutline");
        
        /* ����ǰ�����actionset�����˵�����������ť�����뵽��͸��ͼ��
         * ��Ҫ��plugin.xml�ļ���action�����н�visible="false"�ſ��ó�Ч����
         * ��ʱ������͸��ͼ��action���õ����˵�����������ť����������ڽ����ϣ�ֻ�д򿪱�͸��ͼ�Ż���֡�*/
        layout.addActionSet("com.sl.v0.actionSet");/* ����ΪactionSet��plugin.xml�е�id��ʶ*/
    }
}
