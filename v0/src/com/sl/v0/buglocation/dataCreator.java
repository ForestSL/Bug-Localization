package com.sl.v0.buglocation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sl.v0.buglocation.datas.Bug;
import com.sl.v0.buglocation.datas.Utility;
import com.sl.v0.buglocation.models.MyListTDictionary;
import com.sl.v0.datas.GlobalVar;

public class DataCreator {
	
	private static final String DataSetFolderName = "GithubCode\\cxf.git\\cxf-2.7.11\\";

	private static final String OutputCorpusFolderName = "Corpus\\";
	private static final String OutputBugQueryFileName = "BugQuery.txt";
	private static final String OutputRelListFileName = "RelList.txt";
	private static final String OutputFileListFileName = "FileList.txt";
	private static final String QuerySourceFileName = "BugReport\\cxf2.7.11.xml";
	
	private static final String ReportFolderPath = Utility.ReportFolderPath + DataSetFolderName;
	private static final String FileFolderName=Utility.DatasetFolderPath+DataSetFolderName;
	private static final String BugFileName=Utility.DatasetFolderPath+QuerySourceFileName;
	private static final String CorpusFolderPath = ReportFolderPath + OutputCorpusFolderName;
	
    /*������*/
	public static void main(String[] args) {  
		 execute();
	} 
	
	public static void execute(){
		
		MyListTDictionary<File> javaFiles = new MyListTDictionary<File>();
		GetFiles(FileFolderName,javaFiles);
		List<Bug> allBugs=GetBugs(BugFileName);
		
		/* Create relevance list*/
		HashMap<File, Integer> allIndexedFiles = new HashMap<File, Integer>();
		int counter = 1;
		Iterator it = javaFiles.keySet().iterator();   
		while(it.hasNext()){
			List<File> f=javaFiles.get(it.next());
			//System.out.println(javaFiles.get(it.next()).get(0));
			for(int i=0;i<f.size();i++)
				allIndexedFiles.put(f.get(i), counter++);
		}
		
		/* Create a directory in eclipse saying Corpus*/
		if ((new File(ReportFolderPath)).isDirectory()){
			(new File(ReportFolderPath)).delete();
		}
		(new File(ReportFolderPath)).mkdirs();
		(new File(CorpusFolderPath)).mkdirs();
		
		int corpusCounter = 1;
		int totalCorpus = allIndexedFiles.size();
		for (Map.Entry<File, Integer> fileWithIndex : allIndexedFiles.entrySet())
		{
			(new BaseFunction()).WriteAllLines(CorpusFolderPath + fileWithIndex.getValue() + ".txt", 
					(new BaseFunction()).TextWithFilter((new BaseFunction()).ReadAllText(fileWithIndex.getKey().toString())));
			//System.out.println("Writing corpus " + corpusCounter + " of " + totalCorpus);
			++corpusCounter;
		}
		
		ArrayList<String> content=new ArrayList<String>();
		Iterator it2 = allIndexedFiles.keySet().iterator();   
		while(it2.hasNext()){
			Object key2=it2.next();
			content.add(allIndexedFiles.get(key2)+" "+key2);
		}
		(new BaseFunction()).WriteAllLines(ReportFolderPath + OutputFileListFileName, content);
		
		/* Create stuff*/ 
		int bugCounter = 1;
		int totalBug = allBugs.size();
		for (Bug bug : allBugs){
			String bugFolderPath = ReportFolderPath + bug.getBugId() + "\\";
			(new File(bugFolderPath)).mkdirs();

			(new BaseFunction()).WriteAllLines(bugFolderPath + OutputBugQueryFileName, (new BaseFunction()).TextWithFilter(bug.getSummary() + " " + bug.getDescription()));

			//ArrayList<String> relevanceList = bug.getFixedFiles().SelectMany(x -> relevanceMappingDictionary.get(x).Select(y -> allIndexedFiles.get(y).toString())).Distinct().ToList();

			//(new BaseFunction()).WriteAllLines(bugFolderPath + OutputRelListFileName, relevanceList);

			//System.out.println("Done writing bug " + bugCounter + " of " + totalBug);
			++bugCounter;
		}
		
		
	}
	
	/*��ȡxml�ļ���bug��Ϣ*/
	public static List<Bug> GetBugs(String filePath){
		ArrayList<Bug> allbugs=new ArrayList<Bug>();
		/*����bug����xml�ļ�*/
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try{
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	Document document = db.parse(filePath);
	    	NodeList buglist = document.getElementsByTagName("item");
	    	int bugsCount = buglist.getLength();
			int currentBugsCount = 0;
	    	for(int i = 0; i < buglist.getLength(); i++){	    		
	    		++currentBugsCount;
				//Utility.Status("Reading bug (" + currentBugsCount + " of " + bugsCount + ")");
				/*�½�bug������bug�б�*/
				Bug bug = new Bug();	    		
	    		Element ele = (Element) buglist.item(i);
	    		NodeList childNodes= ele.getChildNodes();
	    		for(int j = 0; j < childNodes.getLength(); j++){
	    			Node n = childNodes.item(j);
	    			if(n.getNodeName() == "key"){
	    				//System.out.println(n.getNodeName() + ":" + n.getTextContent());
	    				bug.setBugId(n.getTextContent());
	    			}
	    			if(n.getNodeName() == "summary"){
	    				//System.out.println(n.getNodeName() + ":" + n.getTextContent());
	    				bug.setSummary(n.getTextContent());
	    			}
	    			if(n.getNodeName() == "description"){
	    				//System.out.println(n.getNodeName() + ":" + n.getTextContent());
	    				bug.setDescription(n.getTextContent());
	    			}
	    			/*TODO:c#��Ŀ�л�ȡfixedFiles��ǩ����δ��⣩*/
	    		}
	    		//System.out.println("---------------------------------");
	    		
	    		allbugs.add(bug);
	    	}
	    }catch (ParserConfigurationException e){
	    	e.printStackTrace();
	    }catch (IOException e){
	    	e.printStackTrace();
	    }catch (SAXException e){
	    	e.printStackTrace();
	    }

		return allbugs;
	}
	
	
	/*��ȡָ����ĿĿ¼�µ�����java�ļ�*/
	public static void GetFiles(String directory,MyListTDictionary<File> javaFiles)
	{	
		File dir = new File(directory);
        FilenameFilter fileter = new FileterByJava(".java");/*����.java�ļ�*/
        List<File> fileList = new ArrayList<File>();
        getFileList(dir,fileter,fileList);
//        File f=new File(ReportFolderPath);
//      	f.mkdirs();/*�ȴ����ļ���*/
//        File desFile = new File(ReportFolderPath,OutputFileListFileName);
//        write2File(fileList, desFile);
        
        /*�����ļ���ֵ�ԣ�����·����*/
        for(int i=0;i<fileList.size();i++){
        	String[] filePath=fileList.get(i).toString().split("\\\\");
        	String fileName=filePath[filePath.length-1];
        	javaFiles.Add(fileName, fileList.get(i));
        }

	}
	
    /**
     * @param dir ��Ҫ������Ŀ¼
     * @param filter ���������������ļ�
     * @param fileList ��ŷ�������������
     */
    public static void getFileList(File dir,FilenameFilter filter,List<File> fileList){
        if(dir.exists()){
            File[] files = dir.listFiles();/*�ҵ�Ŀ¼����������ļ�*/
            for(File file:files){
                /*�ݹ�*/
                if(file.isDirectory()){
                    getFileList(file,filter,fileList);
                }else{
                    /*�Ա������ļ����й��ˣ����������ķ���List������*/
                    if(filter.accept(dir, file.getName())){
                        fileList.add(file);
                    }
                }
            }
            
        }
    }
    
    /**
     * �������е��ļ�������д�뵽Ŀ���ļ���
     * @param list  ��������������ļ��ļ���
     * @param desFile Ҫд���Ŀ���ļ�
     */
    public static void write2File(List<File> fileList,File desFile){
        BufferedWriter bw = null;
        try {
            /*ʹ���ַ���д�뵽Ŀ���ļ���*/
            bw = new BufferedWriter(new FileWriter(desFile));
            /*����List����*/
            for(File file:fileList){
                bw.write(file.getAbsolutePath());/*д��Ŀ���ļ�����·��*/
                bw.newLine();
                bw.flush();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(bw != null){
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
}
