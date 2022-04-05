<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>INDEX</h1>
	<ol>
		<li><a href="<%=request.getContextPath()%>/storeList.jsp">storeList</a></li>
		<li><a href="<%=request.getContextPath()%>/staffList.jsp">staffList</a></li>
		<!-- view 7개 리스트 -->
		<li><a href="<%=request.getContextPath()%>/actorInfoList.jsp">actorInfoList(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/customerList.jsp">customerList(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/filmList.jsp">filmList(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/nicerButSlowerFilmList.jsp">nicerButSlowerFilmList(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/salesByFilmCategory.jsp">salesByFilmCategory(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/salesByStore.jsp">salesByStore(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/staffListView.jsp">staffList(view)</a></li>
		<li><a href="<%=request.getContextPath()%>/filmInStock.jsp">filmInStock(procedure)</a></li>
	<li><a href="<%=request.getContextPath()%>/filmNotInStock.jsp">filmNotInStock(procedure)</a></li>
		<li><a href="<%=request.getContextPath()%>/rewardsReport.jsp">rewardsReport(procedure)</a></li>
	</ol>

</body>
</html>