<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="ly.tool.DecimalCalculate"%>
<%@page import="ly.datamining.Bayes" %>
<%@page import="ly.datamining.PreRead" %>
<%@page import="java.util.ArrayList" %>

<%

ArrayList<ArrayList<String>> trainList=null ;
ArrayList<ArrayList<String>> testList =null;

boolean type = false; //是否除干扰

String train=request.getParameter("train");
String sound=request.getParameter("sound");
String test=request.getParameter("test");

String processedTrain = "D://datamining//process//adultResult.txt";
String processedTest = "D://datamining//process//adult1000Result.txt";
String processedTest100 = "D://datamining//process//adult100Result.txt";

String finalStr = "";
int wrong_number = 0; //记录正确的数量
double finalData = 0.0; //最后比率
boolean flag = true; //标志测试的数量
boolean finish=false;//算法是否完成
if("yes".equals(sound)) type=true;
else type=false;

if(train!=null){
System.out.println("请耐心等待…………");
if(type){
		System.out.println("除噪声的情况：");
	}else{
		System.out.println("没除噪声的情况：");
	}
PreRead convert = new PreRead();
convert.readData(train, processedTrain,type); //训练样本
convert.readData(test, processedTest100,type); //100个测试样本
testList = convert.readTest(processedTest100);
trainList =convert.readTest(processedTrain);	
Bayes bayes = new Bayes();
for(int i = 0;i < testList.size();i++){
	ArrayList<String> tmp = new ArrayList<String>();
	tmp = testList.get(i);
	String label = tmp.get(tmp.size()-1);
	tmp.remove(tmp.size() - 1);
	finalStr = bayes.predictClass(trainList, tmp);
	
	if(!label.equals(finalStr)){
		wrong_number ++;
	}
//System.out.println(k++);
}
finish=true;
System.out.println("预试错误个数："+ wrong_number+", 测试总个数"+testList.size());
System.out.println("正确率为：" + (DecimalCalculate.sub(1.00000000, DecimalCalculate.div(wrong_number, testList.size())))*100 + "%");

}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
table{
  border:1px  #5F9EA0 solid;
  margin-left: auto; 
  margin-right: auto;
  width:800px
}
.tr1{
 background-color:#E0FFFF;
}
</style>
</head>
<body>
<div class="div2">
<p><b>朴素贝叶斯算法各种情况对比</b></p>
<table id="table1">
<tr class="tr1">
<td>训练集的数目</td>
<td>测试集的数目</td>
<td>是否去噪</td>
<td>预测的错误个数</td>
<td>测试总个数</td>
<td>正确率</td>
</tr>
<%
for(int i=0;i<6;i++){%>

<td>
<%=wrong_number %>
</td>
<% }%>
</tr>
</table>
 </div>
 </body>
</html>
