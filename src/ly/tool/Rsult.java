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
	 * ��ʵ��������
	 * @param tmpFile
	 * @return
	 */
   public static ArrayList<ArrayList<String>> returnRs(String tmpFile){
	   ArrayList<ArrayList<String>> rsList=new ArrayList<ArrayList<String>>();//�洢������
	    FileReader reader=null;
	    BufferedReader in=null;
	   //��ʵ�������ļ�����
		//String tmpFile="D://datamining//tmp.txt";//�洢�м������ļ�
		try{
			 reader=new FileReader(new File(tmpFile));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//ÿ�ζ�ȡһ���ı���
				ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");
				   for(int i=0;i<arr.length;i++){
					list.add(arr[i]);
				 	}
				   rsList.add(list);
		}
		 }catch(Exception e){
			 System.out.println("��ȡ�ļ�����");
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
