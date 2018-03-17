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

/*�������ƶ��ļ� ����ÿ��java�ļ���bug���*/

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
		/*��ȡ���ݴ���*/
		(new DataCreator()).GetDatas();
		
		/*��ʼ��ȫ����table*/
		ArrayList<String> content=new ArrayList<String>();
		content=(new BaseFunction()).ReadAllLines(FolderName+"\\Filelist.txt");
		int fileCount=content.size();/*��ȡ�����ļ�����*/
		GlobalVar.objs=new Object[fileCount][];
		for(int i=0;i<fileCount;i++){
			GlobalVar.objs[i]=new Object[6];
			
			String[] con=content.get(i).split(" ");
			String index=con[0];
			String filePath=con[1];
			String[] path=filePath.split("\\\\");
			String file=path[path.length-1];
			fileList.put(index,file);
			
			GlobalVar.objs[i][0]=file;/*table��ֵ �ļ���*/
			for(int j=1;j<6;j++)
				GlobalVar.objs[i][j]="0";
		}
		
		int index=0;
		if(method[0]==true){/*VSM*/
			index=1;
			(new CalculatorVSM()).RunVSM(GlobalVar.codeFolderName);
			JOptionPane.showMessageDialog(null,"��ȡVSM������ݡ���");
			GetVsmResult(FolderName);
			/*table��ֵ*/
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
			/*��ǰbug��*/
			String bugName=bugs.get(i).getName();
			String path=bugs.get(i).getAbsolutePath()+"\\Results\\Vsm.txt";
			ArrayList<String> content=new ArrayList<String>();
			/*��ǰbug��Ӧ���ļ����ƶ�*/
			content=(new BaseFunction()).ReadAllLines(path);
			int time=content.size();
			if(content.size()>10){
				time=10;/*��ʱVSMֻȡ���ƶ�ǰ10���ļ�*/
			}
			for(int j=0;j<time;j++){
				//System.out.println(content.get(j));
				String line=content.get(j);
				String[] tmp=line.split(" ");
				//System.out.println(tmp[0]);
				VsmResult.Add(tmp[0], bugName, fileList);
			}
		}
		
		/*�������*/
//		Iterator it = VsmResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key+" "+VsmResult.get(key));
//		}
	}
	
}
