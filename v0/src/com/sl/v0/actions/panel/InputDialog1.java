package com.sl.v0.actions.panel;

import com.sl.v0.actions.panel.DownloadCode;
import com.sl.v0.datas.GlobalVar;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;

public class InputDialog1 {

	/*��ȡ��ĿԴ������ַ*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
				null,"��������Ŀ��ַ��\n","��Ŀ��ַ",JOptionPane.PLAIN_MESSAGE);
		
		/*������Ŀ��ַ���ذ汾�б�*/
		//Object[] versions ={ "v1.0.0", "v1.0.1", "v2.0.0" };  
		Object[] versions=new DownloadCode().getHistoryInfo(sourcecode);
		String version = (String) JOptionPane.showInputDialog(
				null,"��ѡ��Ҫ���صİ汾:\n", "��Ŀ�汾", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), versions,"");
		
		/*TODO:��������Ŀ¼������޸�*/
		
		/*github��Ŀ��������*/
		String localPath = "E://GitTest";
		String result=(new DownloadCode()).cloneRepository(sourcecode,localPath,version);
		
		if(result.compareTo("success")==0){
			JOptionPane.showMessageDialog(null, "��Ŀ"+version+"���سɹ���");
			
			/*TODO:�Զ���eclipse�д򿪴���*/
			
		}
		else
			JOptionPane.showMessageDialog(null, "��Ŀ"+version+"����ʧ�ܣ������ԡ�");
		
		/*svn��Ŀ�������أ���ʱ�����ǣ�*/
		/*ͬ�ϣ�����checkoutRepository()����*/
		
		return sourcecode;
		/*return result;*/
	}
	
	/*��ȡbug������ַ*/
	public String getreport(){
		String report = JOptionPane.showInputDialog(  
	      "����bug���ݿ���ַ��");
		
		/*TODO:bug��������*/
		
		return report;
	}
	
}
