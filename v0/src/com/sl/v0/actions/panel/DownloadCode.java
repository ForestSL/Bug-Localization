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

public class DownloadCode {  
	
	/*��ȡ��Ŀ�汾���б�*/
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
                String[] lversion=ref.getName().split("/");/*��ȡ refs/tags/�汾��*/
                String sversion=lversion[lversion.length-1];/*��ȡ���еİ汾��*/
                cur=cur+sversion+" ";
            }           
		} catch (InvalidRemoteException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		String[] cur2=cur.split(" ");
		Object[] version=new Object[cur2.length];/*��ȡ�洢��object����*/
		for(int i=0;i<cur2.length;i++){
			version[i]=cur2[i];
			System.out.println(version[i]);
		}
		
		return version;
    } 
	
	/*ָ���汾�Ž���github��������*/
	public static String cloneRepository(String REMOTE_URL,String localPath,String version){
		try{
			System.out.println("��ʼ����......");
			JOptionPane.showMessageDialog(null,"��Ŀ���������С���");/*��ȷ�ϼ�������*/
			
			String url=REMOTE_URL;
			CloneCommand cc = Git.cloneRepository();
			Git git=cc.setURI(url).setBranch(version).setDirectory(new File(localPath)).call();
			
			System.out.println("�������......");
			return "success";
		}catch(Exception e){
		   e.printStackTrace();
		   return "error";
		}
	}
	
	/*svn��������  Ӱ��InputDialog1ʹ�ã�����*/
//	//����SVN�ͻ��˹�����
//	private static SVNClientManager ourClientManager;
//	public static String checkoutRepository(String url,String localPath){
//		/*
//		 * ��ʼ��֧��svn
//		 * Э��Ŀ�  ������ִ�д˲���  
//		**/
//		SVNRepositoryFactoryImpl.setup();
//		/*��ر�����ֵ*/
//		SVNURL repositoryURL = null;
//		try {
//			repositoryURL = SVNURL.parseURIEncoded(url);
//		} catch (SVNException e) {
//			System.out.println("�޷�����");
//			return "fail";
//			
//		}
//		String name = "admin";
//		String password = "admin";  
//		String workPath = localPath;
//		ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
//		//ʵ�����ͻ��˹�����
//		ourClientManager = SVNClientManager.newInstance(
//	    (DefaultSVNOptions) options, name, password);
//		//Ҫ�Ѱ汾�������check out����Ŀ¼
//		File wcDir = new File(workPath);
//		//ͨ���ͻ��˹�������updateClient���ʵ����
//		SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
//		//sets externals not to be ignored during the checkout
//		updateClient.setIgnoreExternals(false);
//		//ִ��check out ���������ع��������İ汾�š�
//		long workingVersion;
//		try {
//			workingVersion = updateClient
//			    .doCheckout(repositoryURL, wcDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY,false);
//			System.out.println("�Ѱ汾��"+workingVersion+" check out ��Ŀ¼��"+wcDir+"�С�");
//			return "success";
//		} catch (SVNException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//			return "error";
//		}
//	}
	
	/*�Բ�github&svn�������ع���*/
	public static void main(String[] args){
		String REMOTE_URL = "https://gitbox.apache.org/repos/asf/cxf.git";
		getHistoryInfo(REMOTE_URL);
		
		String version="cxf-2.7.10";
		String localPath = "E://GitTest2";
		//cloneRepository(REMOTE_URL,localPath,version);
		
	  
//		String localPath2 = "E://SvnTest2";
//		String url2 = "http://svn.apache.org/repos/asf/ant/site/";
////		checkoutRepository(url2,localPath2);
	}

}
