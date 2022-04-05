<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="dao.*" %>
<%@ page import ="java.util.*" %>
<%
	FilmDao fd = new FilmDao();
	Map<String,Object> map =new HashMap<>();
	
	int filmId = 0;
	if(request.getParameter("filmId")!=null){
	filmId= Integer.parseInt(request.getParameter("filmId"));
	System.out.println(filmId + "filmId 디버깅");
	}
	int storeId = 0;
	if(request.getParameter("storeId")!=null){
	storeId= Integer.parseInt(request.getParameter("storeId"));
	System.out.println(storeId + "storeId 디버깅");
	}
	map=fd.filmStockCall(filmId, storeId);
	List<Integer> list = (List<Integer>)(map.get("list"));
	int count =0;
	count = (Integer)(map.get("count"));




%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>영화 재고</title>
</head>
<body>
	<form method="post" action="<%=request.getContextPath() %>/filmInStock.jsp">
		<div>가게Id</div>
		<div><input type="number" name="filmId"></div>
		<div>영화번호</div>
		<div><input type="number" name="storeId"></div>
		<button type ="submit">제출</button>
		<h3><div><%=storeId %>지점 재고: <%=count %></div></h3>
		<table>
			<thead>
				<th>영화번호</th>
			</thead>
			<tbody>
				<%for(int i : list) { %>
					<tr>
						<td><%=i %></td>
					</tr>
				<%} %>
			</tbody>
		</table>
	</form>
</body>
</html>