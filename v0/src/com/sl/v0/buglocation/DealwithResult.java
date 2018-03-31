package com.sl.v0.buglocation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.datas.GlobalVar;

/*处理相似度文件 分析每个java文件的bug情况*/

public class DealwithResult {
	
	public static final String fileFolderName=Utility.ReportFolderPath+GlobalVar.codeFolderName;
	
	/*存储文件名-相似度值键值对*/
	public static final HashMap<String,String> VsmResult=new HashMap<String,String>();
	public static final HashMap<String,String> LsiResult=new HashMap<String,String>();
	public static final HashMap<String,String> JsmResult=new HashMap<String,String>();
	public static final HashMap<String,String> NgdResult=new HashMap<String,String>();
	public static final HashMap<String,String> PmiResult=new HashMap<String,String>();
	public static final String FolderName = Utility.ReportFolderPath+GlobalVar.codeFolderName;
	public static final HashMap<String,String> fileList=new HashMap<String,String>();
	
	/*测试用*/
	public static void main(String args[]){
		//GetVsmResult(FolderName);
		boolean[] method={true,true,true,true,true};
		execute(method);
	} 
	
	public static void execute(boolean[] method){
		if(GlobalVar.bugID==null){
			JOptionPane.showMessageDialog(null,"请选择待分析bug！");
			return;
		}
		
		/*提取数据处理*/
		(new DataCreator()).GetDatas();
		
		/*初始化全局量table*/
		ArrayList<String> content=new ArrayList<String>();
		content=(new BaseFunction()).ReadAllLines(FolderName+"\\Filelist.txt");
		int fileCount=content.size();/*获取所有文件数量*/
		GlobalVar.objs=new String[fileCount][];
		for(int i=0;i<fileCount;i++){
			GlobalVar.objs[i]=new String[6];
			
			String[] con=content.get(i).split(" ");
			String index=con[0];
			String filePath=con[1];/*filePath为文件完整路径*/
			String[] path=filePath.split("\\\\");
			String file=path[path.length-1];
			fileList.put(index,filePath);/*索引-文件名*/
			
			/*TODO:若要将文件名改为文件路径 修改上下两行 将file和filePath互换*/
			GlobalVar.objs[i][0]=filePath;/*table赋值 文件名*/
			for(int j=1;j<6;j++)
				GlobalVar.objs[i][j]="0";
		}
		
		/*进行bug定位计算*/
		(new Calculator()).Run(GlobalVar.codeFolderName,method);
		
		int index=0;
		if(method[0]==true){/*VSM*/
			index=1;
			JOptionPane.showMessageDialog(null,"获取VSM结果数据……");
			GetVsmResult(FolderName);
			/*table赋值*/
			for(int i=0;i<fileCount;i++){
				String tmp=VsmResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	//System.out.println(tmp);
			    	GlobalVar.objs[i][index]=tmp;
			    	//System.out.println("i:"+i+"index"+index+GlobalVar.objs[i][index]);
			    }
			}
			System.out.println("0000:"+GlobalVar.objs[3][1]);
		}
		if(method[1]==true){/*LSI*/
			index=2;
			JOptionPane.showMessageDialog(null,"获取LSI结果数据……");
			GetLsiResult(FolderName);
			/*table赋值*/
			for(int i=0;i<fileCount;i++){
			    String tmp=LsiResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp;
			    }
			}
		}
		if(method[2]==true){/*JSM*/
			index=3;
			JOptionPane.showMessageDialog(null,"获取JSM结果数据……");
			GetJsmResult(FolderName);
			/*table赋值*/
			for(int i=0;i<fileCount;i++){
				String tmp=JsmResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp;
			    }
			}
		}
		if(method[3]==true){/*NGD*/
			index=4;
			JOptionPane.showMessageDialog(null,"获取NGD结果数据……");
			GetNgdResult(FolderName);
			/*table赋值*/
			for(int i=0;i<fileCount;i++){
				String tmp=NgdResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp;
			    }
			}
		}
		if(method[4]==true){/*PMI*/
			index=5;
			JOptionPane.showMessageDialog(null,"获取PMI结果数据……");
			GetPmiResult(FolderName);
			/*table赋值*/
			for(int i=0;i<fileCount;i++){
				String tmp=PmiResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp;
			    }
			}
		}
		
		//System.out.println("1111:"+GlobalVar.objs[1][1]);
	}
	
	/*取指定bug的所有java文件相似度*/
	public static void GetJsmResult(String dir){
		String bugID=GlobalVar.bugID;
		String path=fileFolderName+bugID+"\\Results\\Jsm.txt";
		ArrayList<String> content=new ArrayList<String>();
		/*当前bug对应的文件相似度*/
		content=(new BaseFunction()).ReadAllLines(path);
		for(int i=0;i<content.size();i++){
			String line=content.get(i);
			String[] tmp=line.split(" ");
			String indexPath=tmp[0];/*索引的文件路径*/
			String similarity=tmp[1];/*相似度*/
			
			String[] tmp2=indexPath.split("\\\\");
			String indexName=tmp2[tmp2.length-1];
			//System.out.println(indexName);
			String[] in2=indexName.split("\\.");
			String index=in2[0];/*获取索引号*/
			
			String fileName=null;
			Iterator it = fileList.keySet().iterator();   
			fileName=fileList.get(index);/*文件名*/
			
			JsmResult.put(fileName,similarity);
		}
		
//		Iterator it = JsmResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key);
//			System.out.println(JsmResult.get(key));
//		}
		
	}
	
	public static void GetPmiResult(String dir){
		String bugID=GlobalVar.bugID;
		String path=fileFolderName+bugID+"\\Results\\Pmi.txt";
		ArrayList<String> content=new ArrayList<String>();
		/*当前bug对应的文件相似度*/
		content=(new BaseFunction()).ReadAllLines(path);
		for(int i=0;i<content.size();i++){
			String line=content.get(i);
			String[] tmp=line.split(" ");
			String indexPath=tmp[0];/*索引的文件路径*/
			String similarity=tmp[1];/*相似度*/
			
			String[] tmp2=indexPath.split("\\\\");
			String indexName=tmp2[tmp2.length-1];
			//System.out.println(indexName);
			String[] in2=indexName.split("\\.");
			String index=in2[0];/*获取索引号*/
			
			String fileName=null;
			Iterator it = fileList.keySet().iterator();   
			fileName=fileList.get(index);/*文件名*/
			
			PmiResult.put(fileName,similarity);
		}
		
	}
	
	public static void GetNgdResult(String dir){
		String bugID=GlobalVar.bugID;
		String path=fileFolderName+bugID+"\\Results\\Ngd.txt";
		ArrayList<String> content=new ArrayList<String>();
		/*当前bug对应的文件相似度*/
		content=(new BaseFunction()).ReadAllLines(path);
		for(int i=0;i<content.size();i++){
			String line=content.get(i);
			String[] tmp=line.split(" ");
			String indexPath=tmp[0];/*索引的文件路径*/
			String similarity=tmp[1];/*相似度*/
			
			String[] tmp2=indexPath.split("\\\\");
			String indexName=tmp2[tmp2.length-1];
			//System.out.println(indexName);
			String[] in2=indexName.split("\\.");
			String index=in2[0];/*获取索引号*/
			
			String fileName=null;
			Iterator it = fileList.keySet().iterator();   
			fileName=fileList.get(index);/*文件名*/
			
			NgdResult.put(fileName,similarity);
		}
		
	}
	
	public static void GetLsiResult(String dir){
		String bugID=GlobalVar.bugID;
		String path=fileFolderName+bugID+"\\Results\\Lsi\\90.txt";
		ArrayList<String> content=new ArrayList<String>();
		/*当前bug对应的文件相似度*/
		content=(new BaseFunction()).ReadAllLines(path);
		for(int i=0;i<content.size();i++){
			String line=content.get(i);
			String[] tmp=line.split(" ");
			String indexPath=tmp[0];/*索引的文件路径*/
			String similarity=tmp[1];/*相似度*/
			
			String[] tmp2=indexPath.split("\\\\");
			String indexName=tmp2[tmp2.length-1];
			//System.out.println(indexName);
			String[] in2=indexName.split("\\.");
			String index=in2[0];/*获取索引号*/
			
			String fileName=null;
			Iterator it = fileList.keySet().iterator();   
			fileName=fileList.get(index);/*文件名*/
			
			LsiResult.put(fileName,similarity);
		}
	}
	
	public static void GetVsmResult(String dir){
		String bugID=GlobalVar.bugID;
		String path=fileFolderName+bugID+"\\Results\\Vsm.txt";
		ArrayList<String> content=new ArrayList<String>();
		/*当前bug对应的文件相似度*/
		content=(new BaseFunction()).ReadAllLines(path);
		for(int i=0;i<content.size();i++){
			String line=content.get(i);
			String[] tmp=line.split(" ");
			String indexPath=tmp[0];/*索引的文件路径*/
			String similarity=tmp[1];/*相似度*/
			
			String[] tmp2=indexPath.split("\\\\");
			String indexName=tmp2[tmp2.length-1];
			//System.out.println(indexName);
			String[] in2=indexName.split("\\.");
			String index=in2[0];/*获取索引号*/
			
			String fileName=null;
			Iterator it = fileList.keySet().iterator();   
			fileName=fileList.get(index);/*文件名*/
			
			VsmResult.put(fileName,similarity);
		}
	}
	
}
