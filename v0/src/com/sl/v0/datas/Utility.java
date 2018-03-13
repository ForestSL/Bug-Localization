package com.sl.v0.datas;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public final class Utility
{
	public static final String ReportFolderPath = "E:\\Research-Dataset\\Bug\\Report\\";
	public static final String DatasetFolderPath = "E:\\Research-Dataset\\Bug\\Source\\";
	public static final String MoreBugDatasetRelativeFolderPath = "moreBugs\\";
	public static final String CommonErrorPathFile = "E:\\Research-Dataset\\Bug\\Error.txt";

	public static final int ParallelThreadCount = 1;

	public static final boolean CleanPrevious = false;
	public static final boolean RunVsm = false;
	public static final boolean RunLsi = false;
	public static final boolean RunNgd = false;
	public static final boolean RunSim = true;
	public static final boolean RunJen = false;
	public static final boolean RunAPm = false;

	public static final ArrayList<Integer> LsiKs = new ArrayList<Integer>(Arrays.asList(new Integer[] {50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900}));

	public static void Status(String text)
	{
		System.out.println(text);
	}

	public static String NewLine(int count)
	{
		String s = "";
		for (int i = 0; i < count; i++)
		{
			s += System.lineSeparator();
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
			/*TODO:java��ʵ����������*/
		}
	}
}
