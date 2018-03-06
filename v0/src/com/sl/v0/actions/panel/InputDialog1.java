package com.sl.v0.actions.panel;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.sl.v0.actions.panel.DownloadCode;

public class InputDialog1 {

	/*获取项目源代码网址*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
				null,"请输入项目网址：\n","项目地址",JOptionPane.PLAIN_MESSAGE);
		
		/*根据项目网址返回版本列表*/
		Object[] versions ={ "v1.0.0", "v1.0.1", "v2.0.0" };  
		String version = (String) JOptionPane.showInputDialog(
				null,"请选择要下载的版本:\n", "项目版本", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), versions,"");
		
		/*github项目代码下载*/
		//github下载实现 暂时注释该代码
		String localPath = "E://GitTest";
		DownloadCode dc=new DownloadCode();
		String result=dc.cloneRepository(sourcecode,localPath);
		System.out.println(result); /*控制台提示成功或失败*/
		
		if(result.compareTo("success")==0)
			JOptionPane.showMessageDialog(null, "项目"+version+"下载成功！");
		else
			JOptionPane.showMessageDialog(null, "项目"+version+"下载失败！请重试。");
		
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
