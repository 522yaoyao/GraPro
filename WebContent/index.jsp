<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>首页</title>
</head>
<body>
请选择分类算法：<br>
<a href="<%=request.getContextPath()%>/bayes.jsp">朴素贝叶斯分类算法</a><br>
<a href="<%=request.getContextPath()%>/dstree.jsp">决策时算法</a>
</body>
</html>