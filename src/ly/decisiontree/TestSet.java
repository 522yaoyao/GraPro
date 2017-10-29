package ly.decisiontree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.decisiontree.DicisionTree.Sample;

public class TestSet {
	/**
	 * ��ȡ�����ĵ�
	 * @param fileIn����Ĳ����ļ�
	 * @return �Լ��ϵ���ʽ��������
	 */
	 public  static ArrayList<ArrayList<String>> readTest(String fileIn){
		 ArrayList<ArrayList<String>> testList=new  ArrayList<ArrayList<String>>();
		
		 FileReader reader=null;
		 BufferedReader in=null;
		 
		 try{
			 reader=new FileReader(new File(fileIn));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//ÿ�ζ�ȡһ���ı���
				 ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");
				 for(int i=0;i<arr.length;i++){
					 list.add(arr[i]);
				}
				 testList.add(list);
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
		return testList;
	 }
	 /**
	  * ���ز��Լ���Ԥ������
	  * @param list
	  * @return
	  */
	 public static int testResult(ArrayList<ArrayList<String>> testList,Object obj){
		 HashMap<String, String> map=new HashMap<String,String>();
		 String rs=null;//Ԥ��Ľ��
		 String label=null;//��ȷ�Ľ��
		 int rightNum=0;//Ԥ����ȷ����Ŀ��
		 String[] attrNames = new String[] { "����", "��������", "����",
					"����","ְҵ","��ϵ"," ����","�Ա� ","����ʱ��","����" };
		 for(int i = 0;i < testList.size();i++){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp = testList.get(i);//��ȡһ����������
			    label = tmp.get(tmp.size()-1);//����ȷ�Ľ���洢
				tmp.remove(tmp.size() - 1);
				//����������ת��ΪMap<String,String>����
				for(int j=0; j<tmp.size();j++){
					map.put(attrNames[j], tmp.get(j));
				}
			    //����Ԥ����
				rs=DicisionTree.bianli1(obj, map);
				if(label.equals(rs)) {rightNum++;
				}
				}
		   //  System.out.println("��ȷ�Ľ��"+rs);
		     return rightNum;
			}
	 public static int lastRs(String fileIn){
		 String[] attrNames = new String[] { "����", "��������", "����",
					"����","ְҵ","��ϵ"," ����","�Ա� ","����ʱ��","����" };
			// ��ȡ���������ѷֺ��ࣩ
					Map<Object, List<Sample>> samples = DicisionTree.readSamples(attrNames);
					Object decisionTree = DicisionTree.generateDecisionTree(samples, attrNames);
				//	DicisionTree.outputDecisionTree(decisionTree, 0, null);
		            ArrayList<ArrayList<String>> testList=TestSet.readTest(fileIn);
		            return TestSet.testResult(testList, decisionTree);
		 
	 }
	 }

