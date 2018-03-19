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
	
	/*读取指定目录下所有除Corpus的文件夹*/
	public static void CheckFileName(String path,List<File> files) {  
	    File file = new File(path);
	    File[] tempList = file.listFiles();
	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isDirectory()&&!(tempList[i].getName().equals("Corpus"))) {
//	              System.out.println("文件夹：" + tempList[i]);
	        	files.add(tempList[i]);
	        }
	    }
    } 
	
	/*读取目录下所有文件*/
    public static void GetFiles(String fileDir,List<File> fileList) {  
        File file = new File(fileDir);  
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹  
        if (files == null) {// 如果目录为空，直接退出  
            return;  
        }  
        // 遍历，目录下的所有文件  
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
		String[] cur=text.split("[^a-zA-Z]+");
		for(int i=0;i<cur.length;i++){
			String word=cur[i].toLowerCase();/*全转小写 去重*/
			if(!re.contains(word))
				re.add(word);
		}
		return re;
	}
	
	/*以String形式读取文件所有内容*/
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		
		String str = sb.toString(); 
		return str;
	}

	/*逐行写入文件*/
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
	
	/*逐行读取文件内容 存储至数组*/
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	
}
