package com.sl.v0.actions.panel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Ref;

import com.sl.v0.datas.GlobalVar;

public class DownloadCode {  
	
	/*获取项目版本号列表*/
	public static Object[] getHistoryInfo(String REMOTE_URL) {  
		String cur="";
        System.out.println("Listing remote repository " + REMOTE_URL);
        Collection<Ref> refs;
		try {
			refs = Git.lsRemoteRepository()
			        .setTags(true)
			        .setRemote(REMOTE_URL)
			        .call();
			
            for (Ref ref : refs) {
                //System.out.println("Remote tag: " + ref.getName());
                String[] lversion=ref.getName().split("/");/*获取 refs/tags/版本号*/
                String sversion=lversion[lversion.length-1];/*截取其中的版本号*/
                cur=cur+sversion+" ";
            }           
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"项目获取出错，请重试！");
		} catch (TransportException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"项目获取出错，请重试！");
		} catch (GitAPIException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"项目获取出错，请重试！");
		}
		
		String[] cur2=cur.split(" ");
		Object[] version=new Object[cur2.length];/*截取存储在object对象*/
		for(int i=0;i<cur2.length;i++){
			version[i]=cur2[i];
			//System.out.println(version[i]);/*测试用：输出版本列表*/
		}
		
		return version;
    } 
	
	/*指定版本号进行github代码下载*/
	public static String cloneRepository(String url,String localPath,String version){
		try{
			String[] urlPath=url.split("/");
			String file=urlPath[urlPath.length-1];
			String path=localPath+"/"+file+"/"+version;
			GlobalVar.codeFolderName="GithubCode\\"+file+"\\"+version+"\\";
			
			File dir =new File(path);    
			/*判断项目文件是否存在*/
			if(!dir.exists()&&!dir.isDirectory()){   
				System.out.println("开始下载......");
				JOptionPane.showMessageDialog(null,"开始进行下载……");
			    CloneCommand cc = Git.cloneRepository();
				Git git=cc.setURI(url).setBranch(version).setDirectory(dir).call();
				System.out.println("下载完成......");
			}else{  
			    System.out.println("该项目已存在");
			    JOptionPane.showMessageDialog(null,"该版本项目代码已存在！");
			}  
			
			return "success";
		}catch(Exception e){
		   e.printStackTrace();
		   return "error";
		}
	}
	
	/*svn代码下载  影响InputDialog1使用？？？*/
//	//声明SVN客户端管理类
//	private static SVNClientManager ourClientManager;
//	public static String checkoutRepository(String url,String localPath){
//		/*
//		 * 初始化支持svn
//		 * 协议的库  必须先执行此操作  
//		**/
//		SVNRepositoryFactoryImpl.setup();
//		/*相关变量赋值*/
//		SVNURL repositoryURL = null;
//		try {
//			repositoryURL = SVNURL.parseURIEncoded(url);
//		} catch (SVNException e) {
//			System.out.println("无法连接");
//			return "fail";
//			
//		}
//		String name = "admin";
//		String password = "admin";  
//		String workPath = localPath;
//		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
//		//实例化客户端管理类
//		ourClientManager = SVNClientManager.newInstance(
//	    (DefaultSVNOptions) options, name, password);
//		//要把版本库的内容check out到的目录
//		File wcDir = new File(workPath);
//		//通过客户端管理类获得updateClient类的实例。
//		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
//		//sets externals not to be ignored during the checkout
//		updateClient.setIgnoreExternals(false);
//		//执行check out 操作，返回工作副本的版本号。
//		long workingVersion;
//		try {
//			workingVersion = updateClient
//			    .doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
//			System.out.println("把版本："+workingVersion+" check out 到目录："+wcDir+"中。");
//			return "success";
//		} catch (SVNException e) {
//			e.printStackTrace();
//			return "error";
//		}
//	}
	
	/*自测github&svn代码下载功能*/
//	public static void main(String[] args){
//		String REMOTE_URL = "https://gitbox.apache.org/repos/asf/cxf.git";
//		getHistoryInfo(REMOTE_URL);
//		
//		String version="cxf-2.7.10";
//		String localPath = "E://GitTest2";
//		//cloneRepository(REMOTE_URL,localPath,version);
//		
//	  
////		String localPath2 = "E://SvnTest2";
////		String url2 = "http://svn.apache.org/repos/asf/ant/site/";
//////		checkoutRepository(url2,localPath2);
//	}

}
