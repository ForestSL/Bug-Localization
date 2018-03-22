package com.sl.v0.buglocation.datas;

import java.util.ArrayList;
import java.util.List;

import com.sl.v0.buglocation.DataCreator;
import com.sl.v0.datas.GlobalVar;

public class BugModel {
	
	private List<Bug> list = new ArrayList<Bug>();
	
	public BugModel(){
		Bug tc=new Bug();
		/*读取bug文件名*/
		list=(new DataCreator()).GetBugs(Utility.DatasetFolderPath+GlobalVar.bugReportName);
	}
	
	public List<Bug> elements(){
		return list;
	}
}
