package com.sl.v0.datas;

import java.io.File;
import java.util.HashMap;

/*����ȫ�ֱ���*/

public class GlobalVar {
	
	/*Դ�����bug��������·��*/
	public static String codeFolderName="GithubCode\\cxf.git\\cxf-2.7.11\\";
	public static String bugReportName="BugReport\\cxf2.7.11.xml";
	
	/*bug��λ������ѡ ��Ӧ������VSM LSI JSM NGD PMI*/
	public static boolean[] isMethod={true,true,true,true,true};
	
	public static String bugVersion=null;
	
	/*�洢�û�ѡ�еĵ�Ԫ������ ��Ӧ���ļ���bug��λ���� ����չʾbug��ϸ��Ϣ*/
	public static TableCell cell=new TableCell();
	
	/*�洢�û�ѡ�е��ļ��� ���ڱ༭����*/
	public static String filename=null;
	//public static String editorArea=null;
	
    /*������������*/
    public static String columns[] = { "�ļ�", "VSM", "LSI", "JSM", "NGD", "PMI" };/*��չʱע�����򲿷�*/
//    public static Object objs[][] = {
//        	{
//        		"a.java", "60", "1", "103", "25", "55"
//        	}, {
//        		"b.java", "30", "2", "102", "35", "56"
//        	}, {
//        		"c.java", "50", "4", "101", "14", "67"
//        	}, {
//        		"d.java", "10", "3", "100", "34", "66"
//        	}, {
//        		"e.java", "200", "6", "102", "35", "56"
//        	}, {
//        		"f.java", "40", "10", "101", "14", "67"
//        	}, {
//        		"g.java", "100", "5", "100", "34", "66"
//        	} 
//        };
    public static Object objs[][]=null;/*��ȡ�ļ�����bug����ͳһ������ʾ����*/

}
