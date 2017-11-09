package com.sl.v0.actions.panel;

import javax.swing.JOptionPane;

public class InputDialog1 {

	/*获取项目源代码网址*/
	public String getcode(){
		String sourcecode = JOptionPane.showInputDialog(  
	      "输入项目代码网址：");
		return sourcecode;
	}
	
}
