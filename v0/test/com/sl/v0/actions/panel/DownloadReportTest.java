package com.sl.v0.actions.panel;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sl.v0.datas.GlobalVar;

public class DownloadReportTest {

	@Test
	public void testDownloadReport() {
		String report="https://issues.apache.org/jira/projects/CXF";
		String localPath = "E:\\BugLocation\\Source\\BugReport";
		String version="cxf-2.7.11";
		String result=new DownloadReport().DownloadReport(report,localPath,version);
		assertEquals("success",result);
	}

}
