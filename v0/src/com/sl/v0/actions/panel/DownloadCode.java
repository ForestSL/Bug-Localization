package com.sl.v0.actions.panel;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class DownloadCode {

	static Git git;  
	public static void getHistoryInfo() {  
        File gitDir = new File("https://gitbox.apache.org/repos/asf/cxf.git");  
            try {  
                if (git == null) {  
                    git = Git.open(gitDir); 
                }  
                Iterable<RevCommit> gitlog= git.log().call();  
                for (RevCommit revCommit : gitlog) {  
                    String version=revCommit.getName();//版本号  
                    revCommit.getAuthorIdent().getName();  
                    revCommit.getAuthorIdent().getEmailAddress();  
                    revCommit.getAuthorIdent().getWhen();//时间  
                    System.out.println(version);  
                }  
            }catch (NoHeadException e) {  
                e.printStackTrace();  
            } catch (GitAPIException e) {  
                e.printStackTrace();  
            }  catch (IOException e) {
            	 e.printStackTrace();  
            }
    } 
	
	/*github代码下载*/
	public static String cloneRepository(String url,String localPath){
		try{
			System.out.println("开始下载......");
			JOptionPane.showMessageDialog(null,"项目正在下载中……");/*带确认键？？？*/
//			CloneCommand cc = Git.cloneRepository().setURI(url);
//			cc.setDirectory(new File(localPath)).call();
			System.out.println("下载完成......");
			return "success";
		}catch(Exception e){
		   e.printStackTrace();
		   return "error";
		}
	}
	
	/*svn代码下载  影响InputDialog1使用？？？*/
	//声明SVN客户端管理类
	private static SVNClientManager ourClientManager;
	public static String checkoutRepository(String url,String localPath){
		/*
		 * 初始化支持svn
		 * 协议的库  必须先执行此操作  
		**/
		SVNRepositoryFactoryImpl.setup();
		/*相关变量赋值*/
		SVNURL repositoryURL = null;
		try {
			repositoryURL = SVNURL.parseURIEncoded(url);
		} catch (SVNException e) {
			System.out.println("无法连接");
			return "fail";
			
		}
		String name = "admin";
		String password = "admin";  
		String workPath = localPath;
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		//实例化客户端管理类
		ourClientManager = SVNClientManager.newInstance(
	    (DefaultSVNOptions) options, name, password);
		//要把版本库的内容check out到的目录
		File wcDir = new File(workPath);
		//通过客户端管理类获得updateClient类的实例。
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		//sets externals not to be ignored during the checkout
		updateClient.setIgnoreExternals(false);
		//执行check out 操作，返回工作副本的版本号。
		long workingVersion;
		try {
			workingVersion = updateClient
			    .doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
			System.out.println("把版本："+workingVersion+" check out 到目录："+wcDir+"中。");
			return "success";
		} catch (SVNException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "error";
		}
	}
	
	/*自测github&svn代码下载功能*/
	public static void main(String[] args){
		getHistoryInfo();
		
		String localPath = "E://GitTest2";
		String url = "https://gitbox.apache.org/repos/asf/cxf.git";
		/*cloneRepository(url,localPath);*/
	  
		String localPath2 = "E://SvnTest2";
		String url2 = "http://svn.apache.org/repos/asf/ant/site/";
//		checkoutRepository(url2,localPath2);
	}

}
