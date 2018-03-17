package com.sl.v0.buglocation.models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.sl.v0.buglocation.BaseFunction;
import com.sl.v0.datas.GlobalVar;

public class MyResultDictionary extends HashMap<String, ArrayList<String>>{

	/*term：索引号文件全路径*/
	public final void Add(String term, String value,HashMap<String,String> fileList){
		String[] tmp=term.split("\\\\");
		String indexName=tmp[tmp.length-1];
		//System.out.println(indexName);
		String[] in2=indexName.split("\\.");
		String index=null;
			index=in2[0];/*获取索引号*/
			/*根据FileList.txt获取文件全名*/
			String fileName=null;
			Iterator it = fileList.keySet().iterator();   
			fileName=fileList.get(index);
			//System.out.println(fileName);
		
			if (!this.containsKey(fileName))
			{
				this.put(fileName, new ArrayList<String>());
			}

			if (!this.get(fileName).contains(value))
			{
				this.get(fileName).add(value);
			}
		//}
	}
	
}
