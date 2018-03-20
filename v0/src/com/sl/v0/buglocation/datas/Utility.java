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
			s += System.lineSeparator();/*换行*/
		}
		return s;
	}

	public static Object MyObj = new Object();
	public static void WriteErrorCommon(String location, String message)
	{
		synchronized (MyObj)
		{
			/*c#:打开一个文件，向其中追加指定的字符串，然后关闭该文件。
			 * 如果文件不存在，此方法将创建一个文件，将指定的字符串写入文件，然后关闭该文件。*/
			//File.AppendAllText(CommonErrorPathFile, location + NewLine(1) + message + NewLine(2));
			AppendtoFile(CommonErrorPathFile, location + NewLine(1) + message + NewLine(2));
		}
	}
	
    /*追加文件：使用FileWriter*/  
    public static void AppendtoFile(String fileName, String content) {  
        try {  
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /*测试用：测试AppendtoFile()函数（对应c#AppendAllText方法）*/
//    public static void main(String[] args) {  
//        String fileName = "E:\\Error.txt";  
//        String content = "new append!";   
//        AppendtoFile(fileName, content);  
//        AppendtoFile(fileName, "append end. \n");  
//    }  
}
