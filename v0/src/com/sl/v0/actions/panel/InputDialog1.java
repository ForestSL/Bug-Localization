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
		/*能获取到版本列表*/
		if(versions.length>1){	
			String version = (String) JOptionPane.showInputDialog(
				null,"请选择要下载的版本:\n", "项目版本", JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), versions,"");
		
			if(version!=null){
				GlobalVar.bugVersion=version;
				/*github项目代码下载*/
				String localPath = "E:\\BugLocation\\Source\\GithubCode";
				String result=(new DownloadCode()).cloneRepository(sourcecode,localPath,version);
				if(result.compareTo("success")==0){
					JOptionPane.showMessageDialog(null, "项目"+version+"获取成功！");
			
					/*TODO:自动在eclipse中打开代码*/
				
				}
				else{
					JOptionPane.showMessageDialog(null, "项目"+version+"获取失败！请重试。");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "请重新输入项目地址并选择版本！");/*未选择版本*/
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "请重新输入正确项目地址！");/*地址错误*/
		}
		
		/*svn项目代码下载（暂时不考虑）*/
		/*同上：调用checkoutRepository()即可*/
		
		return sourcecode;
		/*return result;*/
	}
	
	/*获取bug报告网址*/
	public String getreport(){
		String report = JOptionPane.showInputDialog(  
	      "输入bug数据库网址：");
		
		String localPath = "E:\\BugLocation\\Source\\BugReport";
		report.replace(" ", "");

		/*判断是否输入网址*/
		if(!report.equals("")&&report!=null){
			String re=(new DownloadReport()).DownloadReport(report,localPath,GlobalVar.bugVersion);
			if(re=="success")
				JOptionPane.showMessageDialog(null, "bug报告获取成功！");
			else
				JOptionPane.showMessageDialog(null, "请重试！");
		}else{
			JOptionPane.showMessageDialog(null, "请输入bug数据库网址！");
		}
		
		return report;
	}
	
}
