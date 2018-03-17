package com.sl.v0.buglocation.models;

import java.util.HashMap;

public class MyDoubleDictionary extends HashMap<String, Double>{
	public final void Add(String term)
	{
		if (this.containsKey(term))
		{
			this.put(term, (Double)(this.get(term) + 1));
		}
		else
		{
			this.put(term, (double) 1);
		}
	}
}