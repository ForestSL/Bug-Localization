package com.sl.v0.datas;

/*存储table中单元格对应的文件 方法 bug数*/

public class TableCell {
	static String count;
	static String method;
	static String file;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		TableCell.count = count;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		TableCell.method = method;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		TableCell.file = file;
	}

}
