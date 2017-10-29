package ly.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Rsult {
	public static void main(String[] args){
		//ArrayList<ArrayList<String>>rs=returnRs();
		System.out.println("haha");
	}
	
	/**
	 * 将实验结果返回
	 * @param tmpFile
	 * @return
	 */
   public static ArrayList<ArrayList<String>> returnRs(String tmpFile){
	   ArrayList<ArrayList<String>> rsList=new ArrayList<ArrayList<String>>();//存储运算结果
	    FileReader reader=null;
	    BufferedReader in=null;
	   //将实验结果从文件读出
		//String tmpFile="D://datamining//tmp.txt";//存储中间结果的文件
		try{
			 reader=new FileReader(new File(tmpFile));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//每次读取一个文本行
				ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");
				   for(int i=0;i<arr.length;i++){
					list.add(arr[i]);
				 	}
				   rsList.add(list);
		}
		 }catch(Exception e){
			 System.out.println("读取文件出错");
			 e.printStackTrace();
		 }finally{
			 try {
				in.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	  return rsList; 
   }
	


}
