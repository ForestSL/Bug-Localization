package com.sl.v0.buglocation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.buglocation.models.MyResultDictionary;
import com.sl.v0.datas.GlobalVar;

/*处理相似度文件 分析每个java文件的bug情况*/

public class DealwithResult {
	
	public static final MyResultDictionary VsmResult=new MyResultDictionary();
	public static final String FolderName = Utility.ReportFolderPath+GlobalVar.codeFolderName;
	public static final HashMap<String,String> fileList=new HashMap<String,String>();
	
	public static void main(String args[]){
		//GetVsmResult(FolderName);
		boolean[] method={true,false,false,false,false};
		execute(method);
	} 
	
	public static void execute(boolean[] method){
		/*提取数据处理*/
		(new DataCreator()).GetDatas();
		
		/*初始化全局量table*/
		ArrayList<String> content=new ArrayList<String>();
		content=(new BaseFunction()).ReadAllLines(FolderName+"\\Filelist.txt");
		int fileCount=content.size();/*获取所有文件数量*/
		GlobalVar.objs=new Object[fileCount][];
		for(int i=0;i<fileCount;i++){
			GlobalVar.objs[i]=new Object[6];
			
			String[] con=content.get(i).split(" ");
			String index=con[0];
			String filePath=con[1];
			String[] path=filePath.split("\\\\");
			String file=path[path.length-1];
			fileList.put(index,file);
			
			GlobalVar.objs[i][0]=file;/*table赋值 文件名*/
			for(int j=1;j<6;j++)
				GlobalVar.objs[i][j]="0";
		}
		
		int index=0;
		if(method[0]==true){/*VSM*/
			index=1;
			(new CalculatorVSM()).RunVSM(GlobalVar.codeFolderName);
			JOptionPane.showMessageDialog(null,"获取VSM结果数据……");
			GetVsmResult(FolderName);
			/*table赋值*/
			for(int i=0;i<fileCount;i++){
				//System.out.println(GlobalVar.objs[i][0]);
				List<String> tmp=new ArrayList<String>();
				//System.out.println(VsmResult.size());
			    tmp=VsmResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp.size();
			    }
			}
		}
		if(method[1]==true){
			index=2;
		}
		if(method[2]==true){
			index=3;
		}
		if(method[3]==true){
			index=4;
		}
		if(method[4]==true){
			index=5;
		}
		
	}
	
	public static void GetVsmResult(String dir){
		List<File> bugs=new ArrayList<File>();
		(new BaseFunction()).CheckFileName(dir,bugs);
		//System.out.println(bugs.get(0).getAbsolutePath());
		
		for(int i=0;i<bugs.size();i++){
			/*当前bug名*/
			String bugName=bugs.get(i).getName();
			String path=bugs.get(i).getAbsolutePath()+"\\Results\\Vsm.txt";
			ArrayList<String> content=new ArrayList<String>();
			/*当前bug对应的文件相似度*/
			content=(new BaseFunction()).ReadAllLines(path);
			int time=content.size();
			if(content.size()>10){
				time=10;/*暂时VSM只取相似度前10的文件*/
			}
			for(int j=0;j<time;j++){
				//System.out.println(content.get(j));
				String line=content.get(j);
				String[] tmp=line.split(" ");
				//System.out.println(tmp[0]);
				VsmResult.Add(tmp[0], bugName, fileList);
			}
		}
		
		/*测试输出*/
//		Iterator it = VsmResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key+" "+VsmResult.get(key));
//		}
	}
	
}
