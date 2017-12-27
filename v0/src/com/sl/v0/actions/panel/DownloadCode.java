package com.sl.v0.actions.panel;

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class DownloadCode {

	/*github��������*/
	public static String cloneRepository(String url,String localPath){
		
		try{
			System.out.println("��ʼ����......");
			CloneCommand cc = Git.cloneRepository().setURI(url);
			cc.setDirectory(new File(localPath)).call();
			System.out.println("�������......");
			return "success";
		}catch(Exception e){
		   e.printStackTrace();
		   return "error";
		}
	}
	
	/*svn��������*/
	//����SVN�ͻ��˹�����
	private static SVNClientManager ourClientManager;
	public static void checkoutRepository(String url,String localPath) throws SVNException{
		/*
		 * ��ʼ��֧��svn
		 * Э��Ŀ�  ������ִ�д˲���  
		**/
		SVNRepositoryFactoryImpl.setup();
		/*��ر�����ֵ*/
		SVNURL repositoryURL = null;
		try {
			repositoryURL = SVNURL.parseURIEncoded(url);
		} catch (SVNException e) {
			System.out.println("�޷�����");
		}
		String name = "admin";
		String password = "admin";  
		String workPath = localPath;
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		//ʵ�����ͻ��˹�����
		ourClientManager = SVNClientManager.newInstance(
	    (DefaultSVNOptions) options, name, password);
		//Ҫ�Ѱ汾�������check out����Ŀ¼
		//FIle wcDir = new File("d:/test")
		File wcDir = new File(workPath);
		//ͨ���ͻ��˹�������updateClient���ʵ����
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		//sets externals not to be ignored during the checkout
		updateClient.setIgnoreExternals(false);
		//ִ��check out ���������ع��������İ汾�š�
		long workingVersion;
		workingVersion = updateClient
		    .doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
		System.out.println("�Ѱ汾��"+workingVersion+" check out ��Ŀ¼��"+wcDir+"�С�");
	}
	
	/*�Բ�github&svn�������ع���*/
	public static void main(String[] args){
		String localPath = "E://GitTest2";
		String url = "https://gitbox.apache.org/repos/asf/cxf.git";
		/*cloneRepository(url,localPath);*/
	  
		String localPath2 = "E://SvnTest";
		String url2 = "http://svn.apache.org/repos/asf/ant/site/";
//		try {
//			checkoutRepository(url2,localPath2);
//		} catch (SVNException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
	}

}
