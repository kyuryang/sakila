<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	//파라미터값 
	int storeId = -1;
	if(request.getParameter("storeId")!=null){
		storeId=Integer.parseInt(request.getParameter("storeId"));
	}

	String customerName ="";
	if(request.getParameter("customerName")!=null){
		customerName = request.getParameter("customerName");
	}
	String beginDate="";
	if( request.getParameter("beginDate").equals("")){
		 beginDate="0";
	} else if( request.getParameter("beginDate")!=null){
		 beginDate = request.getParameter("beginDate");
	}
	String endDate="";
	if(request.getParameter("endDate").equals("")){
		endDate="9999-12-12";
	}else if(request.getParameter("endDate")!=null){
	 endDate = request.getParameter("endDate");
	}
	//System.out.println("가게번호: "+storeId +"  고객이름 : "+customerName+" 시작날 : "+ beginDate);
	int currentPage=1;
	if(request.getParameter("currentPage")!=null){
		currentPage=Integer.parseInt(request.getParameter("currentPage"));
	}
	int beginRow = 0;
	int rowPerPage = 10;
	beginRow= (currentPage-1) * rowPerPage;	
	
	//검색결과
	RentalDao rentalDao = new RentalDao();
	List<Map<String,Object>> list =rentalDao.selectRentalSearchList(beginRow,rowPerPage,storeId, customerName, beginDate, endDate);
	
	int totalRow=rentalDao.selectRentalTotalList(storeId, customerName, beginDate, endDate);
	System.out.println(totalRow);
	int lastPage= totalRow/rowPerPage+1;							//마지막페이지 설정
	if((totalRow%rowPerPage)==0 && totalRow !=0){									//마지막페이지가 0으로 떨어졌으면 -1
		lastPage-=1;
	}

	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>검색결과리스트</h1>
	<table border="1">
		<thead>
			<tr>
				<th>rentalId</th>
				<th>storeId</th>
				<th>inventoryId</th>
				<th>customerId</th>
				<th>staffId</th>
				<th>cName</th>
				<th>filmId</th>
				<th>title</th>
				<th>rentalDate</th>
				<th>returnDate</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(Map<String,Object> m : list){
			%>		<tr>
					<td><%=m.get("rentalId") %></td>
					<td><%=m.get("storeId") %></td>
					<td><%=m.get("inventoryId") %></td>
					<td><%=m.get("customerId") %></td>
					<td><%=m.get("staffId") %></td>
					<td><%=m.get("cName") %></td>
					<td><%=m.get("filmId") %></td>
					<td><%=m.get("title") %></td>
					<td><%=m.get("rentalDate") %></td>
					<td><%=m.get("returnDate") %></td>
					</tr>
			<%
				}
			%>
		</tbody>
	</table>
		<% if(currentPage>1){ %>				<!--  현재페이지가 1보다크면 이전페이지 출력 -->
	<div><a href="<%=request.getContextPath() %>/rentalSearchAction.jsp?currentPage=<%=currentPage-1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>"> 이전</a></div>
	<% }%>
	<% if(currentPage<lastPage){ %>				<!-- 마지막페이지보다 작으면 다음페이지 출력 -->
	<div><a href="<%=request.getContextPath() %>/rentalSearchAction.jsp?currentPage=<%=currentPage+1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>"> 다음</a></div> 
	<% }%>
</body>
</html>