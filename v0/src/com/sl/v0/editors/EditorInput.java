package com.sl.v0.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EditorInput implements IEditorInput {

    /* ����true����򿪸ñ༭�������������Eclipse���˵����ļ��� 
     * ���²�������򿪵��ĵ����С�����flase�򲻳��������С�*/
    @Override
    public boolean exists() {
        return true;
    }

    /* �༭����������ͼ�꣬����������Ҫ��Editor��
     * �� setTitleImage�������ã����ܳ����ڱ������С�*/
    @Override
    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    /* �༭������������ʾ���ƣ��������getImageDescriptorһ��ҲҪ 
     * ��ChinaEditor����setPartName�������ã����ܳ����ڱ������С�*/
    @Override
    public String getName() {
        return "java�ļ��༭��";
    }

    /* �༭����������С������ʾ���֣�������getName������Editor��������*/
    @Override
    public String getToolTipText() {
        return "java�ļ��༭��";
    }

    /* ����һ�������������汾�༭��������״̬�Ķ���*/
    @Override
    public IPersistableElement getPersistable() {
        return null;
    }

    /* �õ�һ���༭����������*/
    @Override
    public Object getAdapter(Class adapter) {
        return null;
    }

}
