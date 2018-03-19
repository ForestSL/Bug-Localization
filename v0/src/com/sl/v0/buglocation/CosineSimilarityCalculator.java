package com.sl.v0.buglocation;

/*VSM用*/

import java.util.Iterator;

import com.sl.v0.buglocation.models.MyDoubleDictionary;

public class CosineSimilarityCalculator{
	
	private MyDoubleDictionary _vector1;

	public CosineSimilarityCalculator(MyDoubleDictionary vector1){
		_vector1 = vector1;
	}

	public final double GetSimilarity(MyDoubleDictionary vector2){
		double length1 = GetLength(_vector1);
		double length2 = GetLength(vector2);

		double sum=0;
		Iterator it=_vector1.keySet().iterator();
        while(it.hasNext()){
        	String key=(String) it.next();
        	if(vector2.containsKey(key)){
        		sum+=(_vector1.get(key)*vector2.get(key));
        	}
        }
		double dotProduct = sum;

		return vector2.isEmpty() ? 0 : dotProduct / (length1 * length2);
	}

	public static double GetLength(MyDoubleDictionary vector){
		/*求vector值Math.pow(x.Value, 2)的和*/
		double sum=0;
		Iterator it=vector.keySet().iterator();
        while(it.hasNext()){
        	sum+=(Math.pow(vector.get(it.next()),2));
        }
		double length = Math.sqrt(sum);
		return length;
	}
}
