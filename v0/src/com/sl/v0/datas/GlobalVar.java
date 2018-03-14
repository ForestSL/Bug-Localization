package com.sl.v0.datas;

/*定义全局变量*/

public class GlobalVar {
	
	public static String codeFolderName=null;
	public static String bugReportName=null;
	
	public static String bugVersion=null;
	
	/*存储用户选中的单元格数据 对应的文件和bug定位方法 用于展示bug详细信息*/
	public static TableCell cell=new TableCell();
	
	/*存储用户选中的文件名 用于编辑器打开*/
	public static String filename=null;
	//public static String editorArea=null;
	
    /*表格测试用数据*/
    public static String columns[] = { "文件", "VSM", "LSI", "JSM", "NGD", "PMI" };/*扩展时注意排序部分*/
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
    public static Object objs[][]=null;/*获取文件名和bug数后统一更新显示数据*/

}
