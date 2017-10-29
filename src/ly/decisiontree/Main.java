package ly.decisiontree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ly.decisiontree.DicisionTree.Sample;
//import ly.decisiontree.DicisionTree.Tree;
import ly.tool.DecimalCalculate;

public class Main {
	public static void main(String[] args)throws Exception{
		String[] attrNames = new String[] { "����", "��������", "����",
				"����","ְҵ","��ϵ"," ����","�Ա� ","����ʱ��","����" };
		// ��ȡ���������ѷֺ��ࣩ
				Map<Object, List<Sample>> samples = DicisionTree.readSamples(attrNames);
				Object decisionTree = DicisionTree.generateDecisionTree(samples, attrNames);
				DicisionTree.outputDecisionTree(decisionTree, 0, null);
		 String fileIn="D://1//2.txt";
		 ArrayList<ArrayList<String>> testList=TestSet.readTest(fileIn);
		int rs=TestSet.testResult(testList, decisionTree);
		System.out.println("Ԥ����ȷ������"+ rs+", �����ܸ���"+testList.size());
		System.out.println("��ȷ��Ϊ��" +(1-(DecimalCalculate.sub(1.00000000, DecimalCalculate.div(rs, testList.size()))))*100 + "%");
		 
  }

}
