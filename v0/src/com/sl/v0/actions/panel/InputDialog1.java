package com.sl.v0.actions.panel;

import com.sl.v0.actions.panel.DownloadCode;
import com.sl.v0.datas.GlobalVar;

import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;

public class InputDialog1 {

	/*获取项目源代码网址*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
				null,"请输入项目网址：\n","项目地址",JOptionPane.PLAIN_MESSAGE);
		
		/*根据项目网址返回版本列表*/
		//Object[] versions ={ "v1.0.0", "v1.0.1", "v2.0.0" };  
		Object[] versions=new DownloadCode().getHistoryInfo(sourcecode);
		String version = (String) JOptionPane.showInputDialog(
				null,"请选择要下载的版本:\n", "项目版本", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), versions,"");
		
		/*TODO:代码下载目录问题待修改*/
		
		/*github项目代码下载*/
		String localPath = "E://GitTest";
		String result=(new DownloadCode()).cloneRepository(sourcecode,localPath,version);
		
		if(result.compareTo("success")==0){
			JOptionPane.showMessageDialog(null, "项目"+version+"下载成功！");
			
			/*TODO:自动在eclipse中打开代码*/
			
		}
		else
			JOptionPane.showMessageDialog(null, "项目"+version+"下载失败！请重试。");
		
		/*svn项目代码下载（暂时不考虑）*/
		/*同上：调用checkoutRepository()即可*/
		
		return sourcecode;
		/*return result;*/
	}
	
	/*获取bug报告网址*/
	public String getreport(){
		String report = JOptionPane.showInputDialog(  
	      "输入bug数据库网址：");
		
		/*TODO:bug报告下载*/
		
		return report;
	}
	
}
