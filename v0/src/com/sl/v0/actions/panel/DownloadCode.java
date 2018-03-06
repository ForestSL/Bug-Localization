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
                    String version=revCommit.getName();//�汾��  
                    revCommit.getAuthorIdent().getName();  
                    revCommit.getAuthorIdent().getEmailAddress();  
                    revCommit.getAuthorIdent().getWhen();//ʱ��  
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
	
	/*github��������*/
	public static String cloneRepository(String url,String localPath){
		try{
			System.out.println("��ʼ����......");
			JOptionPane.showMessageDialog(null,"��Ŀ���������С���");/*��ȷ�ϼ�������*/
//			CloneCommand cc = Git.cloneRepository().setURI(url);
//			cc.setDirectory(new File(localPath)).call();
			System.out.println("�������......");
			return "success";
		}catch(Exception e){
		   e.printStackTrace();
		   return "error";
		}
	}
	
	/*svn��������  Ӱ��InputDialog1ʹ�ã�����*/
	//����SVN�ͻ��˹�����
	private static SVNClientManager ourClientManager;
	public static String checkoutRepository(String url,String localPath){
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
			return "fail";
			
		}
		String name = "admin";
		String password = "admin";  
		String workPath = localPath;
		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
		//ʵ�����ͻ��˹�����
		ourClientManager = SVNClientManager.newInstance(
	    (DefaultSVNOptions) options, name, password);
		//Ҫ�Ѱ汾�������check out����Ŀ¼
		File wcDir = new File(workPath);
		//ͨ���ͻ��˹�������updateClient���ʵ����
		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
		//sets externals not to be ignored during the checkout
		updateClient.setIgnoreExternals(false);
		//ִ��check out ���������ع��������İ汾�š�
		long workingVersion;
		try {
			workingVersion = updateClient
			    .doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
			System.out.println("�Ѱ汾��"+workingVersion+" check out ��Ŀ¼��"+wcDir+"�С�");
			return "success";
		} catch (SVNException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return "error";
		}
	}
	
	/*�Բ�github&svn�������ع���*/
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
