package com.sl.v0.buglocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.sl.v0.buglocation.datas.Bug;
import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.buglocation.models.MyDoubleDictionary;

public class CalculatorVSM {

	private static final String VsmCompletedFile = "CompletedVsm.txt";
	private static boolean _cleanPrevious;
	private static boolean _runVsm;
	private static final String VsmFileName = "Results\\Vsm.txt";
	
	/*存储文件名-文件内容映射*/
	private static final HashMap<String, ArrayList<String>> CodeFilesWithContent = new HashMap<String, ArrayList<String>>();
	
    /*测试用*/
	public static void main(String[] args) {  
		
	} 
		
	public void RunVSM(String path){
		String datasetFolderPath = path;
		String vsmCompletedFilePath = datasetFolderPath + VsmCompletedFile;
		File file=new File(vsmCompletedFilePath);
		
		if (_runVsm && (!file.exists() || _cleanPrevious)){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				System.out.println("CompletedVsm.txt创建失败！");
			}
		}
				
		ArrayList<String> completedVsm = _runVsm ? ReadAllLines(vsmCompletedFilePath) : new ArrayList<String>();
		
		/*获取bug报告中的bug*/
		List<Bug> bugs = (new DataCreator()).GetBugs("E:\\BugReport\\CXF2.7.11.xml");
		int totalbugsCount = bugs.size();
		
		/*读取并保存java文件内容*/
        Utility.Status("Reading Files");
        List<File> allFiles = (new DataCreator()).GetFiles("E:\\GithubCode\\cxf.git\\cxf-2.7.11");
        int counter = 1;
        for(int i=0;i<allFiles.size();i++){
            Utility.Status("Reading " + (counter++) + " of " + allFiles.size());
            ArrayList<String> text = ReadAllLines(allFiles.get(i).toString());
            CodeFilesWithContent.put(allFiles.get(i).toString(), text);
        }
        
        /*初始化*/
        Utility.Status("Initializing");
        if (_runVsm)
            InitializeForVsm();
        
        /*TODO*/
        
	} 
	
	private static final MyDoubleDictionary IdfDictionary = new MyDoubleDictionary();
	private static final HashMap<String, MyDoubleDictionary> TfDictionary = new HashMap<String, MyDoubleDictionary>();
	private static final HashMap<String, MyDoubleDictionary> TfIdfDictionary = new HashMap<String, MyDoubleDictionary>();
	
	/*VSM初始化*/
	private static void InitializeForVsm(){
		/* compute tf and idf*/
		Iterator it = CodeFilesWithContent.keySet().iterator();   
		while(it.hasNext()){
            MyDoubleDictionary fileTfDictionary = new MyDoubleDictionary();
            
            /* for each word in the file add 1 to the count*/
            String key=(String)it.next();/*key:文件路径*/
            ArrayList<String> content=CodeFilesWithContent.get(key);
            for(int i=0;i<content.size();i++){
            	fileTfDictionary.Add(content.get(i));
            }

            /* save tf result for the file*/
            TfDictionary.put(key, fileTfDictionary);

            /* for each DISTINCT word found in the file increase the idf by 1.
             * At this point idf holds document frequency*/
            Iterator it2=fileTfDictionary.keySet().iterator();
            while(it2.hasNext()){
            	IdfDictionary.Add((String) it2.next());
            }
        }
        
		/* change df to idf*/
        int totalNumberOfDocuments = CodeFilesWithContent.size();
        Iterator it3=IdfDictionary.keySet().iterator();
        while(it3.hasNext()){	
			IdfDictionary.put((String) it3.next(), Math.log10(totalNumberOfDocuments / IdfDictionary.get(it3.next())));
		}
        
        /* update tfidf for each file*/
        Iterator it4=TfDictionary.keySet().iterator();
        while(it4.hasNext()){
            MyDoubleDictionary fileTfIdfDictionary = new MyDoubleDictionary();
            Iterator it5=TfDictionary.get((String)it4.next()).keySet().iterator();
            while(it5.hasNext()){
                fileTfIdfDictionary.put((String) it5.next(), TfDictionary.get((String)it4.next()).get(it5.next()) * IdfDictionary.get(it5.next()));
            }
            TfIdfDictionary.put((String) it4.next(), fileTfIdfDictionary);
        }   
        
	}
	
	/*VSM计算*/
	private static void ComputeVsm(String outputFolderPath, String bugName, List<String> queryText){
		Utility.Status("Creating VSM: " + bugName);

        /* CREATE QUERY TFIDF*/
		MyDoubleDictionary queryTfIdfDictionary = new MyDoubleDictionary();
		for(int i=0;i<queryText.size();i++)
			queryTfIdfDictionary.Add(queryText.get(i));
	
		/* max frequency*/
        List<Double> list=new ArrayList();
        Iterator it=queryTfIdfDictionary.entrySet().iterator();
        while(it.hasNext()){
            list.add(queryTfIdfDictionary.get(it.next()));
        }
        Collections.sort(list);
        double maxFrequency = list.get(list.size()-1);
                
        /* now multiply each by idf to get tfidf for query*/
        Iterator it2=queryTfIdfDictionary.entrySet().iterator();
        while(it2.hasNext()){
        	queryTfIdfDictionary.put((String) it2.next(), IdfDictionary.containsKey(it2.next())
        			? (queryTfIdfDictionary.get(it2.next())/maxFrequency)*IdfDictionary.get(it2.next()):0); 
        }
        
        /*CALCULATE SIMILARITY*/
        MyDoubleDictionary similarityDictionary = new MyDoubleDictionary();
        CosineSimilarityCalculator cosineSimilarityCalculator = new CosineSimilarityCalculator(queryTfIdfDictionary);
        
        /*TODO:compute similarity of fileText with each _codeFiles*/
        
        
	}
	
	/*逐行读取文件内容 存储至数组*/
	public ArrayList<String> ReadAllLines(String filePath){
		FileReader fileReader;
		try {
			fileReader = new FileReader(filePath);
	        BufferedReader bufferedReader =new BufferedReader(fileReader);
	        StringBuilder  stringBuilder =new StringBuilder();
	        ArrayList<String>  strings =new ArrayList<>();
	        String  str=null;
	        
	        while ((str=bufferedReader.readLine())!=null) {
	        	System.out.println(str);
	        	if (str.trim().length()>2) {
	        		strings.add(str);
	            }
	            	
	        	System.out.println(strings.size());
	        	for (int i = 0; i < strings.size(); i++) {
	        		System.out.println(strings.get(i));
	            }
	        }
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
