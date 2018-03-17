package com.sl.v0.buglocation.models;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListTDictionary<T> extends HashMap<String, ArrayList<T>>{
	
	public final void Add(String term, T value)
	{
		if (!this.containsKey(term))
		{
			this.put(term, new ArrayList<T>());
		}

		if (!this.get(term).contains(value))
		{
			this.get(term).add(value);
		}
	}
}
