package com.sl.v0.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInput implements IEditorInput {

    /* 返回true，则打开该编辑器后它会出现在Eclipse主菜单“文件” 
     * 最下部的最近打开的文档栏中。返回flase则不出现在其中。*/
    @Override
    public boolean exists() {
        return true;
    }

    /* 编辑器标题栏的图标，不过它还需要在Editor中
     * 用 setTitleImage方法设置，才能出现在标题栏中。*/
    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    /* 编辑器标题栏的显示名称，和上面的getImageDescriptor一样也要 
     * 在ChinaEditor中用setPartName方法设置，才能出现在标题栏中。*/
    @Override
    public String getName() {
        return "java文件编辑器";
    }

    /* 编辑器标题栏的小黄条提示文字，不需象getName那样在Editor中再设置*/
    @Override
    public String getToolTipText() {
        return "java文件编辑器";
    }

    /* 返回一个可以用做保存本编辑输入数据状态的对象*/
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    /* 得到一个编辑器的适配器*/
    @Override
    public Object getAdapter(Class adapter) {
        return null;
    }

}
