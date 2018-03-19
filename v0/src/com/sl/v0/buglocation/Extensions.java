package com.sl.v0.buglocation;

/*JSM”√*/

import java.util.ArrayList;
import java.util.List;

public final class Extensions{
	
	public static double[] JensenSum(double[] vector1, double[] vector2){		
		if (vector1.length != vector2.length){
			throw new RuntimeException("Length " + vector1.length + " does not match vector 2 length: " + vector2.length);
		}

		double[] result = new double[vector1.length];
		for (int i = 0; i < vector1.length; i++){
			result[i] = (vector1[i] + vector2[i]) / 2;
		}

//		for(int i=0;i<result.length;i++){
//			if(result[i]!=0)
//				System.out.println(result[i]);
//		}
		return result;
	}

	public static double JensenEntropy(double[] vector){
		List<Double> d=new ArrayList<Double>();
		for(int i=0;i<vector.length;i++){
			if(vector[i]!=0)
				d.add(vector[i]*Math.log(vector[i]));
			else
				d.add((double) 0);
		}
		double sum=0;
		for(int i=0;i<d.size();i++)
			sum+=d.get(i);
		//System.out.println(sum);
		//System.out.println(-1.0*sum);
		return -1.0 * sum;
	}
}
