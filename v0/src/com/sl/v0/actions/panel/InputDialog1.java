package com.sl.v0.actions.panel;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.sl.v0.actions.panel.DownloadCode;

public class InputDialog1 {

	/*��ȡ��ĿԴ������ַ*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
				null,"��������Ŀ��ַ��\n","��Ŀ��ַ",JOptionPane.PLAIN_MESSAGE);
		
		/*������Ŀ��ַ���ذ汾�б�*/
		Object[] versions ={ "v1.0.0", "v1.0.1", "v2.0.0" };  
		String version = (String) JOptionPane.showInputDialog(
				null,"��ѡ��Ҫ���صİ汾:\n", "��Ŀ�汾", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), versions,"");
		
		/*github��Ŀ��������*/
		//github����ʵ�� ��ʱע�͸ô���
		String localPath = "E://GitTest";
		DownloadCode dc=new DownloadCode();
		String result=dc.cloneRepository(sourcecode,localPath);
		System.out.println(result); /*����̨��ʾ�ɹ���ʧ��*/
		
		if(result.compareTo("success")==0)
			JOptionPane.showMessageDialog(null, "��Ŀ"+version+"���سɹ���");
		else
			JOptionPane.showMessageDialog(null, "��Ŀ"+version+"����ʧ�ܣ������ԡ�");
		
		/*svn��Ŀ��������*/
		/*ͬ�ϣ�����checkoutRepository()����*/
		
		return sourcecode;
		/*return result;*/
	}
	
	/*��ȡbug������ַ*/
	public String getreport(){
		String report = JOptionPane.showInputDialog(  
	      "����bug���ݿ���ַ��");
		
		/*bug��������*/
		
		return report;
	}
	
}
