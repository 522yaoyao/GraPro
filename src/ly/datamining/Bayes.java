package ly.datamining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.tool.DecimalCalculate;

public class Bayes {
	public String predictClass(ArrayList<ArrayList<String >> trainList,ArrayList<String> testList){
		Map<String, ArrayList<ArrayList<String>>> resultMap = dataSet(trainList);//将两类数据以键值对的形式返回；
		double mMax = 0.0;
		String finalResult  = "";
		for(int i = 0;i < resultMap.size();i++){
			double mCurrent = 0.0;
			String key = "";
			if(i == 0) key = "yes";
			else key = "no";
			ArrayList<ArrayList<String>> temp = resultMap.get(key);//取出某一类的所有数据
			mCurrent = culCofV(temp.size(),trainList.size());//"yes"或“no”占总数据的概率
			for(int j = 0;j < testList.size();j++){
				 
				double pv = culPofV(temp,testList.get(j),j);//先验概率
				mCurrent = DecimalCalculate.mul(mCurrent, pv); //最终运算结果为后验概率
			}
			//max{p(yes|x),p(no|x)}返回最大概率所对应的值
			if(mMax <= mCurrent){
				if(i == 0){
					finalResult = "yes";
				}else{
					finalResult = "no";
				}
				mMax = mCurrent;
			}
		}
		return finalResult;
	}
	/**
	 * 计算总概率P(y)
	 * @param ySize
	 * @param nSize
	 * @return
	 */
	public double culCofV(int ySize,int nSize){
		return DecimalCalculate.div(ySize, nSize);
	}
	/**
	 * 分类
	 * @param list
	 * @return
	 */
	public Map<String, ArrayList<ArrayList<String>>> dataSet(List<ArrayList<String>> list){
		Map<String, ArrayList<ArrayList<String>>> culMap = new HashMap<String, ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> mIsList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> mNoList = new ArrayList<ArrayList<String>>();
		for(int i = 0;i < list.size();i++){
			ArrayList<String> temp = new ArrayList<String>();
			temp = list.get(i);
		String mResult = temp.get(temp.size()-1);//获取最后一项
		if(mResult.equals("yes")){
			mIsList.add(temp);
		}else{
			mNoList.add(temp);
		}
		}
		culMap.put("yes", mIsList);
		culMap.put("no", mNoList);
		return culMap;
	}
	/**
	 * 计算某属性在某特定训练集的概率（先验概率）
	 * @param mList 某一类训练集
	 * @param mStr  需要预测的某条测试数据的相应的属性
	 * @param index 此属性在这条测试集的索引
	 * @return 先验概率
	 */
	public double culPofV(ArrayList<ArrayList<String>> mList,String mStr,int index){
		int count = 0;
		for(int i = 0;i < mList.size();i++){
			if(mStr.equals(mList.get(i).get(index))){
				count++;
			}
		}
		return DecimalCalculate.div(count, mList.size(), 3);
	}


}
