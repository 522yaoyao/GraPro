package ly.decisiontree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class DicisionTree {
	//static TreeSet<Integer> set=new TreeSet<Integer>();
//	public static void main(String[] args) throws Exception {
//		String[] attrNames = new String[] { "����", "��������", "����",
//				"����","ְҵ","��ϵ"," ����","�Ա� ","����ʱ��","����" };
//        Map<String,String> hs=new HashMap<String,String>();
//        hs.put("ְҵ", "6");
//        hs.put("����", "1");
//        hs.put("��ϵ", "1");
//        hs.put("����", "5");
//        hs.put("����ʱ��", "3");
//		// ��ȡ���������ѷֺ��ࣩ
//		Map<Object, List<Sample>> samples = readSamples(attrNames);
//        ArrayList<String> list=new ArrayList<String>();
//        list.add("1");
//        list.add("1");
//        list.add("2");
//		// ���ɾ�����
//		Object decisionTree = generateDecisionTree(samples, attrNames);
//
//		// ���������
//		outputDecisionTree(decisionTree, 0, null);
//		//String rs=bianli(decisionTree,list);
//		String rs1=bianli1(decisionTree,hs);
//	// int  rs=getLength(decisionTree, 0, null);
//	    System.out.println(rs1);
//	}
	/**
	 * ��ȡ�������
	 * @param obj
	 * @param level
	 * @param from
	 * @return
	 */
    public static int getLength(Object obj, int level, Object from){
    	 TreeSet<Integer> set=new TreeSet<Integer>();
    	 length(obj, 0, null ,  set);
    	 return set.last();
    }
	/**
	 * ��ȡ�ѷ����������������Map������ -> ���ڸ÷�����������б�
	 */
	static Map<Object, List<Sample>> readSamples(String[] attrNames) {
		// ��ȡ�������Լ����������࣬�����ʾ������Sample���󣬲������໮��������
		 Map<Object, List<Sample>> ret = new HashMap<Object, List<Sample>>();
		 FileReader reader=null;
		 BufferedReader in=null;
		 String fileIn= "D://datamining//tree//process//adultResult.txt";//�����Ľ����
		 try{
			 reader=new FileReader(new File(fileIn));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//ÿ�ζ�ȡһ���ı���
				 Object[] row=new Object[11];//��Ŷ�ȡ�����ݣ�ǰʮ����int�����һ����String���ͣ�
				// ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");//ת��ΪString���飻
				 for(int j=0;j<arr.length;j++){
					 if(j!=10){
					 row[j]=Integer.parseInt(arr[j]);
					 }else{
					 row[j]=arr[j];
					 }
				 //list.add(arr[i]);
				}
			//	 System.out.println(row[10]);
				 Sample sample = new Sample();
					int i = 0;
					for (int n = row.length - 1; i < n; i++)
						sample.setAttribute(attrNames[i], row[i]);
					sample.setCategory(row[i]);//���������������
					List<Sample> samples = ret.get(row[i]);
					if (samples == null) {
						samples = new LinkedList<Sample>();
						ret.put(row[i], samples);//��Ϊ��no���͡�yes��,
					}
					samples.add(sample);
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
		// �������Լ����������ࣨ�����е����һ��Ԫ��Ϊ�����������ࣩ
//		Object[][] rawData = new Object[][] {
//				{3,1,1,1,6,1,5,1,5,0,"no" },
//				{ 4,1,11,1,5,1,1,1,5,1,"no" },
//				{5,1,8,6,3,4,5,1,2,0,"no" },
//				{ 6,2,4,1,5,3,1,2,5,1,"yes" },
//				{ 4,1,11,3,6,4,1,1,6,1,"yes"},
//				{5,1,1,1,5,3,1,2,5,1,"yes" },
//				{ 4,1,2,1,5,3,5,2,9,1,"yes"},
//				{4,6,1,1,6,3,2,2,5,0,"yes" },
//				{ 3,1,1,3,9,2,1,1,4,1,"no" } };

//		for (Object[] row : rawData) {
//			Sample sample = new Sample();
//			int i = 0;
//			for (int n = row.length - 1; i < n; i++)
//				sample.setAttribute(attrNames[i], row[i]);
//			sample.setCategory(row[i]);//���������������
//			List<Sample> samples = ret.get(row[i]);
//			if (samples == null) {
//				samples = new LinkedList<Sample>();
//				ret.put(row[i], samples);
//			}
//			samples.add(sample);
//		}
    //   System.out.println("���ret"+ret);
		return ret;
	}

	/**
	 * ���������
	 */
	static Object generateDecisionTree(
			Map<Object, List<Sample>> categoryToSamples, String[] attrNames) {

		// ���ֻ��һ��������������������������Ϊ�������ķ���
		if (categoryToSamples.size() == 1)
			return categoryToSamples.keySet().iterator().next();//�������ֵ

		// ���û�й����ߵ����ԣ����������о�����������ķ�����Ϊ�������ķ��࣬��ͶƱѡ�ٳ�����
		if (attrNames.length == 0) {
			int max = 0;
			Object maxCategory = null;
			for (Entry<Object, List<Sample>> entry : categoryToSamples
					.entrySet()) {
				int cur = entry.getValue().size();
				if (cur > max) {
					max = cur;
					maxCategory = entry.getKey();
				}
			}
			return maxCategory;//������������Ϊ�·��ࣻ
		}

		// ѡȡ��������
		Object[] rst = chooseBestTestAttribute(categoryToSamples, attrNames);

		// ����������㣬��֧����Ϊѡȡ�Ĳ�������
		Tree tree = new Tree(attrNames[(Integer) rst[0]]);

		// ���ù��Ĳ������Բ�Ӧ�ٴα�ѡΪ��������
		String[] subA = new String[attrNames.length - 1];
		for (int i = 0, j = 0; i < attrNames.length; i++)
			if (i != (Integer) rst[0])
				subA[j++] = attrNames[i];

		// ���ݷ�֧�������ɷ�֧
		@SuppressWarnings("unchecked")
		Map<Object, Map<Object, List<Sample>>> splits =
		/* NEW LINE */(Map<Object, Map<Object, List<Sample>>>) rst[2];
		for (Entry<Object, Map<Object, List<Sample>>> entry : splits.entrySet()) {
			Object attrValue = entry.getKey();//�������Ե�����ֵ
			Map<Object, List<Sample>> split = entry.getValue();//�ض�����ֵ�������б�
			Object child = generateDecisionTree(split, subA);
			tree.setChild(attrValue, child);
		}
   	return tree;
	}

	/**
	 * ѡȡ���Ų������ԡ�������ָ�������ѡȡ�Ĳ������Է�֧����Ӹ���֧ȷ��������
	 * �ķ�����Ҫ����Ϣ��֮����С����ȼ���ȷ���������Ĳ������Ի�õ���Ϣ�������
	 * �������飺ѡȡ�������±ꡢ��Ϣ��֮�͡�Map(����ֵ->(����->�����б�))
	 */
	static Object[] chooseBestTestAttribute(
			Map<Object, List<Sample>> categoryToSamples, String[] attrNames) {

		int minIndex = -1; // ���������±�
		double minValue = Double.MAX_VALUE; // ��С��Ϣ��
		Map<Object, Map<Object, List<Sample>>> minSplits = null; // ���ŷ�֧����

		// ��ÿһ�����ԣ����㽫����Ϊ�������Ե�������ڸ���֧ȷ���������ķ�����Ҫ����Ϣ��֮�ͣ�ѡȡ��СΪ����
		for (int attrIndex = 0; attrIndex < attrNames.length; attrIndex++) {
			int allCount = 0; // ͳ�����������ļ�����

			// ����ǰ���Թ���Map������ֵ->(����->�����б�)
			Map<Object, Map<Object, List<Sample>>> curSplits =
			/* NEW LINE */new HashMap<Object, Map<Object, List<Sample>>>();
			for (Entry<Object, List<Sample>> entry : categoryToSamples
					.entrySet()) {//Entryӳ�����-ֵ�ԣ�
				Object category = entry.getKey();
				List<Sample> samples = entry.getValue();
				for (Sample sample : samples) {
					Object attrValue = sample
							.getAttribute(attrNames[attrIndex]);
					Map<Object, List<Sample>> split = curSplits.get(attrValue);
					if (split == null) {
						split = new HashMap<Object, List<Sample>>();
						curSplits.put(attrValue, split);
					}
					List<Sample> splitSamples = split.get(category);
					if (splitSamples == null) {
						splitSamples = new LinkedList<Sample>();
						split.put(category, splitSamples);
					}
					splitSamples.add(sample);
				}
				allCount += samples.size();
			}

			// ���㽫��ǰ������Ϊ�������Ե�������ڸ���֧ȷ���������ķ�����Ҫ����Ϣ��֮��
			double curValue = 0.0; // ���������ۼӸ���֧
			for (Map<Object, List<Sample>> splits : curSplits.values()) {//��ȡ��Ӧ���������µ����������б�����ֵ->������->�����б���
				double perSplitCount = 0;
				for (List<Sample> list : splits.values())
					perSplitCount += list.size(); // ���㵱ǰ����ֵ�µ�������������yes��no);
				double perSplitValue = 0.0; // ����������ǰ��֧
				for (List<Sample> list : splits.values()) {
					double p = list.size() / perSplitCount;
					perSplitValue -= p * (Math.log(p) / Math.log(2));
				}
				curValue += (perSplitCount / allCount) * perSplitValue;
			}

			// ѡȡ��СΪ����
			if (minValue > curValue) {
				minIndex = attrIndex;
				minValue = curValue;
				minSplits = curSplits;
			}
		}
    //    System.out.println("����"+ minIndex+" "+minValue+" "+minSplits );
		
		return new Object[] { minIndex, minValue, minSplits };
	}

	/**
	 * ���������������׼���
	 */
	static void outputDecisionTree(Object obj, int level, Object from) {
		for (int i = 0; i < level; i++)
			System.out.print("|-----");
		if (from != null)
			System.out.printf("(%s):", from);
		if (obj instanceof Tree) {//�ӽڵ���Ȼ��һ������
			Tree tree = (Tree) obj;
			String attrName = tree.getAttribute();
			System.out.printf("[%s = ?]\n", attrName);
			for (Object attrValue : tree.getAttributeValues()) {//�������м���set��ͼ��
				Object child = tree.getChild(attrValue);//���ݼ���ȡ��Ӧ��ֵ��
				outputDecisionTree(child, level + 1, attrName + " = "
						+ attrValue);
			}
		} else {//��Ӧ���Ե�ֵ����ȣ�
			System.out.printf("[CATEGORY = %s]\n", obj);
		}
	}
	public static void length(Object obj, int level, Object from ,TreeSet<Integer> set){
		  
		if (obj instanceof Tree) {//�ӽڵ���Ȼ��һ������
			Tree tree = (Tree) obj;
			String attrName = tree.getAttribute();
			//System.out.printf("[%s = ?]\n", attrName);
			for (Object attrValue : tree.getAttributeValues()) {//�������м���set��ͼ��
				Object child = tree.getChild(attrValue);//���ݼ���ȡ��Ӧ��ֵ��
				length(child, level + 1, attrName + " = "
						+ attrValue,set);
			}
		} else {//�ӽڵ���Ҷ�ӽڵ㣬�����������
		//	System.out.printf("[CATEGORY = %s]\n", obj);
			set.add(level);
		}
		
	}
	 public static String bianli(Object obj,ArrayList<String> testVec){
		 String result = new String();
		 if(!(obj instanceof Tree)){
			 result=(String)obj;
		 }
	  //   Tree tree = (Tree) obj;
		// result=tree.getAttribute();
		  if("no".equals(result)||"yes".equals(result))return result;
		 if (obj instanceof Tree) {
			 Tree tree = (Tree) obj;
			 result=tree.getAttribute();//��ȡ��ǰ�ڵ�����ԣ�
			  if("no".equals(result)||"yes".equals(result))return result;
		  for (int i = 0; i <getLength(obj, 0, null); i++) {  
			  if(testVec.size()!=0){
				  for (Object attrValue : tree.getAttributeValues()) {
				  if(testVec.get(0).equals(attrValue.toString())){  
                    testVec.remove(testVec.get(0));  
                      result=bianli(tree.getChild(attrValue),testVec);  
                      if("no".equals(result)||"yes".equals(result))return result;
                  }  
			  }
	 } 
	 }
		 }
			 
		 
		  return result;	  
	 }
	 /**
	  * ������,Ҫ���ǵ����û����Ӧ·�����������ʱ�޷���ȡԤ���������ء�error��;
	  * @param obj
	  * @param testVec
	  * @return
	  */
	 public static String bianli1(Object obj,Map<String,String> map){
		 boolean flag=false;
		 String result = "";
		 if(!(obj instanceof Tree)){//��ǰ�ڵ���Ҷ�ӽڵ㣻
			 result=(String)obj;
		 }
	    if("no".equals(result)||"yes".equals(result))return result;
		 if (obj instanceof Tree) {//��ǰ�ڵ��Ƿ�Ҷ�ӽڵ�
			 String property=null;
			 Tree tree = (Tree) obj;
			 result=tree.getAttribute();//��ȡ��ǰ�ڵ�����ԣ�
			 property=result;
			  if("no".equals(result)||"yes".equals(result)||"error".equals(result))return result;
		  for (int i = 0; i <getLength(obj, 0, null); i++) {  
			  if(map.size()!=0){
			  for (Object value : tree.getAttributeValues()) {
				  if(map.get(property).equals(value.toString()))
					  flag=true;
			  }
				 if(!flag) return "error";
				 for (Object attrValue : tree.getAttributeValues()) {
					
				  if(map.get(property).equals(attrValue.toString())){  
                	  map.remove(property);
                      result=bianli1(tree.getChild(attrValue),map);  
                      if("no".equals(result)||"yes".equals(result)||"error".equals(result))return result;
                  }  
			  }
	 } 
	 }
		 }
			 
		 
		  return result;	  
	 }
	/**
	 * ����������������Ժ�һ��ָ��������������ķ���ֵ
	 */
	static class Sample {

		private Map<String, Object> attributes = new HashMap<String, Object>();

		private Object category;//���ࣻ

		public Object getAttribute(String name) {
			return attributes.get(name);
		}

		public void setAttribute(String name, Object value) {
			attributes.put(name, value);
		}

		public Object getCategory() {
			return category;
		}

		public void setCategory(Object category) {
			this.category = category;
		}

		public String toString() {
			return attributes.toString();
		}

	}

	/**
	 * ����������Ҷ��㣩���������е�ÿ����Ҷ��㶼������һ�þ�����
	 * ÿ����Ҷ������һ����֧���ԺͶ����֧����֧���Ե�ÿ��ֵ��Ӧһ����֧���÷�֧������һ���Ӿ�����
	 */
	static class Tree {

		private String attribute;

		private Map<Object, Object> children = new HashMap<Object, Object>();

		public Tree(String attribute) {
			this.attribute = attribute;
		}

		public String getAttribute() {
			return attribute;
		}

		public Object getChild(Object attrValue) {
			return children.get(attrValue);
		}

		public void setChild(Object attrValue, Object child) {
			children.put(attrValue, child);
		}

		public Set<Object> getAttributeValues() {
			return children.keySet();//���ش�ӳ�����������ļ��� Set ��ͼ��
		}

	}

}