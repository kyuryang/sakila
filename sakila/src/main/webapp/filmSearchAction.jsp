<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<%@ page import = "dao.*"%>
<%@ page import = "vo.*"%>
<%
	String category = request.getParameter("category");
	String rating = request.getParameter("rating");
	double price = -1; // price 데이터가 입력되지 않았을때
	if(!request.getParameter("price").equals("")) {
		price = Double.parseDouble(request.getParameter("price"));
	}
	int length = -1; // length 데이터가 입력되지 않았을때
	if(!request.getParameter("length").equals("")) {
		length = Integer.parseInt(request.getParameter("length"));
	}
	String title = request.getParameter("title");
	String actors = request.getParameter("actors");
	//페이징
	int currentPage=1;
	if(request.getParameter("currentPage")!=null){
		currentPage=Integer.parseInt(request.getParameter("currentPage"));
	}
	int beginRow = 0;
	int rowPerPage = 10;
	
	FilmDao filmDao = new FilmDao();
	int totalRow= filmDao.searchTotalRow(category, rating, price, length, title, actors); //검색된 데이터 개수 
	beginRow= (currentPage-1) * rowPerPage;							//페이지당 첫 데이타 행 
	
	List<FilmInfo> list = filmDao.selectFilmListSearch(beginRow ,rowPerPage ,category, rating, price, length, title, actors);
	//System.out.println(list.size()); // 0	
	

	
	int lastPage= totalRow/rowPerPage+1;							//마지막페이지 설정
	if((totalRow%rowPerPage)==0 && totalRow !=0){									//마지막페이지가 0으로 떨어졌으면 -1
		lastPage-=1;
	}
	System.out.println(category+""+price+" "+rating+" "+ length+ " "+ actors+" " + title);
	System.out.println(currentPage+" " + beginRow +"   페이지");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>데이터 개수 : <%=totalRow %></div>
	<table border="1">
		<%
			for(FilmInfo f : list) {
		%>
				<tr>
					<td><%=f.getFID()%></td>
					<td><%=f.getTitle()%></td>
					<td><%=f.getDescription()%></td>
					<td><%=f.getCategory()%></td>
					<td><%=f.getPrice()%></td>
					<td><%=f.getLength()%></td>
					<td><%=f.getRating()%></td>
					<td><%=f.getActors()%></td>
				</tr>
		<%		
			}
		%>
	</table>
	<div></div>																		
	<% if(currentPage>1){ %>				<!--  현재페이지가 1보다크면 이전페이지 출력 -->
			<a href="<%=request.getContextPath() %>/filmSearchAction.jsp?currentPage=<%=currentPage-1%>&price=<%=price%>&length=<%=length%>&category=<%=category%>&rating=<%=rating%>&title=<%=title%>&actors=<%=actors%>"> 이전</a>
	<% }%>
	<% if(currentPage<lastPage){ %>				<!-- 마지막페이지보다 작으면 다음페이지 출력 -->
			<a href="<%=request.getContextPath() %>/filmSearchAction.jsp?currentPage=<%=currentPage+1%>&price=<%=price%>&length=<%=length%>&category=<%=category%>&rating=<%=rating%>&title=<%=title%>&actors=<%=actors%>"> 다음</a>
	<% }%>
	
</body>
</html>