package com.sl.v0.buglocation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.sl.v0.datas.GlobalVar;

public class DataCreator {
	
    /*������*/
	public static void main(String[] args) {  
		 GetFiles("E:\\GithubCode\\cxf.git\\cxf-2.7.11");
		 GetBugs("E:\\BugReport\\cxf2.7.11.xml");
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
	public static List<File> GetFiles(String directory)
	{	
		File dir = new File(directory);
        FilenameFilter fileter = new FileterByJava(".java");/*����.java�ļ�*/
        List<File> fileList = new ArrayList<File>();
        getFileList(dir,fileter,fileList);
        File desFile = new File(dir, "FileList.txt");/*��ĿͬĿ¼��*/
        write2File(fileList, desFile);
        
        /*TODO:��ֵtable���ļ���(��ѡ���Ƿ��ڴ˴���ֵ ��Ϊ����ֵ���ļ��б� �ɺ���ʹ��)*/
        GlobalVar.objs=new Object[fileList.size()][];
        for(int i=0;i<fileList.size();i++){
        	GlobalVar.objs[i]=new Object[6];
        	String[] filePath=fileList.get(i).toString().split("\\\\");
        	String fileName=filePath[filePath.length-1];
        	GlobalVar.objs[i][0]=fileName;
        	for(int j=1;j<6;j++)
        		GlobalVar.objs[i][j]=0;/*��ʼֵ*/
        }
        
        /*�����ļ��б�����·����*/
		return fileList;
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
