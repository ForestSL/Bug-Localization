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
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.sl.v0.buglocation.datas.Bug;
import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.buglocation.models.MyDoubleDictionary;
import com.sl.v0.datas.GlobalVar;

public class Calculator {
	
	public static BaseFunction basefunction=new BaseFunction();

	private static final String VsmCompletedFile = "CompletedVsm.txt";
	private static final String LsiCompletedFile = "CompletedLsi.txt";
	private static final String JsmCompletedFile = "CompletedJsm.txt";
	private static final String NgdCompletedFile = "CompletedNgd.txt";
	private static final String PmiCompletedFile = "CompletedPmi.txt";
	private static boolean runVSM;
	private static boolean runLSI;
	private static boolean runJSM;
	private static boolean runNGD;
	private static boolean runPMI;
	private static final String VsmFileName = "Results\\Vsm.txt";
	private static final String LsiOutputFolderName = "Results\\Lsi\\";
	private static final String JsmFileName = "Results\\Jsm.txt";
	private static final String NgdFileName = "Results\\Ngd.txt";
	private static final String PmiFileName = "Results\\Pmi.txt";
	private static final String CorpusWithFilterFolderName = "Corpus\\";
	private static final String QueryWithFilterFileName = "BugQuery.txt";
	
	/*存储文件名-文件内容映射*/
	private static final HashMap<String, ArrayList<String>> CodeFilesWithContent = new HashMap<String, ArrayList<String>>();
	
    /*测试用*/
	public static void main(String[] args) {  
		//(new DataCreator()).GetDatas();
		//RunVSM("GithubCode\\cxf.git\\cxf-2.7.11\\");
		boolean[] method={false,false,true,false,false};
		Run(GlobalVar.codeFolderName,method);
	} 
		
	public static void Run(String path,boolean[] method){
		/*method[0-4]:VSM,LSI,JSM,NGD,PMI*/
		runVSM=method[0];
		runLSI=method[1];
		/*JSM数据问题（已解决） hashmap存储new对象导致的数据覆盖 改用文件存储 也很慢 
		 * 决定改用初始化和计算放到一起 边读边计算数据*/
		runJSM=method[2];
		runNGD=method[3];
		runPMI=method[4];
		
		String datasetFolderPath = Utility.ReportFolderPath+path;
		String vsmCompletedFilePath = datasetFolderPath + VsmCompletedFile;
		String lsiCompletedFilePath = datasetFolderPath + LsiCompletedFile;
		String jsmCompletedFilePath = datasetFolderPath + JsmCompletedFile;
		String ngdCompletedFilePath = datasetFolderPath + NgdCompletedFile;
		String pmiCompletedFilePath = datasetFolderPath + PmiCompletedFile;
		
		File file=new File(vsmCompletedFilePath);		
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CompletedVsm.txt创建失败！");
			}
		}
		
		File file2=new File(lsiCompletedFilePath);		
		if (!file2.exists()){
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CompletedLsi.txt创建失败！");
			}
		}
		
		File file3=new File(jsmCompletedFilePath);		
		if (!file3.exists()){
			try {
				file3.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CompletedJsm.txt创建失败！");
			}
		}
		
		File file4=new File(ngdCompletedFilePath);		
		if (!file4.exists()){
			try {
				file4.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CompletedNgd.txt创建失败！");
			}
		}
		
		File file5=new File(pmiCompletedFilePath);		
		if (!file5.exists()){
			try {
				file5.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("CompletedPmi.txt创建失败！");
			}
		}
				
		ArrayList<String> completedVsm = basefunction.ReadAllLines(vsmCompletedFilePath);
		ArrayList<String> completedLsi = basefunction.ReadAllLines(lsiCompletedFilePath);
		ArrayList<String> completedJsm = basefunction.ReadAllLines(jsmCompletedFilePath);
		ArrayList<String> completedNgd = basefunction.ReadAllLines(ngdCompletedFilePath);
		ArrayList<String> completedPmi = basefunction.ReadAllLines(pmiCompletedFilePath);
		
		/*读取bug信息*/
		List<File> bugs=new ArrayList<File>();
		basefunction.CheckFileName(datasetFolderPath,bugs);
		int totalbugsCount = bugs.size();
		//System.out.println(totalbugsCount);
		
		/*读取java文件*/
        Utility.Status("Reading Files");
        JOptionPane.showMessageDialog(null,"正在读文件中……");
        List<File> allFiles = new ArrayList<File>();
        basefunction.GetFiles(datasetFolderPath + CorpusWithFilterFolderName,allFiles);
        int counter = 1;
        for(int i=0;i<allFiles.size();i++){
            //Utility.Status("Reading " + (counter++) + " of " + allFiles.size());
            ArrayList<String> text = basefunction.ReadAllLines(allFiles.get(i).toString());
            CodeFilesWithContent.put(allFiles.get(i).toString(), text);
        }
        //System.out.println("Corpus:"+CodeFilesWithContent.size());
        
        /*TODO:初始化*/
        Utility.Status("Initializing");
        JOptionPane.showMessageDialog(null,"定位方法初始化中……");
        if (runVSM || runPMI || runLSI || runJSM)
			InitializeForVsmLsi();
		if (runJSM)
			InitializeJsm(datasetFolderPath);
//		if (runNGD || runPMI )
//			InitializeForNgdPmi();
//		if (runLSI)
//			DoSvd();
        
        /*Create files*/
        int completedCount = 0;
        for(int i=0;i<totalbugsCount;i++){
        	++completedCount;
        	try{
				Utility.Status("Creating Stuffs: " + bugs.get(i).getName() + " " + completedCount + " of " + totalbugsCount);

				if ( ! ( runVSM&&!completedVsm.contains(bugs.get(i).getName()) 
						|| runLSI&&!completedLsi.contains(bugs.get(i).getName()) 
						|| runJSM&&!completedJsm.contains(bugs.get(i).getName()) 
						|| runNGD&&!completedNgd.contains(bugs.get(i).getName()) 
						|| runPMI&&!completedPmi.contains(bugs.get(i).getName()))){
					Utility.Status("Already Completed Stuff: " + bugs.get(i).getName() + " " + completedCount + " of " + totalbugsCount);
				}else{

				String bugFolderPath = datasetFolderPath + bugs.get(i).getName() + "\\";
				if (!(new File(bugFolderPath + "Results")).isDirectory()){
					(new File(bugFolderPath + "Results")).mkdirs();
				}

				ArrayList<String> queryText = basefunction.ReadAllLines(bugFolderPath + QueryWithFilterFileName);
				//System.out.println("query:"+queryText.get(1));
				
				/*TODO:添加其他方法*/
				if ( runVSM && !completedVsm.contains(bugs.get(i).getName())){
					ComputeVsm(bugFolderPath, bugs.get(i).getName(), queryText);
					completedVsm.add(bugs.get(i).getName());
				}
				
//				if ( runLSI && !completedLsi.contains(bugs.get(i).getName()))
//				{
//					if (!(new File(bugFolderPath + LsiOutputFolderName)).isDirectory())
//					{
//						(new File(bugFolderPath + LsiOutputFolderName)).mkdirs();
//					}
//
//					ComputeLsi(bugFolderPath, bugs.get(i).getName(), queryText);
//					completedLsi.add(bugs.get(i).getName());
//				}

				if ( runJSM && !completedJsm.contains(bugs.get(i).getName())){
					ComputeJsm(bugFolderPath, bugs.get(i).getName(), queryText);
					completedJsm.add(bugs.get(i).getName());
				}
				
//				if ( runNGD && !completedNgd.contains(bugs.get(i).getName()))
//				{
//					ComputeNgd(bugFolderPath, bugs.get(i).getName(), queryText);
//					completedNgd.add(bugs.get(i).getName());
//				}
				
//				if ( runPMI && !completedPmi.contains(bugs.get(i).getName()))
//				{
//					ComputePmi(bugFolderPath, bugs.get(i).getName(), queryText);
//					completedPmi.add(bugs.get(i).getName());
//				}
				
				Utility.Status("DONE Creating Stuff: " + bugs.get(i).getName() + " (" + completedCount + " of " + totalbugsCount + ")");
			
				}
        	}
			catch (RuntimeException e)
			{
				Utility.WriteErrorCommon(path + bugs.get(i).getName(), e.getMessage());
				Utility.Status("ERROR Creating Stuff: " + path + bugs.get(i).getName() + " (" + completedCount + " of " + totalbugsCount + ")");
			}
			finally
			{
				if (runVSM)
					basefunction.WriteAllLines(vsmCompletedFilePath, completedVsm);
				if (runLSI)
					basefunction.WriteAllLines(lsiCompletedFilePath, completedLsi);
				if (runJSM)
					basefunction.WriteAllLines(jsmCompletedFilePath, completedJsm);
				if (runNGD)
					basefunction.WriteAllLines(ngdCompletedFilePath, completedNgd);
				if (runPMI)
					basefunction.WriteAllLines(pmiCompletedFilePath, completedPmi);				
			}
        }
        JOptionPane.showMessageDialog(null,"生成java文件与bug报告相似度文件");        
	} 
	
	/*VSM start*/
	private static final MyDoubleDictionary IdfDictionary = new MyDoubleDictionary();
	private static final HashMap<String, MyDoubleDictionary> TfDictionary = new HashMap<String, MyDoubleDictionary>();
	private static final HashMap<String, MyDoubleDictionary> TfIdfDictionary = new HashMap<String, MyDoubleDictionary>();
	
	private static void InitializeForVsmLsi(){
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
	
	private static void ComputeVsm(String outputFolderPath, String bugName, List<String> queryText){
		Utility.Status("Creating VSM: " + bugName);
		
		/*判断相似度文件是否已经生成*/
		if ((new File(outputFolderPath + VsmFileName)).isFile()){
			Utility.Status("Vsm File Exists.");
			return;
		}

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
	/*VSM end*/
	
	/*JSM start*/
	//private static final HashMap<String, double[]> SourceVectors = new HashMap<String, double[]>();
	//static String SourceVectorsFile = Utility.ReportFolderPath+GlobalVar.codeFolderName+"SourceVectors.txt"; 
	private static final ArrayList<String> AllUniqueWordsInSourceAndQuery = new ArrayList<String>();
	private static final ArrayList<String> allQueryTexts = new ArrayList<String>();
	
    private static void InitializeJsm(String datasetFolderPath){
    	
    	List<File> bugs = new ArrayList<File>();
    	basefunction.CheckFileName(datasetFolderPath, bugs);
    	
    	for(int i=0;i<bugs.size();i++){
    		ArrayList<String> content=new ArrayList<String>();
    		content=basefunction.ReadAllLines(bugs.get(i).getAbsolutePath()+"\\"+QueryWithFilterFileName);
    		for(int j=0;j<content.size();j++)
    			allQueryTexts.add(content.get(j));
    	}

    	/* create the vector for each source code*/
    	ArrayList<String> keylist=new ArrayList<String>();
    	//keylist=(ArrayList<String>) IdfDictionary.keySet();
    	Iterator it = IdfDictionary.keySet().iterator();   
		while(it.hasNext()){
			keylist.add((String) it.next());
		}
    	allQueryTexts.addAll(keylist);
    	AllUniqueWordsInSourceAndQuery.removeAll(allQueryTexts);
    	AllUniqueWordsInSourceAndQuery.addAll(allQueryTexts);
		
    }
    
    private static void ComputeJsm(String outputFolderPath, String bugName, List<String> queryText){
		
		Utility.Status("Computing Source Vectors Jsm: " + bugName);

		if ((new File(outputFolderPath + JsmFileName)).isFile()){
			Utility.Status("Jsm File Exists.");
			return;
		}
		
		/* create the vector for query*/
		double[] queryVector = new double[AllUniqueWordsInSourceAndQuery.size()];
		int queryCounter = 0;
		for(int i=0;i<AllUniqueWordsInSourceAndQuery.size();i++){
			String uniqueWord=AllUniqueWordsInSourceAndQuery.get(i);
			//System.out.println(uniqueWord);
			if(queryText.contains(uniqueWord)){
				//System.out.println("-------------------------");
				double tmp=0;/*queryText中uniqueWord的个数*/
				for(int j=0;j<queryText.size();j++){
					if(queryText.get(j).equals(uniqueWord))
						tmp++;
				}
				//System.out.println(tmp);
				queryVector[queryCounter] = tmp / queryText.size();
				//System.out.println(queryVector[queryCounter]);
			}else{
				queryVector[queryCounter] = 0;
			}
			//System.out.println(queryVector[queryCounter]);
			queryCounter++;
		}

		MyDoubleDictionary similarityDictionary = new MyDoubleDictionary();
		double[] p=new double[AllUniqueWordsInSourceAndQuery.size()];
		Iterator it2 = TfDictionary.keySet().iterator();   
		while(it2.hasNext()){
			String key=(String) it2.next();
			MyDoubleDictionary tfDictionary = TfDictionary.get(key);
			int totalWordsInFile = CodeFilesWithContent.get(key).size();
			
			int counter=0;
			for(int i=0;i<AllUniqueWordsInSourceAndQuery.size();i++){
				double d = tfDictionary.containsKey(AllUniqueWordsInSourceAndQuery.get(i)) 
						? tfDictionary.get(AllUniqueWordsInSourceAndQuery.get(i)) / totalWordsInFile : 0;
				p[counter]=d;
				counter++;
			}
			
			/*直接针对每行进行计算*/
			/* calculate H(p), H(q) and H(p + q)*/
			double sumEntropy = (new Extensions()).JensenEntropy(((new Extensions()).JensenSum(p,queryVector)));
            double pEntropy = 1.0 / 2 * (new Extensions()).JensenEntropy(p);
            double qEntropy = 1.0 / 2 * (new Extensions()).JensenEntropy(queryVector);
            
            //System.out.println(sumEntropy);

            double jensenDivergence = sumEntropy - pEntropy - qEntropy;
            double jensenSimilarity = 1 - jensenDivergence;

            similarityDictionary.put(key, jensenSimilarity);
		}       
		
		/*done*/
        WriteDocumentVectorToFileOrderedDescending(outputFolderPath + JsmFileName, similarityDictionary);

        Utility.Status("DONE Computing Source Vectors Jensen: " + bugName);

    }
	/*JSM end*/
	
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
        basefunction.WriteAllLines(filePath, content);
    }

}
