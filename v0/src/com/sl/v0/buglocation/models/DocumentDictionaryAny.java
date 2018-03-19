package com.sl.v0.buglocation.models;

import java.util.HashMap;

public class DocumentDictionaryAny<T> extends HashMap<String, T>{

	public final void Add(String term, T value)
	{	
		if (this.containsKey(term))
		{
			this.put(term, value);/*����key������ֵ ����ֱ��put*/
		}
		else
		{
			super.put(term, value);
		}
	}
	
}
