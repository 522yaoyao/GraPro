package ly.tool;

import java.math.BigDecimal;

/**
 * 由于Java的简单运算类型不能精确的对浮点数进行计算，此工具类提供精确的浮点数计算，包括加减乘除和四舍五入
 */
public class DecimalCalculate {
	
	//默认的除法精度
	private static final int  DEF_DIV_SCALE=10;
    
	//将构造方法设置成私有的，可以很好的保证此类不会在其他类中被实例化
	private DecimalCalculate(){
		
	}
	
	/**
	 * 精确的加法计算
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1,double v2){   
        BigDecimal b1 = new BigDecimal(Double.toString(v1));   
        BigDecimal b2 = new BigDecimal(Double.toString(v2));   
        return b1.add(b2).doubleValue();   
    }   
	
	/**
	 * 精确的减法计算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	 public static double sub(double v1,double v2){   
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	        return b1.subtract(b2).doubleValue(); 
	 }
	 
	 /**
	  * 精确的乘法计算
	  * @param v1 被乘数
	  * @param v2 乘数
	  * @return 两个数的积
	  */
	 public static double mul(double v1,double v2){   
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	        return b1.multiply(b2).doubleValue();   
	    }
	 
	 /**
	  * 精确的除法运算
	  * @param v1 被除数
	  * @param v2 除数
	  * @return两个数的商
	  */
	 public static double div(double v1,double v2){
		 return div(v1,v2,DEF_DIV_SCALE);
	 }
	 /**
	  * 提供相对精确的除法运算，当发生除不尽的情况的时候，由scale参数的值确定精度，采用四舍五入的方法（为上舍入的舍入模式）
	  * @param v1 被除数
	  * @param v2 除数
	  * @param scale 小数点后面保留的位数
	  * @return 两个数相除四舍五入的结果
	  */
	 public static double div(double v1,double v2,int scale){
		 if(scale<0) throw new IllegalArgumentException("此精度必须是正整数或者零");
		 BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	     BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	     //返回一个 BigDecimal，其值为 (this / divisor)，其标度为指定标度,向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式
	     return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	 }
	 
	  /**
	  * 精确的小数位四舍五入处理
	  * @param v 需要四舍五入的数
	  * @param scale 小数点后保留的位数（精度）
	  * @return 四舍五入的结果
	  */
	 public static double round(double v,int scale){
		 if(scale<0) throw new IllegalArgumentException("此精度必须是正整数或者零");
		 BigDecimal b = new BigDecimal(Double.toString(v));   
	     BigDecimal one = new BigDecimal("1");  
	     //返回一个 BigDecimal，其值为 (this / divisor)，其标度为指定标度,向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式
	     return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	   }
	 
	 /**
	  * 提供精确的类型转换（转换为float类型）
	  * @param v 类型转换的数
	  * @return 转换后的结果
	  */
	 public static float convertsToFloat(double v){
		 BigDecimal b=new BigDecimal(v);
		 return b.floatValue();
	   }
	
	 /**
	  * 提供精确的类型转换（转换为int类型）
	  * @param v 类型转换的数
	  * @return 转换后的结果
	  */
	 public static float convertsToInt(double v){
		 BigDecimal b=new BigDecimal(v);
		 return b.intValue();
	   }
	 
	 /**
	  * 提供精确的类型转换（转换为long类型）
	  * @param v 类型转换的数
	  * @return 转换后的结果
	  */
	 public static float convertsToLong(double v){
		 BigDecimal b=new BigDecimal(v);
		 return b.longValue();
	   }
    /**
     * 返回最大值
     * @param v1第一个数
     * @param v2第二个数
     * @return 返回的最大值
     */
	 public static double returnMax(double v1,double v2){
		 BigDecimal b1 = new BigDecimal(v1);   
		 BigDecimal b2 = new BigDecimal(v2); 
		 return b1.max(b2).doubleValue();  
	 }
	 
	 /**
	  * 返回最小值
	  * @param v1第一个数
	  * @param v2第二个数
	  * @return 返回的最小值
	  */
	 public static double returnMin(double v1,double v2){
		 BigDecimal b1 = new BigDecimal(v1);
		 BigDecimal b2 = new BigDecimal(v2);
		 return b1.min(b2).doubleValue(); 
	 }
	 
	 /**
	  * 精确的比较两个数字
	  * @param v1第一个数字
	  * @param v2第二个数字
	  * @return当结果分别为小于、等于或大于时，返回 -1、0 或 1
	  */
	public static int compareTo(double v1,double v2){
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);//当结果分别为小于、等于或大于时，返回 -1、0 或 1
	}
	 
}
