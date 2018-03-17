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
		/*�ܻ�ȡ���汾�б�*/
		if(versions.length>1){	
			String version = (String) JOptionPane.showInputDialog(
				null,"��ѡ��Ҫ���صİ汾:\n", "��Ŀ�汾", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), versions,"");
		
			if(version!=null){
				GlobalVar.bugVersion=version;
				/*github��Ŀ��������*/
				String localPath = "E:\\BugLocation\\Source\\GithubCode";
				String result=(new DownloadCode()).cloneRepository(sourcecode,localPath,version);
				if(result.compareTo("success")==0){
					JOptionPane.showMessageDialog(null, "��Ŀ"+version+"��ȡ�ɹ���");
			
					/*TODO:�Զ���eclipse�д򿪴���*/
				
				}
				else{
					JOptionPane.showMessageDialog(null, "��Ŀ"+version+"��ȡʧ�ܣ������ԡ�");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "������������Ŀ��ַ��ѡ��汾��");/*δѡ��汾*/
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "������������ȷ��Ŀ��ַ��");/*��ַ����*/
		}
		
		/*svn��Ŀ�������أ���ʱ�����ǣ�*/
		/*ͬ�ϣ�����checkoutRepository()����*/
		
		return sourcecode;
		/*return result;*/
	}
	
	/*��ȡbug������ַ*/
	public String getreport(){
		String report = JOptionPane.showInputDialog(  
	      "����bug���ݿ���ַ��");
		
		String localPath = "E:\\BugLocation\\Source\\BugReport";
		report.replace(" ", "");

		/*�ж��Ƿ�������ַ*/
		if(!report.equals("")&&report!=null){
			String re=(new DownloadReport()).DownloadReport(report,localPath,GlobalVar.bugVersion);
			if(re=="success")
				JOptionPane.showMessageDialog(null, "bug�����ȡ�ɹ���");
			else
				JOptionPane.showMessageDialog(null, "�����ԣ�");
		}else{
			JOptionPane.showMessageDialog(null, "������bug���ݿ���ַ��");
		}
		
		return report;
	}
	
}
