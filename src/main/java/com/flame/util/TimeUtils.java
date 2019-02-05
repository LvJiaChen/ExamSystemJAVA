package com.flame.util;

import java.util.Date;

/**
 * 时间格式转换工具
 * @author 苗念宽
 */
public class TimeUtils {
	/**
    * @param 要转换的毫秒数
    * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
    */
   public static String formatDuring(long mss) {
       long days = mss / ( 60 * 60 * 24);
       long hours = (mss % ( 60 * 60 * 24)) / ( 60 * 60);
       long minutes = (mss % ( 24 * 60 * 60)) / (60);
       long seconds = (mss % ( 60)) ;
       String timeDuring = null;
       timeDuring = days + " 天 " + hours + " 时 " + minutes + " 分 " + seconds + " 秒 ";
//       if(days==0&&hours==0&&minutes==0){
//    	   timeDuring = seconds + " 秒 ";
//       }else if(days==0&&hours==0){
//    	   timeDuring = minutes + " 分 " + seconds + " 秒 ";
//       }else if(days==0){
//    	   timeDuring=hours + " 时 " + minutes + " 分 " + seconds + " 秒 ";
//       }
       if(days==0){
    	   timeDuring  = hours + " 时 " + minutes + " 分 " + seconds + " 秒 ";
    	   if(hours==0){
    		   timeDuring = minutes + " 分 " + seconds + " 秒 ";
    		   if(minutes==0){
    			   timeDuring = seconds + " 秒 ";
    		   }
    	   }
       }
         return timeDuring;
   }
   /**
    * @param begin 时间段的开始
    * @param end   时间段的结束
    * @return  输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
    */
   public static String formatDuring(Date begin, Date end) {
       return formatDuring(end.getTime() - begin.getTime());
   }
}
