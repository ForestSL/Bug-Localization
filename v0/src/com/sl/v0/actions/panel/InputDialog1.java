package com.sl.v0.actions.panel;

import java.io.File;

import javax.swing.JOptionPane;

public class InputDialog1 {

	/*��ȡ��ĿԴ������ַ*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
	      "������Ŀ������ַ��");
		
		/*github��Ŀ��������*/
//		github����ʵ�� ��ʱע�͸ô���
//		String localPath = "E://GitTest";
//		String result=DownloadCode.cloneRepository(sourcecode,localPath);
//		System.out.println(result); /*����̨��ʾ�ɹ���ʧ��*/
		
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
