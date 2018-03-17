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
	
	/*根据用户输入的url组装带有选择信息的新的url*/
	public static String DownloadReport(String url,String localPath,String version){
		
		String[] murl=url.split("/");
		String project=murl[murl.length-1].toUpperCase();/*获取用户查找的bug数据库项目名*/
		String newUrl=null;
		String re=null;
		/*用户未指定对应版本*/
		if(version==null){
			newUrl="https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"
			           +project+"+AND+issuetype+%3D+Bug+AND+affectedVersion+%3D+EMPTY+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000";
			re=OpenHtml(newUrl,project,localPath,"all");
		}else{/*指定bug报告版本*/
			String[] v=version.split("-");
			version=v[v.length-1];/*处理版本号格式 获取版本数字*/
			newUrl="https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+"
				      +project+"+AND+issuetype+%3D+Bug+AND+affectedVersion+%3D+"
				      +version+"+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000";
			re=OpenHtml(newUrl,project,localPath,version);
		}

		return re;
	}
	
	/*临时方法：直接在url中指定选择信息进行html下载*/
	public static String OpenHtml(String url,String project,String localPath,String version){
        try {
            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setUseCaches(true); //使用缓存
            httpUrlConnection.connect();           //建立连接
            InputStream inputStream = httpUrlConnection.getInputStream(); //读取输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
            String string;
            String file=localPath+"/"+project+version+".xml";
            //String file="E:\\"+project+".xml";
            GlobalVar.bugReportName="BugReport\\"+project+version+".xml";
            
            File dir =new File(file);    
			/*判断项目文件是否存在*/
			if(!dir.exists()&&!dir.isDirectory()){
				/*必须先创建文件夹 否则会找不到路径*/
				File dirFile = new File(localPath);
				boolean bFile = dirFile.mkdir();
				
				Writer out = new FileWriter(file);            
	            while ((string = bufferedReader.readLine()) != null) {
	                //System.out.println(string); //打印输出
	            	out.write(string);
	            }
	            out.close();
			}else{  
			    System.out.println("该bug报告已存在");
			    JOptionPane.showMessageDialog(null,"该bug报告已存在！");
			}  
            
            bufferedReader.close();
            inputStream.close();
            httpUrlConnection.disconnect();
            
            return "success";
             
        } catch (MalformedURLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"bug报告获取出错！");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"bug报告获取出错！");
        } 
        return "failed";
	}
	  
	/*测试用*/
//	public static void main(String[] args) {  
//		
//		String re=DownloadReport("https://issues.apache.org/jira/projects/CXF","E:\\BugReport");
//		String openStatus=OpenHtml("https://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml?jqlQuery=project+%3D+CXF+AND+issuetype+%3D+Bug+AND+status+%3D+Open+ORDER+BY+priority+DESC%2C+updated+DESC&tempMax=1000",
//				"CXF","E:\\BugReport");
//	
//	}  
	
}
