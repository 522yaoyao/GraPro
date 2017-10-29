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
//		String[] attrNames = new String[] { "年龄", "工作类型", "教育",
//				"婚姻","职业","关系"," 种族","性别 ","工作时间","国家" };
//        Map<String,String> hs=new HashMap<String,String>();
//        hs.put("职业", "6");
//        hs.put("教育", "1");
//        hs.put("关系", "1");
//        hs.put("年龄", "5");
//        hs.put("工作时间", "3");
//		// 读取样本集（已分好类）
//		Map<Object, List<Sample>> samples = readSamples(attrNames);
//        ArrayList<String> list=new ArrayList<String>();
//        list.add("1");
//        list.add("1");
//        list.add("2");
//		// 生成决策树
//		Object decisionTree = generateDecisionTree(samples, attrNames);
//
//		// 输出决策树
//		outputDecisionTree(decisionTree, 0, null);
//		//String rs=bianli(decisionTree,list);
//		String rs1=bianli1(decisionTree,hs);
//	// int  rs=getLength(decisionTree, 0, null);
//	    System.out.println(rs1);
//	}
	/**
	 * 获取树的深度
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
	 * 读取已分类的样本集，返回Map：分类 -> 属于该分类的样本的列表
	 */
	static Map<Object, List<Sample>> readSamples(String[] attrNames) {
		// 读取样本属性及其所属分类，构造表示样本的Sample对象，并按分类划分样本集
		 Map<Object, List<Sample>> ret = new HashMap<Object, List<Sample>>();
		 FileReader reader=null;
		 BufferedReader in=null;
		 String fileIn= "D://datamining//tree//process//adultResult.txt";//处理后的结果集
		 try{
			 reader=new FileReader(new File(fileIn));
			 in=new BufferedReader(reader);
			 String line=null;
			 while((line=in.readLine())!=null){//每次读取一个文本行
				 Object[] row=new Object[11];//存放读取的数据（前十项是int型最后一项是String类型）
				// ArrayList<String> list=new ArrayList<String>();
				 String[] arr=line.split(",");//转化为String数组；
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
					sample.setCategory(row[i]);//将最后的类别放在这里；
					List<Sample> samples = ret.get(row[i]);
					if (samples == null) {
						samples = new LinkedList<Sample>();
						ret.put(row[i], samples);//键为“no”和“yes”,
					}
					samples.add(sample);
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
		// 样本属性及其所属分类（数组中的最后一个元素为样本所属分类）
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
//			sample.setCategory(row[i]);//将最后的类别放在这里；
//			List<Sample> samples = ret.get(row[i]);
//			if (samples == null) {
//				samples = new LinkedList<Sample>();
//				ret.put(row[i], samples);
//			}
//			samples.add(sample);
//		}
    //   System.out.println("输出ret"+ret);
		return ret;
	}

	/**
	 * 构造决策树
	 */
	static Object generateDecisionTree(
			Map<Object, List<Sample>> categoryToSamples, String[] attrNames) {

		// 如果只有一个样本，将该样本所属分类作为新样本的分类
		if (categoryToSamples.size() == 1)
			return categoryToSamples.keySet().iterator().next();//返回类别值

		// 如果没有供决策的属性，则将样本集中具有最多样本的分类作为新样本的分类，即投票选举出分类
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
			return maxCategory;//将样本最多的作为新分类；
		}

		// 选取测试属性
		Object[] rst = chooseBestTestAttribute(categoryToSamples, attrNames);

		// 决策树根结点，分支属性为选取的测试属性
		Tree tree = new Tree(attrNames[(Integer) rst[0]]);

		// 已用过的测试属性不应再次被选为测试属性
		String[] subA = new String[attrNames.length - 1];
		for (int i = 0, j = 0; i < attrNames.length; i++)
			if (i != (Integer) rst[0])
				subA[j++] = attrNames[i];

		// 根据分支属性生成分支
		@SuppressWarnings("unchecked")
		Map<Object, Map<Object, List<Sample>>> splits =
		/* NEW LINE */(Map<Object, Map<Object, List<Sample>>>) rst[2];
		for (Entry<Object, Map<Object, List<Sample>>> entry : splits.entrySet()) {
			Object attrValue = entry.getKey();//测试属性的属性值
			Map<Object, List<Sample>> split = entry.getValue();//特定属性值的样本列表
			Object child = generateDecisionTree(split, subA);
			tree.setChild(attrValue, child);
		}
   	return tree;
	}

	/**
	 * 选取最优测试属性。最优是指如果根据选取的测试属性分支，则从各分支确定新样本
	 * 的分类需要的信息量之和最小，这等价于确定新样本的测试属性获得的信息增益最大
	 * 返回数组：选取的属性下标、信息量之和、Map(属性值->(分类->样本列表))
	 */
	static Object[] chooseBestTestAttribute(
			Map<Object, List<Sample>> categoryToSamples, String[] attrNames) {

		int minIndex = -1; // 最优属性下标
		double minValue = Double.MAX_VALUE; // 最小信息量
		Map<Object, Map<Object, List<Sample>>> minSplits = null; // 最优分支方案

		// 对每一个属性，计算将其作为测试属性的情况下在各分支确定新样本的分类需要的信息量之和，选取最小为最优
		for (int attrIndex = 0; attrIndex < attrNames.length; attrIndex++) {
			int allCount = 0; // 统计样本总数的计数器

			// 按当前属性构建Map：属性值->(分类->样本列表)
			Map<Object, Map<Object, List<Sample>>> curSplits =
			/* NEW LINE */new HashMap<Object, Map<Object, List<Sample>>>();
			for (Entry<Object, List<Sample>> entry : categoryToSamples
					.entrySet()) {//Entry映射项（键-值对）
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

			// 计算将当前属性作为测试属性的情况下在各分支确定新样本的分类需要的信息量之和
			double curValue = 0.0; // 计数器：累加各分支
			for (Map<Object, List<Sample>> splits : curSplits.values()) {//获取对应特征属性下的所有样本列表（属性值->（分类->样本列表））
				double perSplitCount = 0;
				for (List<Sample> list : splits.values())
					perSplitCount += list.size(); // 计算当前属性值下的所有样本数（yes和no);
				double perSplitValue = 0.0; // 计数器：当前分支
				for (List<Sample> list : splits.values()) {
					double p = list.size() / perSplitCount;
					perSplitValue -= p * (Math.log(p) / Math.log(2));
				}
				curValue += (perSplitCount / allCount) * perSplitValue;
			}

			// 选取最小为最优
			if (minValue > curValue) {
				minIndex = attrIndex;
				minValue = curValue;
				minSplits = curSplits;
			}
		}
    //    System.out.println("构造"+ minIndex+" "+minValue+" "+minSplits );
		
		return new Object[] { minIndex, minValue, minSplits };
	}

	/**
	 * 将决策树输出到标准输出
	 */
	static void outputDecisionTree(Object obj, int level, Object from) {
		for (int i = 0; i < level; i++)
			System.out.print("|-----");
		if (from != null)
			System.out.printf("(%s):", from);
		if (obj instanceof Tree) {//子节点仍然是一棵树；
			Tree tree = (Tree) obj;
			String attrName = tree.getAttribute();
			System.out.printf("[%s = ?]\n", attrName);
			for (Object attrValue : tree.getAttributeValues()) {//返回所有键的set视图；
				Object child = tree.getChild(attrValue);//根据键获取相应的值；
				outputDecisionTree(child, level + 1, attrName + " = "
						+ attrValue);
			}
		} else {//对应属性的值不相等；
			System.out.printf("[CATEGORY = %s]\n", obj);
		}
	}
	public static void length(Object obj, int level, Object from ,TreeSet<Integer> set){
		  
		if (obj instanceof Tree) {//子节点仍然是一棵树；
			Tree tree = (Tree) obj;
			String attrName = tree.getAttribute();
			//System.out.printf("[%s = ?]\n", attrName);
			for (Object attrValue : tree.getAttributeValues()) {//返回所有键的set视图；
				Object child = tree.getChild(attrValue);//根据键获取相应的值；
				length(child, level + 1, attrName + " = "
						+ attrValue,set);
			}
		} else {//子节点是叶子节点，返回树的深度
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
			 result=tree.getAttribute();//获取当前节点的属性；
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
	  * 遍历树,要考虑到最后没有相应路径的情况，此时无法获取预测结果，返回“error”;
	  * @param obj
	  * @param testVec
	  * @return
	  */
	 public static String bianli1(Object obj,Map<String,String> map){
		 boolean flag=false;
		 String result = "";
		 if(!(obj instanceof Tree)){//当前节点是叶子节点；
			 result=(String)obj;
		 }
	    if("no".equals(result)||"yes".equals(result))return result;
		 if (obj instanceof Tree) {//当前节点是非叶子节点
			 String property=null;
			 Tree tree = (Tree) obj;
			 result=tree.getAttribute();//获取当前节点的属性；
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
	 * 样本，包含多个属性和一个指明样本所属分类的分类值
	 */
	static class Sample {

		private Map<String, Object> attributes = new HashMap<String, Object>();

		private Object category;//种类；

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
	 * 决策树（非叶结点），决策树中的每个非叶结点都引导了一棵决策树
	 * 每个非叶结点包含一个分支属性和多个分支，分支属性的每个值对应一个分支，该分支引导了一棵子决策树
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
			return children.keySet();//返回此映射中所包含的键的 Set 视图。
		}

	}

}