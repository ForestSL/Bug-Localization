package com.sl.v0.datas;

/*����ȫ�ֱ���*/

public class GlobalVar {
	
	/*�洢�û�ѡ�еĵ�Ԫ������ ��Ӧ���ļ���bug��λ���� ����չʾbug��ϸ��Ϣ*/
	public static TableCell cell=new TableCell();
	
	/*�洢�û�ѡ�е��ļ��� ���ڱ༭����*/
	public static String filename=null;
	//public static String editorArea=null;
	
    /*������������*/
    public static String columns[] = { "�ļ�", "VSM", "LSI", "JSM", "NGD", "PMI" };/*��չʱע�����򲿷�*/
    public static Object objs[][] = {
        	{
        		"a.java", "60", "1", "103", "25", "55"
        	}, {
        		"b.java", "30", "2", "102", "35", "56"
        	}, {
        		"c.java", "50", "4", "101", "14", "67"
        	}, {
        		"d.java", "10", "3", "100", "34", "66"
        	}, {
        		"e.java", "200", "6", "102", "35", "56"
        	}, {
        		"f.java", "40", "10", "101", "14", "67"
        	}, {
        		"g.java", "100", "5", "100", "34", "66"
        	} 
        };

}
