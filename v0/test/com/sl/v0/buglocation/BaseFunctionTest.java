package com.sl.v0.buglocation;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BaseFunctionTest {

	@Test
	public void testCheckFileName() {
		List<File> files=new ArrayList<File>();
		String path="E:\\BugLocation\\Report\\GithubCode\\cxf.git\\cxf-2.7.11";
		new BaseFunction().CheckFileName(path,files);
		assertNotNull(files);
	}

	@Test
	public void testGetFiles() {
		List<File> fileList=new ArrayList<File>();
		String fileDir="E:\\BugLocation\\Report\\GithubCode\\cxf.git\\cxf-2.7.11";
		new BaseFunction().GetFiles(fileDir,fileList);
		assertNotNull(fileList);
	}

	@Test
	public void testReadAllText() {
		String filepath="E:\\BugLocation\\Report\\GithubCode\\cxf.git\\cxf-2.7.11\\Corpus\\1.txt";
		String content=new BaseFunction().ReadAllText(filepath);
		assertNotNull(content);
	}

	@Test
	public void testReadAllLines() {
		String filepath="E:\\BugLocation\\Report\\GithubCode\\cxf.git\\cxf-2.7.11\\Corpus\\1.txt";
		ArrayList<String> content=new ArrayList<String>();
		content=new BaseFunction().ReadAllLines(filepath);
		assertNotNull(content);
	}

}
