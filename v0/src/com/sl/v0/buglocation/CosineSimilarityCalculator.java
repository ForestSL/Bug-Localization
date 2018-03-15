package com.sl.v0.buglocation;

import com.sl.v0.buglocation.models.MyDoubleDictionary;

public class CosineSimilarityCalculator{
	
	private MyDoubleDictionary _vector1;

	public CosineSimilarityCalculator(MyDoubleDictionary vector1){
		_vector1 = vector1;
	}

//	public final double GetSimilarity(MyDoubleDictionary vector2){
//		double length1 = GetLength(_vector1);
//		double length2 = GetLength(vector2);
//
//		double dotProduct = _vector1.Where(wordWithCount -> vector2.containsKey(wordWithCount.Key)).Sum(wordWithCount -> (wordWithCount.Value * vector2.get(wordWithCount.Key)));
//
//		return vector2.isEmpty() ? 0 : dotProduct / (length1 * length2);
//	}
//
//	public final String GetSimilarityText(MyDoubleDictionary vector2){
//		double length1 = GetLength(_vector1);
//		double length2 = GetLength(vector2);
//
//		var dotProductObj = _vector1.Where(wordWithCount -> vector2.containsKey(wordWithCount.Key)).Select(wordWithCount -> new {Word = wordWithCount.Key, Value1 = wordWithCount.Value, Value2 = vector2.get(wordWithCount.Key)}).Select(x -> String.format("%1$s %2$s, %3$s", x.Word, x.Value1.toString("##.000"), x.Value2.toString("##.000")));
//
//		String dotProductString = tangible.DotNetToJavaStringHelper.join(System.lineSeparator(), dotProductObj);
//		return dotProductString;
//	}
//
//	public static double GetLength(MyDoubleDictionary vector){
//		double length = Math.sqrt(vector.Sum(x -> Math.pow(x.Value, 2)));
//		return length;
//	}
}
