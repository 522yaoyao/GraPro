package ly.tool;

import java.math.BigDecimal;

/**
 * ����Java�ļ��������Ͳ��ܾ�ȷ�ĶԸ��������м��㣬�˹������ṩ��ȷ�ĸ��������㣬�����Ӽ��˳�����������
 */
public class DecimalCalculate {
	
	//Ĭ�ϵĳ�������
	private static final int  DEF_DIV_SCALE=10;
    
	//�����췽�����ó�˽�еģ����Ժܺõı�֤���಻�����������б�ʵ����
	private DecimalCalculate(){
		
	}
	
	/**
	 * ��ȷ�ļӷ�����
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������ĺ�
	 */
	public static double add(double v1,double v2){   
        BigDecimal b1 = new BigDecimal(Double.toString(v1));   
        BigDecimal b2 = new BigDecimal(Double.toString(v2));   
        return b1.add(b2).doubleValue();   
    }   
	
	/**
	 * ��ȷ�ļ�������
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������Ĳ�
	 */
	 public static double sub(double v1,double v2){   
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	        return b1.subtract(b2).doubleValue(); 
	 }
	 
	 /**
	  * ��ȷ�ĳ˷�����
	  * @param v1 ������
	  * @param v2 ����
	  * @return �������Ļ�
	  */
	 public static double mul(double v1,double v2){   
	        BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	        BigDecimal b2 = new BigDecimal(Double.toString(v2));   
	        return b1.multiply(b2).doubleValue();   
	    }
	 
	 /**
	  * ��ȷ�ĳ�������
	  * @param v1 ������
	  * @param v2 ����
	  * @return����������
	  */
	 public static double div(double v1,double v2){
		 return div(v1,v2,DEF_DIV_SCALE);
	 }
	 /**
	  * �ṩ��Ծ�ȷ�ĳ������㣬�������������������ʱ����scale������ֵȷ�����ȣ�������������ķ�����Ϊ�����������ģʽ��
	  * @param v1 ������
	  * @param v2 ����
	  * @param scale С������汣����λ��
	  * @return �����������������Ľ��
	  */
	 public static double div(double v1,double v2,int scale){
		 if(scale<0) throw new IllegalArgumentException("�˾��ȱ�����������������");
		 BigDecimal b1 = new BigDecimal(Double.toString(v1));   
	     BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
	     //����һ�� BigDecimal����ֵΪ (this / divisor)������Ϊָ�����,����ӽ��ġ��������룬����������������ֵľ�����ȣ���Ϊ�����������ģʽ
	     return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	 }
	 
	  /**
	  * ��ȷ��С��λ�������봦��
	  * @param v ��Ҫ�����������
	  * @param scale С���������λ�������ȣ�
	  * @return ��������Ľ��
	  */
	 public static double round(double v,int scale){
		 if(scale<0) throw new IllegalArgumentException("�˾��ȱ�����������������");
		 BigDecimal b = new BigDecimal(Double.toString(v));   
	     BigDecimal one = new BigDecimal("1");  
	     //����һ�� BigDecimal����ֵΪ (this / divisor)������Ϊָ�����,����ӽ��ġ��������룬����������������ֵľ�����ȣ���Ϊ�����������ģʽ
	     return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	   }
	 
	 /**
	  * �ṩ��ȷ������ת����ת��Ϊfloat���ͣ�
	  * @param v ����ת������
	  * @return ת����Ľ��
	  */
	 public static float convertsToFloat(double v){
		 BigDecimal b=new BigDecimal(v);
		 return b.floatValue();
	   }
	
	 /**
	  * �ṩ��ȷ������ת����ת��Ϊint���ͣ�
	  * @param v ����ת������
	  * @return ת����Ľ��
	  */
	 public static float convertsToInt(double v){
		 BigDecimal b=new BigDecimal(v);
		 return b.intValue();
	   }
	 
	 /**
	  * �ṩ��ȷ������ת����ת��Ϊlong���ͣ�
	  * @param v ����ת������
	  * @return ת����Ľ��
	  */
	 public static float convertsToLong(double v){
		 BigDecimal b=new BigDecimal(v);
		 return b.longValue();
	   }
    /**
     * �������ֵ
     * @param v1��һ����
     * @param v2�ڶ�����
     * @return ���ص����ֵ
     */
	 public static double returnMax(double v1,double v2){
		 BigDecimal b1 = new BigDecimal(v1);   
		 BigDecimal b2 = new BigDecimal(v2); 
		 return b1.max(b2).doubleValue();  
	 }
	 
	 /**
	  * ������Сֵ
	  * @param v1��һ����
	  * @param v2�ڶ�����
	  * @return ���ص���Сֵ
	  */
	 public static double returnMin(double v1,double v2){
		 BigDecimal b1 = new BigDecimal(v1);
		 BigDecimal b2 = new BigDecimal(v2);
		 return b1.min(b2).doubleValue(); 
	 }
	 
	 /**
	  * ��ȷ�ıȽ���������
	  * @param v1��һ������
	  * @param v2�ڶ�������
	  * @return������ֱ�ΪС�ڡ����ڻ����ʱ������ -1��0 �� 1
	  */
	public static int compareTo(double v1,double v2){
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.compareTo(b2);//������ֱ�ΪС�ڡ����ڻ����ʱ������ -1��0 �� 1
	}
	 
}
