package ly.decisiontree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ly.decisiontree.DicisionTree.Sample;
//import ly.decisiontree.DicisionTree.Tree;
import ly.tool.DecimalCalculate;

public class Main {
	public static void main(String[] args)throws Exception{
		String[] attrNames = new String[] { "年龄", "工作类型", "教育",
				"婚姻","职业","关系"," 种族","性别 ","工作时间","国家" };
		// 读取样本集（已分好类）
				Map<Object, List<Sample>> samples = DicisionTree.readSamples(attrNames);
				Object decisionTree = DicisionTree.generateDecisionTree(samples, attrNames);
				DicisionTree.outputDecisionTree(decisionTree, 0, null);
		 String fileIn="D://1//2.txt";
		 ArrayList<ArrayList<String>> testList=TestSet.readTest(fileIn);
		int rs=TestSet.testResult(testList, decisionTree);
		System.out.println("预试正确个数："+ rs+", 测试总个数"+testList.size());
		System.out.println("正确率为：" +(1-(DecimalCalculate.sub(1.00000000, DecimalCalculate.div(rs, testList.size()))))*100 + "%");
		 
  }

}
