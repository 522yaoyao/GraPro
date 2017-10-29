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
	 * 读取测试文档
	 * @param fileIn读入的测试文件
	 * @return 以集合的形式返回数据
	 */
	 public  static ArrayList<ArrayList<String>> readTest(String fileIn){
		 ArrayList<ArrayList<String>> testList=new  ArrayList<ArrayList<String>>();
		
		 FileReader reader=null;
		 BufferedReader in=null;
		 
		 try{
			 reader=new FileReader(new File(fileIn));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//每次读取一个文本行
				 ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");
				 for(int i=0;i<arr.length;i++){
					 list.add(arr[i]);
				}
				 testList.add(list);
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
		return testList;
	 }
	 /**
	  * 返回测试集的预测结果；
	  * @param list
	  * @return
	  */
	 public static int testResult(ArrayList<ArrayList<String>> testList,Object obj){
		 HashMap<String, String> map=new HashMap<String,String>();
		 String rs=null;//预测的结果
		 String label=null;//正确的结果
		 int rightNum=0;//预测正确的数目；
		 String[] attrNames = new String[] { "年龄", "工作类型", "教育",
					"婚姻","职业","关系"," 种族","性别 ","工作时间","国家" };
		 for(int i = 0;i < testList.size();i++){
				ArrayList<String> tmp = new ArrayList<String>();
				tmp = testList.get(i);//获取一条测试数据
			    label = tmp.get(tmp.size()-1);//将正确的结果存储
				tmp.remove(tmp.size() - 1);
				//将数组类型转换为Map<String,String>类型
				for(int j=0; j<tmp.size();j++){
					map.put(attrNames[j], tmp.get(j));
				}
			    //返回预测结果
				rs=DicisionTree.bianli1(obj, map);
				if(label.equals(rs)) {rightNum++;
				}
				}
		   //  System.out.println("正确的结果"+rs);
		     return rightNum;
			}
	 public static int lastRs(String fileIn){
		 String[] attrNames = new String[] { "年龄", "工作类型", "教育",
					"婚姻","职业","关系"," 种族","性别 ","工作时间","国家" };
			// 读取样本集（已分好类）
					Map<Object, List<Sample>> samples = DicisionTree.readSamples(attrNames);
					Object decisionTree = DicisionTree.generateDecisionTree(samples, attrNames);
				//	DicisionTree.outputDecisionTree(decisionTree, 0, null);
		            ArrayList<ArrayList<String>> testList=TestSet.readTest(fileIn);
		            return TestSet.testResult(testList, decisionTree);
		 
	 }
	 }

