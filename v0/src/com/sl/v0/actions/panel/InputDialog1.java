package com.sl.v0.actions.panel;

import java.io.File;

import javax.swing.JOptionPane;

public class InputDialog1 {

	/*获取项目源代码网址*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
	      "输入项目代码网址：");
		
		/*github项目代码下载*/
//		github下载实现 暂时注释该代码
//		String localPath = "E://GitTest";
//		String result=DownloadCode.cloneRepository(sourcecode,localPath);
//		System.out.println(result); /*控制台提示成功或失败*/
		
		/*svn项目代码下载*/
		/*同上：调用checkoutRepository()即可*/
		
		return sourcecode;
		/*return result;*/
	}
	
	/*获取bug报告网址*/
	public String getreport(){
		String report = JOptionPane.showInputDialog(  
	      "输入bug数据库网址：");
		
		/*bug报告下载*/
		
		return report;
	}
	
}
