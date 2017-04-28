package it.unibo.utils;

import java.util.Random;

public class LabelNameGenerator {
	
	private static String abc = "abcdefghilmnopqrstuvzABCDEFGHILMNOPQRSTUVZ0123456789";	
	private static Random random;
	
	private static int len = 10;
	
	static{
		random = new Random(System.currentTimeMillis());
	}
	
	public static String getUniqueLabel(){
		String s = "";
				
		for(int i=0; i<len; i++)
			s += abc.charAt(random.nextInt(abc.length()));
		
		return s;				
	}

}
