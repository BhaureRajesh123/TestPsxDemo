package com.posidex.test;

public class Demo {
	public static void main(String[] args) {
		String str = "FCCI_BH";
		String[] strArr = str.split("\\,");
		
		for(int i=0;i<strArr.length;i++) {
			System.out.println("Name : "+strArr[i]);   
		}
	}

}
