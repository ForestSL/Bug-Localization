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

public class DownloadReport {
	
	/*�����û������url��װ����ѡ����Ϣ���µ�url*/
	public static String DownloadReport(String url){
		
		/*TODO:ʵ��url�ֽ�����װ ����OpenHtml()��������*/
		
		return null;
	}
	
	/*��ʱ������ֱ����url��ָ��ѡ����Ϣ����html����*/
	public static String OpenHtml(String url){
        try {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setUseCaches(true); //ʹ�û���
            httpUrlConnection.connect();           //��������
            InputStream inputStream = httpUrlConnection.getInputStream(); //��ȡ������
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
            String string;
            Writer out = new FileWriter("E:\\cxfBug.xml");
            while ((string = bufferedReader.readLine()) != null) {
                //System.out.println(string); //��ӡ���
            	out.write(string);
            }
            out.close();
            
            bufferedReader.close();
            inputStream.close();
            httpUrlConnection.disconnect();
            
            return "success";
             
        } catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"bug�����ȡ���������ԣ�");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"bug�����ȡ���������ԣ�");
        } 
        return "failed";
	}
	  
	/*������*/
	public static void main(String[] args) {  
		//String openStatus=openHtml("https://issues.apache.org/jira/projects/CXF");
		//"https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+AGILA+AND+issuetype+%3D+Bug+AND+status+%3D+Open+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000"
		String openStatus=OpenHtml("https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+CXF+AND+issuetype+%3D+Bug+AND+status+%3D+Open+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000");
		//System.out.println(openStatus);
//		"https://issues.apache.org/jira/projects/CXF"
//		"https://issues.apache.org/jira/issues/?jql=project%20%3D%20CXF%20AND%20issuetype%20%3D%20Bug%20AND%20status%20%3D%20Open%20ORDER%20BY%20priority%20DESC%2C%20updated%20DESC"
//		"https://issues.apache.org/jira/browse/CXF-7508?jql=project%20%3D%20CXF%20ORDER%20BY%20priority%20DESC%2C%20updated%20DESC"
//		"https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+CXF+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000"
//		"https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+CXF+AND+issuetype+%3D+Bug+AND+status+%3D+Open+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000"
	}  
	
}
