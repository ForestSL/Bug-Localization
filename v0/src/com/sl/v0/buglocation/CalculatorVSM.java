package com.sl.v0.buglocation;

/*VSM计算：得每个bug下所有java与该bug的相似度*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.sl.v0.buglocation.datas.Bug;
import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.buglocation.models.MyDoubleDictionary;
import com.sl.v0.datas.GlobalVar;

public class CalculatorVSM {

	private static final String VsmCompletedFile = "CompletedVsm.txt";
	private static boolean _cleanPrevious;
	private static boolean _runVsm;
	private static final String VsmFileName = "Results\\Vsm.txt";
	private static final String CorpusWithFilterFolderName = "Corpus\\";
	private static final String QueryWithFilterFileName = "BugQuery.txt";
	
	/*存储文件名-文件内容映射*/
	private static final HashMap<String, ArrayList<String>> CodeFilesWithContent = new HashMap<String, ArrayList<String>>();
	
    /*测试用*/
//	public static void main(String[] args) {  
//		(new DataCreator()).GetDatas();
//		//RunVSM("GithubCode\\cxf.git\\cxf-2.7.11\\");
//		RunVSM(GlobalVar.codeFolderName);
//	} 
		
	public static void RunVSM(String path){
		
		String datasetFolderPath = Utility.ReportFolderPath+path;
		String vsmCompletedFilePath = datasetFolderPath + VsmCompletedFile;
		
		File file=new File(vsmCompletedFilePath);		
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CompletedVsm.txt创建失败！");
			}
		}
				
		ArrayList<String> completedVsm = (new BaseFunction()).ReadAllLines(vsmCompletedFilePath);
		
		List<File> bugs=new ArrayList<File>();
		(new BaseFunction()).CheckFileName(datasetFolderPath,bugs);
		int totalbugsCount = bugs.size();
		//System.out.println(totalbugsCount);
		
        Utility.Status("Reading Files");
        JOptionPane.showMessageDialog(null,"正在读文件中……");
        List<File> allFiles = new ArrayList<File>();
        (new BaseFunction()).GetFiles(datasetFolderPath + CorpusWithFilterFolderName,allFiles);
        int counter = 1;
        for(int i=0;i<allFiles.size();i++){
            //Utility.Status("Reading " + (counter++) + " of " + allFiles.size());
            ArrayList<String> text = (new BaseFunction()).ReadAllLines(allFiles.get(i).toString());
            CodeFilesWithContent.put(allFiles.get(i).toString(), text);
        }
        //System.out.println("Corpus:"+CodeFilesWithContent.size());
        
        /*初始化*/
        Utility.Status("Initializing");
        JOptionPane.showMessageDialog(null,"定位方法初始化中……");
        //if (_runVsm)
            InitializeForVsm();
        
        /*Create files*/
        int completedCount = 0;
        for(int i=0;i<totalbugsCount;i++){
        	++completedCount;
        	try
			{
				Utility.Status("Creating Stuffs: " + bugs.get(i).getName() + " " + completedCount + " of " + totalbugsCount);

				if (completedVsm.contains(bugs.get(i).getName()))
				{
					Utility.Status("Already Completed Stuff: " + bugs.get(i).getName() + " " + completedCount + " of " + totalbugsCount);
					return;
				}

				String bugFolderPath = datasetFolderPath + bugs.get(i).getName() + "\\";

				if (!(new File(bugFolderPath + "Results")).isDirectory())
				{
					(new File(bugFolderPath + "Results")).mkdirs();
				}

				ArrayList<String> queryText = (new BaseFunction()).ReadAllLines(bugFolderPath + QueryWithFilterFileName);
				//System.out.println("query:"+queryText.get(1));
				
				if (!completedVsm.contains(bugs.get(i).getName()))
				{
					ComputeVsm(bugFolderPath, bugs.get(i).getName(), queryText);
					completedVsm.add(bugs.get(i).getName());
				}

				Utility.Status("DONE Creating Stuff: " + bugs.get(i).getName() + " (" + completedCount + " of " + totalbugsCount + ")");
			}
			catch (RuntimeException e)
			{
				Utility.WriteErrorCommon(path + bugs.get(i).getName(), e.getMessage());
				Utility.Status("ERROR Creating Stuff: " + path + bugs.get(i).getName() + " (" + completedCount + " of " + totalbugsCount + ")");
			}
			finally
			{
				//if (_runVsm)
				//{
					(new BaseFunction()).WriteAllLines(vsmCompletedFilePath, completedVsm);
				//}
				
			}
        }
        JOptionPane.showMessageDialog(null,"生成java文件与bug报告相似度文件");
        
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
            	String key2=(String) it2.next();
            	IdfDictionary.Add(key2);
            }
        }
        
		/* change df to idf*/
        int totalNumberOfDocuments = CodeFilesWithContent.size();
        Iterator it3=IdfDictionary.keySet().iterator();
        while(it3.hasNext()){	
        	String key3=(String) it3.next();
			IdfDictionary.put(key3, Math.log10(totalNumberOfDocuments / IdfDictionary.get(key3)));
		}
        
        /* update tfidf for each file*/
        Iterator it4=TfDictionary.keySet().iterator();
        while(it4.hasNext()){
            MyDoubleDictionary fileTfIdfDictionary = new MyDoubleDictionary();
            MyDoubleDictionary mdd5=new MyDoubleDictionary();
            String key4=(String)it4.next();
            mdd5=TfDictionary.get(key4);
            Iterator it5= mdd5.keySet().iterator();
            while(it5.hasNext()){
            	MyDoubleDictionary mdd=new MyDoubleDictionary();
            	mdd=TfDictionary.get(key4);
            	String key5=(String)it5.next();
            	if(mdd.containsKey(key5)&&IdfDictionary.containsKey(key5)){
            		Double dvalue= mdd.get(key5) * IdfDictionary.get(key5);
            		fileTfIdfDictionary.put(key5, dvalue);
            	}else{
            		System.out.println("----------------------------");
            	}
            }
            TfIdfDictionary.put(key4, fileTfIdfDictionary);
        }   
        
	}
	
	/*VSM计算*/
	private static void ComputeVsm(String outputFolderPath, String bugName, List<String> queryText){
		Utility.Status("Creating VSM: " + bugName);

        /* CREATE QUERY TFIDF*/
		MyDoubleDictionary queryTfIdfDictionary = new MyDoubleDictionary();
		for(int i=0;i<queryText.size();i++){
			//System.out.println(queryText.get(i));
			queryTfIdfDictionary.Add(queryText.get(i));
		}
		//System.out.println(queryTfIdfDictionary.size());
	
		/* max frequency*/
        ArrayList<Double> list=new ArrayList<Double>();
        Iterator it=queryTfIdfDictionary.keySet().iterator();
        while(it.hasNext()){
        	Object key= it.next();
        	//System.out.println(key.toString());
        	//System.out.println(queryTfIdfDictionary.get(key));
            list.add((Double)queryTfIdfDictionary.get(key));
        }
        Collections.sort(list);
        double maxFrequency = list.get(list.size()-1);
                
        /* now multiply each by idf to get tfidf for query*/
        Iterator it2=queryTfIdfDictionary.keySet().iterator();
        while(it2.hasNext()){
        	String key2=(String) it2.next();
        	queryTfIdfDictionary.put(key2, IdfDictionary.containsKey(key2)
        			? (queryTfIdfDictionary.get(key2)/maxFrequency)*IdfDictionary.get(key2):0); 
        }
        
        /*CALCULATE SIMILARITY*/
        MyDoubleDictionary similarityDictionary = new MyDoubleDictionary();
        CosineSimilarityCalculator cosineSimilarityCalculator = new CosineSimilarityCalculator(queryTfIdfDictionary);
        
        /*compute similarity of fileText with each _codeFiles*/
        Iterator it3=TfIdfDictionary.keySet().iterator();
        while(it3.hasNext()){
        	String key3=(String) it3.next();
        	//if(it3.next()=="675"||it3.next()=="6405"){
        		double cosineSimilarityWithUseCase = cosineSimilarityCalculator.GetSimilarity(TfIdfDictionary.get(key3));
        		similarityDictionary.put(key3, cosineSimilarityWithUseCase);
            //}
        }
        
        /*WRITE TO FILE*/
        WriteDocumentVectorToFileOrderedDescending(outputFolderPath + VsmFileName, similarityDictionary);

        Utility.Status("Completed VSM: " + bugName);
        
	}
	
	/*Writes vector to file ordered*/
	public static void WriteDocumentVectorToFileOrderedDescending(String filePath, MyDoubleDictionary vector)
    {
		boolean asInt = false;
        String pattern = asInt ? "##" : "##.00000";
        
        /*将vector.entrySet()转换成list*/  
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(vector.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {  
            /*按value降序排序*/  
            @Override  
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {   
                return o2.getValue().compareTo(o1.getValue());  
            }  
        }); 
        
        ArrayList<String> content=new ArrayList<String>();
//        Iterator it=vector.keySet().iterator();
//        while(it.hasNext()){
//        	String key=(String) it.next();
//        	/*格式化vector.get(it.next())*/
//        	String.format(pattern, vector.get(key));
//        	content.add(key+" "+vector.get(key));
//        }
        for(int i=0;i<list.size();i++){
        	String key=list.get(i).getKey();
        	Double value=list.get(i).getValue();
        	String.format(pattern, value);
        	content.add(key+" "+value);
        }
        
        /*逐行写入文件*/
        (new BaseFunction()).WriteAllLines(filePath, content);
    }

}
