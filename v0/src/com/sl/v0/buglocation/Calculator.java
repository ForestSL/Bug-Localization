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

import Jama.Matrix;
import Jama.SingularValueDecomposition;

import com.sl.v0.buglocation.datas.Bug;
import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.buglocation.models.DocumentDictionaryAny;
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
		boolean[] method={false,false,false,false,true};
		Run(GlobalVar.codeFolderName,method);
	} 
		
	public static void Run(String path,boolean[] method){
		/*method[0-4]:VSM,LSI,JSM,NGD,PMI*/
		runVSM=method[0];/*成功 原始文件数计算速度正常 */
		runLSI=method[1];/*文件5000多个 矩阵无法计算 JVM不够 所以5种方法演示以100个文件为例*/
		/*JSM数据问题（已解决） hashmap存储new对象导致的数据覆盖 改用文件存储 也很慢 
		 * 决定改用初始化和计算放到一起 边读边计算数据*/
		runJSM=method[2];/*成功 原始文件数计算过慢 100文件测试速度正常*/
		runNGD=method[3];/*成功 以100文件测试*/
		runPMI=method[4];/*成功 以100文件测试*/
		
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
        
        /*TODO:为了方便演示项目 文件只读100个 演示计算结果*/
        int time=allFiles.size();
        if(allFiles.size()>100)
        	time=100;
        
        for(int i=0;i<time;i++){
            //Utility.Status("Reading " + (counter++) + " of " + allFiles.size());
            ArrayList<String> text = basefunction.ReadAllLines(allFiles.get(i).toString());
            CodeFilesWithContent.put(allFiles.get(i).toString(), text);
        }
        //System.out.println("Corpus:"+CodeFilesWithContent.size());
        
        /*初始化*/
        Utility.Status("Initializing");
        JOptionPane.showMessageDialog(null,"定位方法初始化中……");
        if (runVSM || runPMI || runLSI || runJSM)
			InitializeForVsmLsi();
		if (runJSM)
			InitializeJsm(datasetFolderPath);
		if (runNGD || runPMI )
			InitializeForNgdPmi();
		if (runLSI)
			DoSvd();
        
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
				
				if ( runVSM && !completedVsm.contains(bugs.get(i).getName())){
					ComputeVsm(bugFolderPath, bugs.get(i).getName(), queryText);
					completedVsm.add(bugs.get(i).getName());
				}
				
				if ( runLSI && !completedLsi.contains(bugs.get(i).getName()))
				{
					if (!(new File(bugFolderPath + LsiOutputFolderName)).isDirectory())
					{
						(new File(bugFolderPath + LsiOutputFolderName)).mkdirs();
					}

					ComputeLsi(bugFolderPath, bugs.get(i).getName(), queryText);
					completedLsi.add(bugs.get(i).getName());
				}

				if ( runJSM && !completedJsm.contains(bugs.get(i).getName())){
					ComputeJsm(bugFolderPath, bugs.get(i).getName(), queryText);
					completedJsm.add(bugs.get(i).getName());
				}
				
				if ( runNGD && !completedNgd.contains(bugs.get(i).getName()))
				{
					ComputeNgd(bugFolderPath, bugs.get(i).getName(), queryText);
					completedNgd.add(bugs.get(i).getName());
				}
				
				if ( runPMI && !completedPmi.contains(bugs.get(i).getName()))
				{
					ComputePmi(bugFolderPath, bugs.get(i).getName(), queryText);
					completedPmi.add(bugs.get(i).getName());
				}
				
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
	
	/*LSI start*/
	private static HashMap<Integer, Matrix> _uk;
	private static HashMap<Integer, Matrix> _sk;
	private static HashMap<Integer, Matrix> _vkTranspose;
	
	private static void DoSvd(){
		Utility.Status("Creating SVD");
		
		/*create the matrix*/
		int totalNumberOfSourceFiles = TfDictionary.size();
		int totalDistinctTermsInAllSourceFiles = IdfDictionary.size();

		HashMap<String, Integer> allSourceFilesWithIndex = new HashMap<String, Integer>();
		int index=0;
		for (Map.Entry<String, MyDoubleDictionary> fileNameWithTfDictionary : TfDictionary.entrySet()){
			allSourceFilesWithIndex.put(fileNameWithTfDictionary.getKey(), index);
			index++;
		}
		HashMap<String, Integer> allSourceWordsWithIndex = new HashMap<String, Integer>();
		index=0;
		for (Map.Entry<String, Double> fileWordWithTf : IdfDictionary.entrySet()){
			allSourceWordsWithIndex.put(fileWordWithTf.getKey(), index);
			index++;
		}
		
		// row, col row is word col docs
		double[][] sourceMatrix = new double[totalDistinctTermsInAllSourceFiles][totalNumberOfSourceFiles];		
		for (Map.Entry<String, MyDoubleDictionary> fileNameWithTfDictionary : TfDictionary.entrySet()){
			int fileIndex = allSourceFilesWithIndex.get(fileNameWithTfDictionary.getKey());
			Iterator it = (fileNameWithTfDictionary.getValue()).keySet().iterator();   
			while(it.hasNext()){
				String fileWordWithTf=(String) it.next();
				sourceMatrix[allSourceWordsWithIndex.get(fileWordWithTf)][fileIndex] = (fileNameWithTfDictionary.getValue()).get(fileWordWithTf);
			}
		}
//		for(int i=0;i<sourceMatrix.length;i++)
//			for(int j=0;j<sourceMatrix[i].length;j++)
//				System.out.println(sourceMatrix[i][j]);
		
		// create matrix
		Matrix generalMatrix = new Matrix(sourceMatrix);

		// singular value decomposition	
		//SVD svd = new SVD(generalMatrix);
		SingularValueDecomposition svd=generalMatrix.svd();

		_uk = new HashMap<Integer, Matrix>();
		_sk = new HashMap<Integer, Matrix>();
		_vkTranspose = new HashMap<Integer, Matrix>();
		
		ArrayList<Integer> tmp=new ArrayList<Integer>();
		for(int i=0;i<Utility.LsiKs.size();i++){
			//System.out.println(svd.getS().getColumnDimension());
			if(Utility.LsiKs.get(i)< svd.getS().getColumnDimension())
				tmp.add(Utility.LsiKs.get(i));
		}

		//System.out.println(tmp.size());
		
		for(int i=0;i<tmp.size();i++){
			int k=tmp.get(i);
			Utility.Status("Creating k matrix of size " + k);
			_uk.put(k, new Matrix(svd.getU().getArray(), svd.getU().getRowDimension(), k));
			_sk.put(k, new Matrix(svd.getS().getArray(), k, k));
			_vkTranspose.put(k, new Matrix(svd.getV().getArray(), k, svd.getV().getColumnDimension()));
		}
	}
	
	private static void ComputeLsi(String outputFolderPath, String bugName, ArrayList<String> queryText){
		Utility.Status("Creating LSI: " + bugName);

		int totalDistinctTermsInAllSourceFiles = IdfDictionary.size();

		HashMap<String, Integer> allSourceFilesWithIndex = new HashMap<String, Integer>();
		int index=0;
		for (Map.Entry<String, MyDoubleDictionary> fileNameWithTfDictionary : TfDictionary.entrySet()){
			allSourceFilesWithIndex.put(fileNameWithTfDictionary.getKey(), index);
			index++;
		}
		HashMap<String, Integer> allSourceWordsWithIndex = new HashMap<String, Integer>();
		index=0;
		for (Map.Entry<String, Double> fileWordWithTf : IdfDictionary.entrySet()){
			allSourceWordsWithIndex.put(fileWordWithTf.getKey(), index);
			index++;
		}
		
		// create one for query as well
		double[][] queryMatrixTranspose = new double[1][totalDistinctTermsInAllSourceFiles];
		for(String queryWord:queryText){
			if (allSourceWordsWithIndex.containsKey(queryWord)){
				queryMatrixTranspose[0][allSourceWordsWithIndex.get(queryWord)] = queryMatrixTranspose[0][allSourceWordsWithIndex.get(queryWord)] + 1;
			}
		}
		
		ArrayList<Object> ks = new ArrayList<Object>();
		Iterator it = _uk.keySet().iterator();   
		while(it.hasNext()){
			int x=(int) it.next();
			if(!(new File(outputFolderPath + LsiOutputFolderName + x + ".txt")).isFile())
				ks.add(x);
		}
	
		for (Object k : ks){
			Utility.Status("Creating LSI for " + bugName + " where k=" + k);
			Matrix uk = _uk.get(k);
			Matrix sk = _sk.get(k);
			Matrix vkTranspose = _vkTranspose.get(k);

			Matrix q = new Matrix(queryMatrixTranspose);
			Matrix tmp=q.times(uk);
			Matrix qv = tmp.times(sk.inverse());
			ArrayList<Double> qDoubles =new ArrayList<Double>();
			for(int i=0;i<qv.getColumnDimension();i++)
				qDoubles.add(qv.get(0, i));

			HashMap<String,Double> similarityList=new HashMap<String,Double>();
			Iterator it2 = allSourceFilesWithIndex.keySet().iterator();   
			while(it2.hasNext()){
				String doc=(String) it2.next();
				List<Double> list=new ArrayList<Double>();
				for(int i=0;i<vkTranspose.getArray().length;i++)
					list.add(vkTranspose.getArray()[i][allSourceFilesWithIndex.get(doc)]);
				
				double tdouble=GetSimilarity(qDoubles, list);
				similarityList.put(doc,tdouble);
			}
			
			ArrayList<String> content=new ArrayList<String>();
			
			boolean asInt = false;
	        String pattern = asInt ? "##" : "##.00000";
	        /*将vector.entrySet()转换成list*/  
	        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(similarityList.entrySet());  
	        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {  
	            /*按value降序排序*/  
	            @Override  
	            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {   
	                return o2.getValue().compareTo(o1.getValue());  
	            }  
	        }); 	        
	        for(int i=0;i<list.size();i++){
	        	String key=list.get(i).getKey();
	        	Double value=list.get(i).getValue();
	        	String.format(pattern, value);
	        	content.add(key+" "+value);
	        }
	        //System.out.println(k);
			basefunction.WriteAllLines(outputFolderPath + LsiOutputFolderName + k + ".txt", content);
		}

		Utility.Status("Completed LSI: " + bugName);		
	}
	
	private static double GetSimilarity(List<Double> a1, List<Double> a2){
		double dotProduct = 0;
		double aSum = 0, bSum = 0;

		for (int i = 0; i < a1.size(); i++){
			dotProduct += a1.get(i) * a2.get(i);
			aSum += Math.pow(a1.get(i), 2);
			bSum += Math.pow(a2.get(i), 2);
		}

		return dotProduct / (Math.sqrt(aSum) * Math.sqrt(bSum));
	}
	/*LSI end*/
	
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
    
    /*NGD start*/
    private static void ComputeNgd(String ngdOutputFolderPath, String bugName, ArrayList<String> fileText){
    	Utility.Status("Creating NGD: " + bugName);
    	
    	if ((new File(ngdOutputFolderPath + NgdFileName)).isFile()){
			Utility.Status("Ngd File Exists.");
			return;
		}

		MyDoubleDictionary tssDocumentDictionary = new MyDoubleDictionary();
		double logD = Math.log10(CodeFilesWithContent.size() + 1); // just make the N bigger than any
		
		/* Create list of word contained in query*/
		ArrayList<String> distinctQueryWordList = new ArrayList<String>();
		for(int i=0;i<fileText.size();i++){
			if(!distinctQueryWordList.contains(fileText.get(i)))/*去重*/
				distinctQueryWordList.add(fileText.get(i));
		}
		DocumentDictionaryAny<MyDoubleDictionary> ngdMatrix = new DocumentDictionaryAny<MyDoubleDictionary>();
		
		for (String queryWordW2 : distinctQueryWordList){
			MyDoubleDictionary ngdDictionary = new MyDoubleDictionary();

			for (String sourceWordW1 : WordAndContainingFiles.keySet()){
				boolean sourceContainsUseCaseWord = WordAndContainingFiles.containsKey(queryWordW2);

				int countD1 = WordAndContainingFiles.get(sourceWordW1).size();
				// number of file containing W1 + if query also contains the word
				int countD2 = sourceContainsUseCaseWord ? WordAndContainingFiles.get(queryWordW2).size() : 0;
				// if query contains source then add 1 (query contains usecase word + source word
				// if source contains query word find the intersection of files containing both words
				List<String> result = new ArrayList<String>();/*存储两个list的交集*/
				for (String arr : WordAndContainingFiles.get(sourceWordW1)) {//遍历list1
					//System.out.println(arr);
					if(WordAndContainingFiles.get(queryWordW2)!=null){
						if (WordAndContainingFiles.get(queryWordW2).contains(arr)) {//如果存在这个数
							result.add(arr);//放进一个list里面，这个list就是交集
						}
					}
				}
				int tmp= result.size();				
				int countD1D2 = sourceContainsUseCaseWord ? tmp : 0;

				// d1 and d2 will never be 0, d1d2 however can be
				double ngd = (countD1D2 == 0) ? 0 : ComputenNgd(countD1, countD2, countD1D2, logD);

				ngdDictionary.put(sourceWordW1, ngd);
			}
			ngdMatrix.put(queryWordW2, ngdDictionary);
		}
		
		ArrayList<String> distinctQueryWordListForTss = new ArrayList<String>();
		for(int i=0;i<fileText.size();i++){
			//if(!distinctQueryWordListForTss.contains(fileText.get(i)))/*去重*/
				distinctQueryWordListForTss.add(fileText.get(i));
		}
		
		int totalNumberOfDocumentInSource = CodeFilesWithContent.size();
		
		for (Map.Entry<String, ArrayList<String>> sourceFileWithWords : CodeFilesWithContent.entrySet()){
			ArrayList<String> distinctSourceWords = new ArrayList<String>();
			//System.out.println(sourceFileWithWords);
			if(CodeFilesWithContent.get(sourceFileWithWords.getKey())!=null){
				for(int i=0;i<CodeFilesWithContent.get(sourceFileWithWords.getKey()).size();i++){
					//if(!distinctSourceWords.contains(fileText.get(i)))/*去重*/
						distinctSourceWords.add(CodeFilesWithContent.get(sourceFileWithWords.getKey()).get(i));
				}
			}
			
			double sumQueryTimeIdf = 0.0;
			double sumQueryIdf = 0.0;

			for (String queryWord : distinctQueryWordListForTss){
				double maxSim = -1;
				//System.out.println(distinctSourceWords.size());/*错误：输出为空*/
				for (String sourceWord : distinctSourceWords){
					if(ngdMatrix.get(queryWord)!=null){
						if(ngdMatrix.get(queryWord).get(sourceWord)!=null){
							double currentNgd = ngdMatrix.get(queryWord).get(sourceWord);
							//System.out.println(currentNgd);
							if (maxSim < currentNgd){
								maxSim = currentNgd;
							}
						}
					}
				}
				//System.out.println(maxSim);

				// if term does not occur in any corpus then its only in use case hence 1
				double idf = 0;
				if (WordAndContainingFiles.containsKey(queryWord)){
					idf = Math.log10((double)totalNumberOfDocumentInSource / WordAndContainingFiles.get(queryWord).size());
				}
				sumQueryIdf += idf;
				sumQueryTimeIdf += (maxSim * idf);
			}

			double sumCorpusTimeIdf = 0.0;
			double sumCorpusIdf = 0.0;

			for (String sourceWord : distinctSourceWords){
				double maxSim = -1;
				for (String queryWord : distinctQueryWordListForTss){
					if(ngdMatrix.get(queryWord)!=null){
						if(ngdMatrix.get(queryWord).get(sourceWord)!=null){
							double currentNgd = ngdMatrix.get(queryWord).get(sourceWord);
							if (maxSim < currentNgd){
								maxSim = currentNgd;
							}
						}
					}
				}

				// sourceWord has to be in IdfDictionary
				if(WordAndContainingFiles.get(sourceWord)!=null){
					double idf = Math.log10((double)totalNumberOfDocumentInSource / WordAndContainingFiles.get(sourceWord).size());

					sumCorpusIdf += idf;
					sumCorpusTimeIdf += (maxSim * idf);
				}
			}
			
//			System.out.println(sumQueryTimeIdf);
//			System.out.println(sumQueryIdf);
//			System.out.println(sumCorpusTimeIdf);
//			System.out.println(sumCorpusIdf);
//			System.out.println("-------------------------------");

			double tss = (1.0 / 2) * ((sumQueryTimeIdf / sumQueryIdf) + (sumCorpusTimeIdf / sumCorpusIdf));
			//System.out.println(tss);
			tssDocumentDictionary.put(sourceFileWithWords.getKey(), tss);
		}
		
		WriteDocumentVectorToFileOrderedDescending(ngdOutputFolderPath + NgdFileName, tssDocumentDictionary);

		Utility.Status("Completed NGD: " + bugName);
		
    }
    
	private static double ComputenNgd(double d1, double d2, double d1D2, double logD)
	{
		double logD1 = Math.log10(d1);
		double logD2 = Math.log10(d2);
		double logD1D2 = Math.log10(d1D2);

		double upper = Math.max(logD1, logD2) - logD1D2;
		double lower = logD - Math.min(logD1, logD2);
		double ngd = upper / lower;

		return Math.pow(Math.E, -2 * ngd);
	}
    /*NGD end*/
    
    /*PMI start*/
    private static final HashMap<String, ArrayList<String>> WordAndContainingFiles = new HashMap<String, ArrayList<String>>();
    
    private static void InitializeForNgdPmi(){
    	
    	Iterator it = CodeFilesWithContent.keySet().iterator();   
		while(it.hasNext()){
			String key=(String) it.next();
			ArrayList<String> wordlist=new ArrayList<String>();
			for(int i=0;i<CodeFilesWithContent.get(key).size();i++)
				wordlist.add(CodeFilesWithContent.get(key).get(i));
			for(int i=0;i<wordlist.size();i++){
				if (!WordAndContainingFiles.containsKey(wordlist.get(i)))
				{
					WordAndContainingFiles.put(wordlist.get(i), new ArrayList<String>());
				}
				WordAndContainingFiles.get(wordlist.get(i)).add(key);
			}
		}
		
	}
    
    private static void ComputePmi(String pmiOutputFolderPath, String reqName, ArrayList<String> reqText){
    	Utility.Status("Creating Pmi: " + reqName);
    	
		if ((new File(pmiOutputFolderPath + PmiFileName)).isFile()){
			Utility.Status("Pmi File Exists.");
			return;
		}

		/* Create list of word contained in query*/
		ArrayList<String> distinctReqWordList = new ArrayList<String>();
		for(int i=0;i<reqText.size();i++){
			if(!distinctReqWordList.contains(reqText.get(i)))/*去重*/
				distinctReqWordList.add(reqText.get(i));
		}
		DocumentDictionaryAny<MyDoubleDictionary> nPmiMatrix = new DocumentDictionaryAny<MyDoubleDictionary>();
		int n = CodeFilesWithContent.size();
		
		/* Compute pmi for each word in WordAndContainingFiles and unique words in query*/
		for (String reqWordW2 : distinctReqWordList){
			MyDoubleDictionary nPmiDictionary = new MyDoubleDictionary();

			for (String sourceWordW1 : WordAndContainingFiles.keySet()){
				boolean sourceContainsUseCaseWord = WordAndContainingFiles.containsKey(reqWordW2);
				
				int countW1 = WordAndContainingFiles.get(sourceWordW1).size();
				//double averageCountW1Files = _wordAndContainingFiles[sourceWordW1].Select(x => _codeFilesWithContent[x].Count).Average();
				int countW2 = sourceContainsUseCaseWord ? WordAndContainingFiles.get(reqWordW2).size() : 0;
				//double averageCountW2Files = sourceContainsUseCaseWord ? _wordAndContainingFiles[reqWordW2].Select(x => _codeFilesWithContent[x].Count).Average() : 0;
				
				// if query contains source then add 1 (query contains usecase word + source word
				// if source contains query word find the intersection of files containing both words
				List result = new ArrayList();/*存储两个list的交集*/
				for (Object arr : WordAndContainingFiles.get(sourceWordW1)) {//遍历list1
					if(WordAndContainingFiles.get(reqWordW2)!=null){
						if (WordAndContainingFiles.get(reqWordW2).contains(arr)) {//如果存在这个数
							result.add(arr);//放进一个list里面，这个list就是交集
						}
					}
				}
				int tmp= result.size();
				int countW1W2 = sourceContainsUseCaseWord ? tmp : 0;
				//double averageCountW1W2Files = sourceContainsUseCaseWord ? _wordAndContainingFiles[sourceWordW1].Intersect(_wordAndContainingFiles[reqWordW2]).Select(x => _codeFilesWithContent[x].Count).Average() : 0;
				
				// d1 and d2 will never be 0, d1d2 however can be
				double nPmi;
				if (countW1W2 == 0){
					// no cooccurence
					nPmi = -1;
				}else{
					if (countW1 == countW1W2 && countW2 == countW1W2){
						nPmi = 1;
					}else{
						nPmi = (Math.log10((double)countW1 / n * countW2 / n) / Math.log10((double)countW1W2 / n) - 1) * ((double)countW1W2 / CodeFilesWithContent.size());
					}
				}
				nPmiDictionary.put(sourceWordW1, nPmi);
			}
			nPmiMatrix.put(reqWordW2, nPmiDictionary);
		}
		
		MyDoubleDictionary tssDocumentDictionary = GetTssAltered(reqText, nPmiMatrix, -1);

		WriteDocumentVectorToFileOrderedDescending(pmiOutputFolderPath + PmiFileName, tssDocumentDictionary);

		Utility.Status("Completed APm: " + reqName);
		
    }
    
    private static MyDoubleDictionary GetTssAltered(ArrayList<String> reqFileText, DocumentDictionaryAny<MyDoubleDictionary> simMatrix, double noMatch){
    	MyDoubleDictionary tssDocumentDictionary = new MyDoubleDictionary();
		HashMap<String, Double> reqTfDictionary = new HashMap<String, Double>();
		
		for(String reqWord:reqFileText){
			if (!reqTfDictionary.containsKey(reqWord)){
				reqTfDictionary.put(reqWord, (double) 0);
			}
			reqTfDictionary.put(reqWord, reqTfDictionary.get(reqWord) + 1);
		}
		
		ArrayList<String> reqWordListForTss = new ArrayList<String>();
		for(int i=0;i<reqFileText.size();i++){
			reqWordListForTss.add(reqFileText.get(i));
		}
		int totalNumberOfDocumentInSource = CodeFilesWithContent.size();
		
		for (Map.Entry<String, ArrayList<String>> sourceFileWithWords : CodeFilesWithContent.entrySet())
		{
			ArrayList<String> sourceWords = new ArrayList<String>();
			for(int i=0;i<sourceFileWithWords.getValue().size();i++){
				sourceWords.add(sourceFileWithWords.getValue().get(i));
			}
			double sumReqTimeIdf = 0.0;
			double sumReqIdf = 0.0;

			for (String reqWord : reqWordListForTss){
				double maxSim = -1;
				for (String sourceWord : sourceWords){
					double currentSim = GetSim(reqWord, sourceWord, simMatrix, noMatch);
					if (maxSim < currentSim){
						maxSim = currentSim;
					}
				}

				// if term does not occur in any source then its only in use case hence 1
				double idf = 0;
				if (WordAndContainingFiles.containsKey(reqWord)){
					idf = Math.log10((double)totalNumberOfDocumentInSource / WordAndContainingFiles.get(reqWord).size());
				}

				sumReqIdf += idf;
				sumReqTimeIdf += (maxSim * idf);
			}

			double sumSourceTimeIdf = 0.0;
			double sumSourceIdf = 0.0;

			for (String sourceWord : sourceWords){
				double maxSim = -1;
				for (String reqWord : reqWordListForTss){
					double currentSim = GetSim(reqWord, sourceWord, simMatrix, noMatch);
					if (maxSim < currentSim){
						maxSim = currentSim;
					}
				}

				// sourceWord has to be in IdfDictionary
				double idf = Math.log10((double)totalNumberOfDocumentInSource / WordAndContainingFiles.get(sourceWord).size());

				sumSourceTimeIdf += (maxSim * idf);
				sumSourceIdf += idf;
			}

			double tss = (1.0 / 2) * ((sumReqTimeIdf / sumReqIdf) + (sumSourceTimeIdf / sumSourceIdf));
			tssDocumentDictionary.put(sourceFileWithWords.getKey(), tss);
		}

		return tssDocumentDictionary;
    }
    
    private static double GetSim(String w1, String w2, DocumentDictionaryAny<MyDoubleDictionary> matrix, double noMatch){
		if (matrix.containsKey(w1) && matrix.get(w1).containsKey(w2)){
			return matrix.get(w1).get(w2);
		}

		if (matrix.containsKey(w2) && matrix.get(w2).containsKey(w1)){
			return matrix.get(w2).get(w1);
		}

		return noMatch;
	}
    /*PMI end*/
	
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
