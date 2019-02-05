package com.flame.test;

import java.math.BigDecimal;

public class test {
	public static void main(String[] args) {
		String[] scort =new String[2];
		scort[0]="7";
		scort[1]="8";
		BigDecimal bigDecimal=new BigDecimal(scort[0]);
		System.out.println(bigDecimal);
	}
}
