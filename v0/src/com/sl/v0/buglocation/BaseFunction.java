package com.sl.v0.buglocation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class BaseFunction {
	
	/*��ȡָ��Ŀ¼�����г�Corpus���ļ���*/
	public static void CheckFileName(String path,List<File> files) {  
	    File file = new File(path);
	    File[] tempList = file.listFiles();
	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isDirectory()&&!(tempList[i].getName().equals("Corpus"))) {
//	              System.out.println("�ļ��У�" + tempList[i]);
	        	files.add(tempList[i]);
	        }
	    }
    } 
	
	/*��ȡĿ¼�������ļ�*/
    public static void GetFiles(String fileDir,List<File> fileList) {  
        File file = new File(fileDir);  
        File[] files = file.listFiles();// ��ȡĿ¼�µ������ļ����ļ���  
        if (files == null) {// ���Ŀ¼Ϊ�գ�ֱ���˳�  
            return;  
        }  
        // ������Ŀ¼�µ������ļ�  
        for (File f : files) {  
            if (f.isFile()) {  
                fileList.add(f);  
            } else if (f.isDirectory()) {  
                //System.out.println(f.getAbsolutePath());  
                GetFiles(f.getAbsolutePath(),fileList);  
            }  
        }  
//        for (File f1 : fileList) {  
//            System.out.println(f1.getName());  
//        }  
    }  
	
	public static ArrayList<String> TextWithFilter(String text)
	{
		ArrayList<String> re=new ArrayList<String>();
		text=text.toLowerCase();/*ȫתСд*/
		
		/*TODO:��ȥStopWords.txt�еĴ�*/
		//System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").getPath());
		//ArrayList<String> stopwords=ReadAllLines("E://StopWords.txt");
		
//		String[] tmp1=text.split(" ");
//		for(int i=0;i<tmp1.length;i++){
//			text=tmp1[i];
//			String[] tmp2=text.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z](?:s[a-z]|[abcdefghijklmnopqrtuvwxyz]))");
//			for(int j=0;j<tmp2.length;j++){ 
//				text=tmp2[j];
//				//System.out.println(text);
				
				String[] cur=text.split("[^a-zA-Z]+");
				for(int k=0;k<cur.length;k++){
					//if(!re.contains(word))/*ȥ��*/
					//if(!stopwords.contains(cur[k]))/*��������stopwords.txt��*/
						re.add(cur[k]);
				}
				
//			}
//		}	
		return re;
	}
	
	/*��String��ʽ��ȡ�ļ���������*/
	public static String ReadAllText(String filepath){
		StringBuilder sb = new StringBuilder();
		String s ="";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filepath));
			while( (s = br.readLine()) != null) {
				sb.append(s + "\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}		
		String str = sb.toString(); 
		return str;
	}

	/*����д���ļ�*/
	public static void WriteAllLines(String filePath,ArrayList<String> content){
		try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)),"UTF-8"));
            for (int i=0;i<content.size();i++) {
                bw.write(content.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.err.println("write errors :" + e);
        }
	}
	
	/*���ж�ȡ�ļ����� �洢������*/
	public static ArrayList<String> ReadAllLines(String filePath){
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath);
	        BufferedReader bufferedReader =new BufferedReader(fileReader);
	        StringBuilder  stringBuilder =new StringBuilder();
	        ArrayList<String>  strings =new ArrayList<>();
	        String  str=null;
	        
	        while ((str=bufferedReader.readLine())!=null) {
	        	//System.out.println(str);
	        	if (str.trim().length()>2) {
	        		strings.add(str);
	            }
	            	
//	        	System.out.println(strings.size());
//	        	for (int i = 0; i < strings.size(); i++) {
//	        		System.out.println(strings.get(i));
//	            }
	        }
	        fileReader.close();
	        bufferedReader.close();
	        return strings;
			
		} catch (FileNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}
	
}
