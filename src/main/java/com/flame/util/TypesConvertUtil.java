package com.flame.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据类型与bytes 转换
 * @author Think
 *
 */
public class TypesConvertUtil {
	
	/** 
     * byte的数组转换成Date
     *  
     * @param buf 
     */  
    public static Date bytesToDate(byte[] buf,String format) {
    	if(buf==null||buf.length==0)
    		return null;
    	if(format==null)
    		format = "yyyy-MM-dd HH:mm:ss";
    	String strDate = new String(buf);
    	DateFormat dateFormat=new SimpleDateFormat(format);//类型自己按你的数据定
    	Date dateResult =null;
		try {
			dateResult = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return dateResult;
    }
	/** 
     * byte的数组转换成Boolean
     *  
     * @param buf 
     */  
    public static Boolean bytesToBoolean(byte[] buf) {
    	if(buf==null||buf.length==0)
    		return null;
    	for(int i=0;i<buf.length;i++){
			if(buf[i]>0)
				return true;
		}
    	return false;
    }
    
    /** 
     * byte的数组转换成int 
     *  
     * @param buf 
     */  
    public static Integer bytesToInteger(byte[] buf) {
    	if(buf==null||buf.length==0)
    		return null;
    	buf =bytesCut(buf,4);
    	return ((((int) buf[0] & 0xff) << 24) 
                   | (((int) buf[1] & 0xff) << 16) 
                   | (((int) buf[2] & 0xff) << 8) | (((int) buf[3] & 0xff) << 0)); 
    }
    
	/** 
     * byte的数组转换成long 
     *  
     * @param buf 
     */  
    public static Long bytesToLong(byte[] buf) {
    	if(buf==null||buf.length==0)
    		return null;
    	if(buf.length<8)
    		buf =bytesCut(buf,8);
    	else if(buf.length>8){
    		if(buf.length>8){
				byte[] t = new byte[8];
				System.arraycopy(buf, buf.length-8, t, 0, 8);
				buf = t;
			}
    	}
    	return ((((long) buf[0] & 0xff) << 56) 
                   | (((long) buf[1] & 0xff) << 48) 
                   | (((long) buf[2] & 0xff) << 40) 
                   | (((long) buf[3] & 0xff) << 32) 
                   | (((long) buf[4] & 0xff) << 24) 
                   | (((long) buf[5] & 0xff) << 16) 
                   | (((long) buf[6] & 0xff) << 8) | (((long) buf[7] & 0xff) << 0)); 
    }
    /**
     *  byte的数组转换成Double
     * @param arr
     * @return
     */
    public static Double bytesToDouble(byte[] arr) {  
    	if(arr==null||arr.length==0)
    		return null;
        long value = 0;  
        if(arr.length<8)
        	arr =bytesCut(arr,8);
    	else if(arr.length>8){
    		if(arr.length>8){
				byte[] t = new byte[8];
				System.arraycopy(arr, arr.length-8, t, 0, 8);
				arr = t;
			}
    	}
        for (int i = 0; i < 8; i++) {  
            value |= ((long) (arr[i] & 0xff)) << (8 * i);  
        }  
        return Double.longBitsToDouble(value);  
    }  
    
    
  /**
   * date 转化�? byte 数组  
   * @param date
   * @param format
   * @param length  返回数组的位�? 不足 前面�?0
   * @return
   */
    public static byte[] dateToBytes(Date date,String format,int length) {
    	if(date==null)
    		return null;
    	DateFormat dateFormat=new SimpleDateFormat(format);//类型自己按你的数据定
    	String dateStr = dateFormat.format(date);
		
    	return bytesCut(dateStr.getBytes(), length);
    }
	/** 
     * Boolean转换成byte的数�?
     *  
     * @param buf 
     */  
    public static byte[] booleanToBytes(boolean b,int length) {
    	byte[] temp = new byte[1];
    	if(b)
    		temp[0] = (byte)1;
    	else
    		temp[0] = (byte)0;
    	return bytesCut(temp, length);
    }
	/** 
     * long转换�? byte的数�?
     *  
     * @param buf 
     */  
    public static byte[] longToBytes(long s,int length) {  
        byte[] temp = new byte[8];  
        for (int i = 0; i < 8; i++) {  
            int offset = (temp.length - 1 - i) * 8;  
            temp[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return bytesCut(temp, length);
    } 
    /**
     *  byte的数组转换成Double
     * @param arr
     * @return
     */
    public static byte[] doubleToBytes(double d,int length) {  
    	 long value = Double.doubleToRawLongBits(d);  
         byte[] byteRet = new byte[8];  
         for (int i = 0; i < 8; i++) {  
             byteRet[i] = (byte) ((value >> 8 * i) & 0xff);  
         }  
         return bytesCut(byteRet,length);  
    } 
    
    
  
    
    
    /**
     * 字节裁剪，位数不�? 前面�? 0，当位数过多，从�?后取�?
     * byte数组 
     * @return
     */
    public static byte[] bytesCut(byte[] buff,int length){
    	byte[] temp = new byte[length];
    	
    	if(buff.length<length){
    		int diffVal= length-buff.length;
    		for(int i=0;i<diffVal;i++)
    			temp[i]=0;
    		for(int i=diffVal,j=0;i<length;i++,j++)
    			temp[i]=buff[j];
    	}else if(buff.length==length){
    		return buff;
    	}else{
    		int diffVal= buff.length-length;
    		for(int i=0;i<length;i++)
    			//temp[i]=buff[(buff.length-1)-i];
    			temp[i]=buff[diffVal+i];
    	}
    	return temp;
    }
    
    public static byte[] bytesCutReverse(byte[] buff,int length){
    	byte[] temp = new byte[length];
    	
    	if(buff.length<length){
    		for(int i=0,j=0;i<buff.length;i++,j++)
    			temp[i]=buff[j];
    	}else if(buff.length==length){
    		return buff;
    	}else{
    		for(int i=0;i<length;i++)
    			temp[i]=buff[i];
    	}
    	return temp;
    }
    
    /**
     * 字符串转换为Ascii
     * @param value
     * @return
     */
	public static String stringToAscii(String value)  
	{  
    	StringBuffer sbu = new StringBuffer();  
    	char[] chars = value.toCharArray();   
        for (int i = 0; i < chars.length; i++) {  
            if(i != chars.length - 1)  
            {  
                sbu.append((int)chars[i]).append(",");  
            }  
            else {  
                sbu.append((int)chars[i]);  
            }  
        }  
        return sbu.toString();  

	}
    
	 public static void main(String[] args) {
		   
		 String s="30353030";
		 
		   byte[] b= ByteUtil.hexStringToByteArray(s);//字符串转二进制
		   System.out.println(bytesToAscii(b,false));//二进制转Ascii
		   System.out.println(bytesToInteger(ByteUtil.hexStringToByteArray(bytesToAscii(b,false))));
		   
	}
    /**
     * byte[]转换为Ascii
     * @param value
     * @return
     */
	public static String bytesToAscii(byte[] byteValue,boolean isFront){  
		String nRcvString;
		StringBuffer tStringBuf=new StringBuffer ();
		char[]tChars=new char[byteValue.length];
			     
		for(int i=0;i<byteValue.length;i++)
			tChars[i]=(char)byteValue[i];
	     
		tStringBuf.append(tChars);     
		nRcvString=tStringBuf.toString();   
	     
        int len = nRcvString.length();
        int st = 0;
        char[] val = nRcvString.toCharArray();    /* avoid getfield opcode */

        if (isFront) {
        	while ((st < len) && (val[st] <= ' ')) {
        		st++;
        	}
		}else {
	        while ((st < len) && (val[len - 1] <= ' ')) {
	            len--;
	        }
		}
       return ((st > 0) || (len < nRcvString.length())) ? nRcvString.substring(st, len) : nRcvString;

	}
	
	
	
	
	public static int byteArrayToInt(byte[] input) {  
		byte[] b = new byte[4];
		int o =0;
		for (int i = 0; i < 4; i++) {
			if (i<4-input.length) {
				b[i] = (byte) 0x00;
			}else {
				b[i] = input[o];
				o++;
			}
		}
		return   (b[3] & 0xFF |  
	            (b[2] & 0xFF) << 8 |  
	            (b[1] & 0xFF) << 16 |  
	            (b[0] & 0xFF) << 24);  
	}

}
