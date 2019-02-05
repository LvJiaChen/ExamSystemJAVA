package com.flame.util;

import java.nio.ByteBuffer;
/*
 * 字节码工具类
 */
public class ByteUtil {
	
	
	
	/**
	 * 二进制转十六进制字符
	 * @param src
	 * @return
	 */
	public static String byteArrayToHexString(byte[] src,boolean isAppendSpace){
		StringBuilder stringBuilder = new StringBuilder();
		if(src==null||src.length==0)
			return null;
		for(int i =0;i<src.length;i++){
			if(isAppendSpace&&i>0)
				stringBuilder.append(" ");
			stringBuilder.append(byteToHexString(src[i]));
		}
		
		return stringBuilder.toString();
	}
	/**
	 * byte 转十六进�?
	 * @param src
	 * @return
	 */
	public static String byteToHexString(byte src){
		
		StringBuilder stringBuilder = new StringBuilder();
		int v = src & 0xFF;
		String hv =Integer.toHexString(v);
		if(hv.length()==1)
			stringBuilder.append(0);
		stringBuilder.append(hv);
		return stringBuilder.toString().toUpperCase();
	}
	
    
	
	/**
	 * 十六进制字符转二进制数组
	 * @param src
	 * @return
	 */
	public static byte[] hexStringToByteArray(String hexString){
		   if (hexString==null || hexString.length()==0)
		        throw new IllegalArgumentException("this hexString must not be empty");

		    hexString = hexString.toLowerCase();
		    final byte[] byteArray = new byte[hexString.length() / 2];
		    int k = 0;
		    for (int i = 0; i < byteArray.length; i++) {//因为�?16进制，最多只会占�?4位，转换成字节需要两�?16进制的字符，高位在先
		        byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
		        byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
		        byteArray[i] = (byte) (high << 4 | low);
		        k += 2;
		    }
		    return byteArray;
	}
	
	/**
	 * 二进制数组转二进制字符串
	 * @param b
	 * @return
	 */
	public static String bytesToString(byte[] b){
		StringBuilder sb = new StringBuilder();
		for(byte temp: b)
			sb.append(byteToString(temp));
		return sb.toString();
	}
	
	
	
	/**
	 * 二进制转二进制字符串
	 * @param b
	 * @return
	 */
	public static String byteToString(byte b){
		StringBuilder sb = new StringBuilder();
		String str =Integer.toBinaryString(b & 0xFF);
		for(int i=str.length();i<8;i++)
			sb.append(0);
		sb.append(str);
		return sb.toString();
	}
	/**
	 * 二进制字符串转二进制
	 * @param s
	 * @return
	 */
	public static byte stringToByte(String bString)
	{
		byte result=0;
		for(int i=bString.length()-1,j=0;i>=0;i--,j++){
		result+=(Byte.parseByte(bString.charAt(i)+"")*Math.pow(2, j));
		}
		return result;
	}
	
	/**
	 * 二进制字符串转二进制
	 * @param s
	 * @return
	 * @throws Exception 
	 */
	public static byte[] stringToBytes(String s) throws Exception
	{
		int byteLength = s.length()/8;
		if(s.length()%8!=0)
			throw new Exception(s+"不符合转换要�?...");
		byte[] res = new byte[byteLength];
		for(int i=0;i<byteLength;i++)
			res[i]=stringToByte(s.substring(i*8, i*8+8));
		
		return res;
	}
	/**
	 * 高低位转�?   高字节在�? 低字节在�?
	 * 待调换高四位和第四位的数�?
	 */
	public static byte hightLowConversion(byte src){
//		System.out.println(byteToString(src));
		 // 高四�?
        byte high4 = (byte) (src & 240) ; //240的二进制 11110000
        // 低四�?
        byte low4 = (byte) (src & 15); // 15的二进制形式 00001111
      
//        System.out.println(byteToString(high4));
//        System.out.println(byteToString(low4));
         
        byte res = (byte) ( high4+low4 );
         
//        System.out.println(byteToString(res));// 84的二进制形式 01010100
        
        return res;
	}
	/**
	 * 高低位转�?   高字节在�? 低字节在�?
	 * 待调换高8位和�?8位的数据
	 */
	public static byte[] hightLowConversion(short src){
		byte[] temp =ByteBuffer.allocate(2).putShort(src).array();
//		System.out.print(byteToString(temp[0]));
//		System.out.print(byteToString(temp[1]));
//		System.out.println();
		if(temp[0]<temp[1]){
			byte tem =temp[0];
			temp[0]=temp[1];
			temp[1] =tem;
		}
//		System.out.print(byteToString(temp[0]));
//		System.out.print(byteToString(temp[1]));
		return temp;
	}
	
	/**
	 * 高低位转�?   高字节在�? 低字节在�?
	 * 待调换高8位和�?8位的数据
	 */
	public static byte[] lowHighConversion(short src){
		byte[] temp =ByteBuffer.allocate(2).putShort(src).array();
	
		byte tem =temp[0];
		temp[0]=temp[1];
		temp[1] =tem;
		
		return temp;
	}
	/**
	 * 高低位转�?   高字节在�? 低字节在�?
	 * 待调换高16位和�?16位的数据
	 */
	public static byte[] hightLowConversion(int src){
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		ByteBuffer resBuffer = ByteBuffer.allocate(4);
		byte[] temp = byteBuffer.putInt(src).array();

		
		short s1= getShort(temp,0);
		short s2= getShort(temp,2);
		
		resBuffer.putShort(s2);
		resBuffer.putShort(s1);
		
		
		return resBuffer.array();
	}
	/**
	 * char转换成byte
	 * @param c
	 * @return
	 */
	private static byte charToByte(char c){
		return (byte) "0123456789".indexOf(c);
	}
	
	private static short getShort(byte[] b, int index) {  
        return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));  
    }
	
	
	private static byte[] shortToBytes(Short src){
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);
		return byteBuffer.putShort(src).array();

	}
	public static void main(String[] args) {
		byte[] a= new byte[8];
		System.out.println(bytesToString(a));
	}
}
