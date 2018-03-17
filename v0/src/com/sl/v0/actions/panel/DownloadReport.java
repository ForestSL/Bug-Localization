package com.sl.v0.actions.panel;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

import com.sl.v0.datas.GlobalVar;

public class DownloadReport {
	
	/*�����û������url��װ����ѡ����Ϣ���µ�url*/
	public static String DownloadReport(String url,String localPath,String version){
		
		String[] murl=url.split("/");
		String project=murl[murl.length-1].toUpperCase();/*��ȡ�û����ҵ�bug���ݿ���Ŀ��*/
		String newUrl=null;
		String re=null;
		/*�û�δָ����Ӧ�汾*/
		if(version==null){
			newUrl="https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"
			           +project+"+AND+issuetype+%3D+Bug+AND+affectedVersion+%3D+EMPTY+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000";
			re=OpenHtml(newUrl,project,localPath,"all");
		}else{/*ָ��bug����汾*/
			String[] v=version.split("-");
			version=v[v.length-1];/*����汾�Ÿ�ʽ ��ȡ�汾����*/
			newUrl="https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"
				      +project+"+AND+issuetype+%3D+Bug+AND+affectedVersion+%3D+"
				      +version+"+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000";
			re=OpenHtml(newUrl,project,localPath,version);
		}

		return re;
	}
	
	/*��ʱ������ֱ����url��ָ��ѡ����Ϣ����html����*/
	public static String OpenHtml(String url,String project,String localPath,String version){
        try {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setUseCaches(true); //ʹ�û���
            httpUrlConnection.connect();           //��������
            InputStream inputStream = httpUrlConnection.getInputStream(); //��ȡ������
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
            String string;
            String file=localPath+"/"+project+version+".xml";
            //String file="E:\\"+project+".xml";
            GlobalVar.bugReportName="BugReport\\"+project+version+".xml";
            
            File dir =new File(file);    
			/*�ж���Ŀ�ļ��Ƿ����*/
			if(!dir.exists()&&!dir.isDirectory()){
				/*�����ȴ����ļ��� ������Ҳ���·��*/
				File dirFile = new File(localPath);
				boolean bFile = dirFile.mkdir();
				
				Writer out = new FileWriter(file);            
	            while ((string = bufferedReader.readLine()) != null) {
	                //System.out.println(string); //��ӡ���
	            	out.write(string);
	            }
	            out.close();
			}else{  
			    System.out.println("��bug�����Ѵ���");
			    JOptionPane.showMessageDialog(null,"��bug�����Ѵ��ڣ�");
			}  
            
            bufferedReader.close();
            inputStream.close();
            httpUrlConnection.disconnect();
            
            return "success";
             
        } catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"bug�����ȡ����");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"bug�����ȡ����");
        } 
        return "failed";
	}
	  
	/*������*/
//	public static void main(String[] args) {  
//		
//		String re=DownloadReport("https://issues.apache.org/jira/projects/CXF","E:\\BugReport");
//		String openStatus=OpenHtml("https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+CXF+AND+issuetype+%3D+Bug+AND+status+%3D+Open+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000",
//				"CXF","E:\\BugReport");
//	
//	}  
	
}
