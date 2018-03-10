package com.sl.v0.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.sl.v0.datas.GlobalVar;
import com.sl.v0.views.View2;
import com.sl.v0.views.View3;

public class SamplePerspective implements IPerspectiveFactory {
	
    /*参数IPageLayout是用于透视图的布局管理器*/
    public void createInitialLayout(IPageLayout layout) {
    	
        /*得到本透视图的编辑空间标识*/
        String editorArea = layout.getEditorArea();
        //GlobalVar.editorArea=editorArea;
        
        /*在透视图左部创建一个空间，并将PackageExplorer放入其中。*/
        IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.15f, editorArea);
        left.addView("org.eclipse.jdt.ui.PackageExplorer");
        
        /*将“视图2、视图3”加入到透视图的底部*/
        IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.5f, editorArea);
        bottom.addView("com.sl.v0.views.View2");/*由于我们把视图的id取成和类全名一样，所以也可以用这种写法*/
        IFolderLayout bottomFolder = layout.createFolder("bottom2", 
        		IPageLayout.BOTTOM, 0.5f,"bottom");
        bottomFolder.addView("com.sl.v0.views.View3");
        
        /*将大纲视图Outline放入透视图右边*/
        IFolderLayout right = layout.createFolder(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.8f, editorArea);
        right.addView("org.eclipse.ui.views.ContentOutline");
        
        /* 将以前定义的actionset（主菜单、工具栏按钮）加入到本透视图。
         * 这要在plugin.xml文件的action设置中将visible="false"才看得出效果，
         * 这时打开其他透视图，action设置的主菜单、工具栏按钮将不会出现在界面上，只有打开本透视图才会出现。*/
        layout.addActionSet("com.sl.v0.actionSet");/* 参数为actionSet在plugin.xml中的id标识*/
    }
}
