package com.sl.v0.buglocation.datas;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public final class Utility{
	
	public static final String ReportFolderPath = "E:\\BugLocation\\Report\\";
	public static final String DatasetFolderPath = "E:\\BugLocation\\Source\\";
	public static final String MoreBugDatasetRelativeFolderPath = "moreBugs\\";
	public static final String CommonErrorPathFile = "E:\\BugLocation\\Error.txt";

	public static final ArrayList<Integer> LsiKs = new ArrayList<Integer>(Arrays.asList(new Integer[] {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90}));

	public static void Status(String text)
	{
		System.out.println(text);
	}

	public static String NewLine(int count)
	{
		String s = "";
		for (int i = 0; i < count; i++)
		{
			s += System.lineSeparator();/*����*/
		}
		return s;
	}

	public static Object MyObj = new Object();
	public static void WriteErrorCommon(String location, String message)
	{
		synchronized (MyObj)
		{
			/*c#:��һ���ļ���������׷��ָ�����ַ�����Ȼ��رո��ļ���
			 * ����ļ������ڣ��˷���������һ���ļ�����ָ�����ַ���д���ļ���Ȼ��رո��ļ���*/
			//File.AppendAllText(CommonErrorPathFile, location + NewLine(1) + message + NewLine(2));
			AppendtoFile(CommonErrorPathFile, location + NewLine(1) + message + NewLine(2));
		}
	}
	
    /*׷���ļ���ʹ��FileWriter*/  
    public static void AppendtoFile(String fileName, String content) {  
        try {  
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /*�����ã�����AppendtoFile()��������Ӧc#AppendAllText������*/
//    public static void main(String[] args) {  
//        String fileName = "E:\\Error.txt";  
//        String content = "new append!";   
//        AppendtoFile(fileName, content);  
//        AppendtoFile(fileName, "append end. \n");  
//    }  
}
