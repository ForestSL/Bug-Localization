package com.sl.v0.actions.panel;

import static org.junit.Assert.*;

import org.junit.Test;

public class DownloadCodeTest {

	@Test
	public void testGetHistoryInfo() {
		String REMOTE_URL="https://gitbox.apache.org/repos/asf/cxf.git";
		Object[] versions=new DownloadCode().getHistoryInfo(REMOTE_URL);
		System.out.println("��Ŀ�汾�б����£�");
		for(int i=0;i<versions.length;i++)
			System.out.println(versions[i]);
		System.out.println("��Ŀ�汾�б������");
		assertNotNull(versions);
	}

	@Test
	public void testCloneRepository() {
		String REMOTE_URL="https://gitbox.apache.org/repos/asf/cxf.git";
		String localPath = "E:\\BugLocation\\Source\\GithubCode";
		String version="cxf-2.7.11";
		String result=new DownloadCode().cloneRepository(REMOTE_URL,localPath,version);
		System.out.println("��Ŀ�������������"+result);
		assertEquals("success",result);
	}

}
