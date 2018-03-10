package com.sl.v0.datas;

/*视图消息传递*/

import java.util.ArrayList;

public class TableCellModel {

	private ArrayList<TableCell> list = new ArrayList<TableCell>();
	
	public TableCellModel(){
		TableCell tc=new TableCell();
		tc.setCount(GlobalVar.cell.getCount());
		tc.setFile(GlobalVar.cell.getFile());
		tc.setMethod(GlobalVar.cell.getMethod());
		list.add(tc);
	}
	
	public ArrayList<TableCell> elements(){
		return list;
	}
	
}
