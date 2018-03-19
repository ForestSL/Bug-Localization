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
	public static final MyResultDictionary LsiResult=new MyResultDictionary();
	public static final MyResultDictionary JsmResult=new MyResultDictionary();
	public static final MyResultDictionary NgdResult=new MyResultDictionary();
	public static final MyResultDictionary PmiResult=new MyResultDictionary();
	public static final String FolderName = Utility.ReportFolderPath+GlobalVar.codeFolderName;
	public static final HashMap<String,String> fileList=new HashMap<String,String>();
	
	/*������*/
	public static void main(String args[]){
		//GetVsmResult(FolderName);
		boolean[] method={false,false,true,false,false};
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
			String filePath=con[1];/*filePathΪ�ļ�����·��*/
			String[] path=filePath.split("\\\\");
			String file=path[path.length-1];
			fileList.put(index,file);/*����-�ļ���*/
			
			/*TODO:��Ҫ���ļ�����Ϊ�ļ�·�� �޸��������� ��file��ΪfilePath*/
			GlobalVar.objs[i][0]=file;/*table��ֵ �ļ���*/
			for(int j=1;j<6;j++)
				GlobalVar.objs[i][j]="0";
		}
		
		/*����bug��λ����*/
		(new Calculator()).Run(GlobalVar.codeFolderName,method);
		
		int index=0;
		if(method[0]==true){/*VSM*/
			index=1;
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
		if(method[1]==true){/*LSI*/
			index=2;
			JOptionPane.showMessageDialog(null,"��ȡLSI������ݡ���");
			GetLsiResult(FolderName);
			/*table��ֵ*/
			for(int i=0;i<fileCount;i++){
				//System.out.println(GlobalVar.objs[i][0]);
				List<String> tmp=new ArrayList<String>();
				//System.out.println(VsmResult.size());
			    tmp=LsiResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp.size();
			    }
			}
		}
		if(method[2]==true){/*JSM*/
			index=3;
			JOptionPane.showMessageDialog(null,"��ȡJSM������ݡ���");
			GetJsmResult(FolderName);
			/*table��ֵ*/
			for(int i=0;i<fileCount;i++){
				//System.out.println(GlobalVar.objs[i][0]);
				List<String> tmp=new ArrayList<String>();
				//System.out.println(VsmResult.size());
			    tmp=JsmResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp.size();
			    }
			}
		}
		if(method[3]==true){/*NGD*/
			index=4;
			JOptionPane.showMessageDialog(null,"��ȡNGD������ݡ���");
			GetNgdResult(FolderName);
			/*table��ֵ*/
			for(int i=0;i<fileCount;i++){
				//System.out.println(GlobalVar.objs[i][0]);
				List<String> tmp=new ArrayList<String>();
				//System.out.println(VsmResult.size());
			    tmp=NgdResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp.size();
			    }
			}
		}
		if(method[4]==true){/*PMI*/
			index=5;
			JOptionPane.showMessageDialog(null,"��ȡPMI������ݡ���");
			GetPmiResult(FolderName);
			/*table��ֵ*/
			for(int i=0;i<fileCount;i++){
				//System.out.println(GlobalVar.objs[i][0]);
				List<String> tmp=new ArrayList<String>();
				//System.out.println(VsmResult.size());
			    tmp=PmiResult.get(GlobalVar.objs[i][0]);
				//System.out.println(tmp.size());
			    if(tmp!=null){
			    	GlobalVar.objs[i][index]=tmp.size();
			    }
			}
		}
		
	}
	
	/*TODO:���ƶ�ֻȡǰ10��Ϊbug�ļ� ������ ����5�ַ������иĽ�*/
	public static void GetJsmResult(String dir){
		List<File> bugs=new ArrayList<File>();
		(new BaseFunction()).CheckFileName(dir,bugs);
		//System.out.println(bugs.get(0).getAbsolutePath());
		
		for(int i=0;i<bugs.size();i++){
			/*��ǰbug��*/
			String bugName=bugs.get(i).getName();
			String path=bugs.get(i).getAbsolutePath()+"\\Results\\Jsm.txt";
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
				JsmResult.Add(tmp[0], bugName, fileList);
			}
		}
		
		/*�������*/
//		Iterator it = JsmResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key+" "+JsmResult.get(key));
//		}
	}
	
	public static void GetPmiResult(String dir){
		List<File> bugs=new ArrayList<File>();
		(new BaseFunction()).CheckFileName(dir,bugs);
		//System.out.println(bugs.get(0).getAbsolutePath());
		
		for(int i=0;i<bugs.size();i++){
			/*��ǰbug��*/
			String bugName=bugs.get(i).getName();
			String path=bugs.get(i).getAbsolutePath()+"\\Results\\Pmi.txt";
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
				PmiResult.Add(tmp[0], bugName, fileList);
			}
		}
		
		/*�������*/
//		Iterator it = PmiResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key+" "+PmiResult.get(key));
//		}
	}
	
	public static void GetNgdResult(String dir){
		List<File> bugs=new ArrayList<File>();
		(new BaseFunction()).CheckFileName(dir,bugs);
		//System.out.println(bugs.get(0).getAbsolutePath());
		
		for(int i=0;i<bugs.size();i++){
			/*��ǰbug��*/
			String bugName=bugs.get(i).getName();
			String path=bugs.get(i).getAbsolutePath()+"\\Results\\Ngd.txt";
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
				NgdResult.Add(tmp[0], bugName, fileList);
			}
		}
		
		/*�������*/
//		Iterator it = NgdResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key+" "+NgdResult.get(key));
//		}
	}
	
	/*TODO:LSI�����ļ���������ͬ ��������ʽ����*/
	public static void GetLsiResult(String dir){
		List<File> bugs=new ArrayList<File>();
		(new BaseFunction()).CheckFileName(dir,bugs);
		//System.out.println(bugs.get(0).getAbsolutePath());
		
		for(int i=0;i<bugs.size();i++){
			/*��ǰbug��*/
			String bugName=bugs.get(i).getName();
			String path=bugs.get(i).getAbsolutePath()+"\\Results\\Lsi.txt";
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
				LsiResult.Add(tmp[0], bugName, fileList);
			}
		}
		
		/*�������*/
//		Iterator it = LsiResult.keySet().iterator();   
//		while(it.hasNext()){
//			String key=(String) it.next();
//			System.out.println(key+" "+LsiResult.get(key));
//		}
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
