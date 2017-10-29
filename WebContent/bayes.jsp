<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@page import="ly.tool.DecimalCalculate"%>
<%@page import="ly.tool.Rsult" %>   
<%@page import="ly.datamining.Bayes" %>
<%@page import="ly.datamining.PreRead" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.BufferedWriter"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.IOException"%>

<%

ArrayList<ArrayList<String>> trainList=null ;
ArrayList<ArrayList<String>> testList=null;
ArrayList<String> tmpList=new ArrayList<String>();//存储中间结果

ArrayList<ArrayList<String>> rsList=new ArrayList<ArrayList<String>>();//存储运算结果
//rsList=Rsult.returnRs();
ArrayList<Double> curr=new ArrayList<Double>();//存放正确率
ArrayList<String> list2=new ArrayList<String>();

boolean type = false; //是否除干扰，如果是除干扰，在读入原始数据时将带有“?”的数据忽略

String train=request.getParameter("train");
String sound=request.getParameter("sound");
String test=request.getParameter("test");

String processedTrain = "D://datamining//process//adultResult.txt";
String processedTest = "D://datamining//process//adultTestResult.txt";
String tmpFile="D://datamining//tmp.txt";//存储中间结果的文件

FileWriter writer=null;
BufferedWriter bWriter=null;

FileReader reader=null;
BufferedReader in=null;

String finalStr = "";
String trainNum="";//训练集的数目
String testNum="";//测试集的数目
int wrong_number = 0; //记录错误的数量
String finalData="";//正确率
boolean flag = true; //标志测试的数量
boolean finish=false;//算法是否完成
if("yes".equals(sound)) type=true;
else type=false;

if(train!=null){
// System.out.println("请耐心等待…………");
// if(type){
// 		System.out.println("除噪声的情况：");
// 	}else{
// 		System.out.println("没除噪声的情况：");
// 	}
PreRead convert = new PreRead();
convert.readData(train, processedTrain,type); //训练样本
convert.readData(test, processedTest,type); //测试样本
testList = convert.readTest(processedTest);
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
//存入数据集条数
if(train.contains("32561")){
	trainNum="32561";
	tmpList.add(trainNum);
}else if(train.contains("20000")){
	trainNum="20000";
	tmpList.add(trainNum);
	
}else if(train.contains("7500")){
	trainNum="7500";
	tmpList.add(trainNum);
}else if(train.contains("5000")){
	trainNum="5000";
	tmpList.add(trainNum);
}else if(train.contains("10000")){
	trainNum="10000";
	tmpList.add(trainNum);
}else if(train.contains("2000")){
	trainNum="2000";
	tmpList.add(trainNum);
}
if(test.contains("1000"))  testNum="1000";
else testNum="100";
tmpList.add(testNum);//测试集数目
tmpList.add(sound);//是否除噪
tmpList.add(String.valueOf(wrong_number));//预测的错误个数
tmpList.add(String.valueOf(testList.size()));//测试总个数
finalData= (DecimalCalculate.sub(1.00000000, DecimalCalculate.div(wrong_number, testList.size())))*100 + "%";
tmpList.add(finalData);//正确率
finish=true;
if(finish){//将实验结果写入文件
	try{
	    writer = new FileWriter(new File(tmpFile),true);//每次都是从文件的末尾写入,而不是覆盖
		bWriter = new BufferedWriter(writer);
		String line = null;
		for(int i=0;i<tmpList.size();i++){//每次读取一个文本行
			bWriter.write(tmpList.get(i));
		    bWriter.write(",");
			}
		bWriter.newLine();//写入一个行分隔符
		}catch(Exception e){//抛出UnsupportedEncodingException、ileNotFoundException
		System.out.println("读取文件出错");
		e.printStackTrace();
	}finally{
		try {
			bWriter.flush();
			bWriter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
//System.out.println("预试错误个数："+ wrong_number+", 测试总个数"+testList.size());
//System.out.println("正确率为：" + (DecimalCalculate.sub(1.00000000, DecimalCalculate.div(wrong_number, testList.size())))*100 + "%");
}
%>
<%if(finish){
//     ArrayList<ArrayList<String>> rsList=new ArrayList<ArrayList<String>>();//存储运算结果
     rsList=Rsult.returnRs(tmpFile);
//     ArrayList<Double> curr=new ArrayList<Double>();//存放正确率
//     ArrayList<String> list2=new ArrayList<String>();
   //curr.add(1);
   //curr.add(2);
   for(int i=0;i<rsList.size();i++){
	   String value=rsList.get(i).get(5);//将正确率取出
	   String handle="";
	   double result=0.0;
	   if(5==value.length()){ 
		   handle=value.substring(0, 4)+"0";
		   list2.add(handle+"%");
	   }else{
		   handle=value.substring(0, 5);
		   list2.add(handle+"%");
	   }
	   result=(Double.parseDouble(handle)-67.00)*13;//正确率差距较小故作放大处理
	   curr.add(result);
	  // System.out.println(result);
	  
   }
}
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>展示界面(朴素贝叶斯)</title>
<style type="text/css">
.div1{
 height:250px;
}
.div2{
  text-align:center;
 }
 .center{
 padding-left:10px;
  border:1px  black solid;
  margin:0 auto;
  background-color:#F5F5F5;
  height:100%;
  width:300px;
} 
.train{
  text-align:left;
}
.bottom{
 text-align:right;
 padding :0px 10px 0px 0px;
}
table{
  float:left;
  border:1px  #5F9EA0 solid;
  font-size:15px;
  margin-left: 10px;
  width:600px
}
.tr1{
 background-color:#E0FFFF;
}
#di{
  border-left:1px solid skyblue;
  border-bottom:1px solid skyblue;
  width:600px;
  height:200px;
 position:relative;
 margin-left:100px;
  float:left;
 }
 li{
    font-size:10px;
   float:left;
   position:absolute;
   bottom:0px;
   background-color:salmon;
   text-align:center;
   font-weight:bold;
   color:black;
   display:none;
   background-color:#5F9EA0;
   width:30px;
   list-style:none;
   }
  #li0{
    left:0px;
    }
    #li1{
    left:50px;
   }
    #li2{
    left:100px;
    }
    #li3{
    left:150px;
    }
    #li4{
    left:200px;
   }
    #li5{
    left:250px;
    }
    #li6{
    left:300px;
    }
    #li7{
    left:350px;
    }
    #li8{
    left:400px;
   }
    #li9{
    left:450px;
    }
    #li10{
    left:500px;
    }
    #li11{
    left:550px;
   }
    #li12{
    left:600px;
    }
    #li13{
    left:650px;
    }
    #li14{
    left:700px;
    }
    #show{
    display:none;
    }
</style>
</head>
<body>
<a href="<%=request.getContextPath()%>/index.jsp">返回</a><br>
<form action="bayes.jsp" method="post"  > 
<div class="div1" >
<div class="center">
<div class="first" id="first">
<p align="center">请选择合适的数据集：</p>
<input type="radio" name="train" value="D://datamining//file//adult2000.data">训练集2000条<br>
<input type="radio" name="train" value="D://datamining//file//adult5000.data">训练集5000条<br>
<input type="radio" name="train" value="D://datamining//file//adult7500.data">训练集7500条<br>
<input type="radio" name="train" value="D://datamining//file//adult10000.data">训练集10000条<br>
<input type="radio" name="train" value="D://datamining//file//adult20000.data">训练集20000条<br>
<input type="radio" name="train"  value="D://datamining//file//adult32561.data">训练集38000条<br>
<div class="bottom">
<input type="button"  value="下一步" onclick="first()">
</div>
</div>
<div class="second"  id="second"style="display:none;">
<p align="center">是否去噪声</p>
<input type="radio" name="sound"  value="yes">去除噪声<br>
<input type="radio" name="sound" value="no" checked="checked">不去除噪声<br>
<p></p>
<br>
<div class="bottom">
<input type="button" value="上一步" onclick="first2()">
<input type="button"  value="下一步"  onclick="second()">
</div>
</div>
<div class="third"  id="third" style="display:none;">
<p align="center">请选择合适的测试集</p>
<input type="radio" name="test"  value="D://datamining//file//adult100.test"  checked="checked">测试集100条<br>
<input type="radio" name="test" value="D://datamining//file//adult1000.test">测试集1000条<br>
<p></p>
<br>
<div class="bottom">
<input type="button" value="上一步" onclick="second2()">
<input type="submit"  value="完成"  onclick="submit1()">
</div>
</div>
</div>
</div>
</form>
<!-- <iframe style="width:0; height:0; margin-top:-10px;" name="submitFrame" src="about:blank"></iframe> -->
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
<%if(finish){ //将实验结果从文件读出
	//ArrayList<String> list=null;
	try{
		 reader=new FileReader(new File(tmpFile));
		 in=new BufferedReader(reader);
		 String line=null;
		 while((line=in.readLine())!=null){//每次读取一个文本行
			// list=new ArrayList<String>();
			 String[] arr=line.split(",");%>
			 <tr>
			 <% 
			 for(int i=0;i<arr.length;i++){
			 
			 %>
		<td><%= arr[i]%></td>
		<% 	}%>
		</tr>
	<%	
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



 } %>
</table>
<div  id="di">
<h3>柱状图（正确率）</h3>
<input type="button" id="show" value="生成柱状图" onclick="show()">
<ul id="list">
        
       <%
         
    	 for(int i=0;i<rsList.size();i++){
    	 ArrayList<String> list=rsList.get(i);//获取一条结果数据
    	 %>
    	 <li id="li<%=i %>"><%=list2.get(i)%></li>
    	 <% 
     }
//      out.println("<script type='text/javascript'>window.onload=function(){");
    for(int i=0;i<curr.size();i++){
        //System.out.println("经过");
     	out.println("<script type='text/javascript'>");
    	out.println("var elem=document.getElementById('li"+i+"');"); 
       out.println("elem.style.height='"+(new Double(curr.get(i)).intValue())+"px'");
    //	System.out.println("elem.style.height='"+(new Double(curr.get(i)).intValue())+"px'");
    	// out.println("alert(elem)");
     	 out.println("elem.style.display='block'");
    	  out.println("</script>");
      }

       
 %>
       
       </ul>
       
</div>
</div>
 </body>
 </html>
 <script type="text/javascript">
 window.onload=function(){
	 
 }
 function finish(){
	 
 }
 function first(){
	 var fir=document.getElementById("first");
	 var sec=document.getElementById("second");
	 var thi=document.getElementById("third");
	 fir.style.display="none";
	 sec.style.display="block";
	 thi.style.display="none";
	  }
 function first2(){
	 var fir=document.getElementById("first");
	 var sec=document.getElementById("second");
	 var thi=document.getElementById("third");
	 fir.style.display="block";
	 sec.style.display="none";
	 thi.style.display="none";
 }
 function second(){
	 var fir=document.getElementById("first");
	 var sec=document.getElementById("second");
	 var thi=document.getElementById("third");
	 fir.style.display="none";
	 sec.style.display="none";
	 thi.style.display="block";
 }
 function second2(){
	var fir=document.getElementById("first");
	 var sec=document.getElementById("second");
	 var thi=document.getElementById("third");
	 fir.style.display="none";
	 sec.style.display="block";
	 thi.style.display="none";
 }
 
 function submit1(){
	 //show();
	 var flag = false;
	 var allRadio = document.getElementsByName('train');
	 for(var i = 0;i < allRadio.length;i++){
	    if(allRadio[i].checked){
	     flag = true;
	    }
	 }
	 if (!flag){
	  alert("请选择相应的训练集!")
	 }
	     var fir=document.getElementById("first");
		 var sec=document.getElementById("second");
		 var thi=document.getElementById("third");
		 fir.style.display="block";
		 sec.style.display="none";
		 thi.style.display="none";
	 

	 ////////////////////////////////////////////////
	 
// 	 for(var i=0;i<objTable.rows.length;i++)
// 	 {
	 
// 	   var objTD=     objTable.rows[i].insertCell(0);
// 	   objTD.innerHTML=i;
	     
// 	 }
	
 }
 function show(){
	// alert("haha");
	 <%for(int i=0;i<curr.size();i++){%>
	 var elem=document.getElementById("li"+<%=i%>);
	//	alert(elem);
		elem.style.height=<%=curr.get(i)%>+"px";
	    elem.style.display="block";
		<%}%>
	var e=document.getElementById("di");
	
//e.style.border-bottom="1px #5F9EA0 solid ";
 }
</script>
