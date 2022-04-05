<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="dao.*" %>
<%@ page import ="java.util.*" %>
<%
	FilmDao fd = new FilmDao();
	Map<String,Object> map =new HashMap<>();									//
	
	int filmId = 0;
	if(request.getParameter("filmId")!=null){
	filmId= Integer.parseInt(request.getParameter("filmId"));					//filmId 만약 링크를 따라왔을때  파마리터값을filmId에 저장한다.
	System.out.println(filmId + "filmId 디버깅");
	}
	int storeId = 0;
	if(request.getParameter("storeId")!=null){
	storeId= Integer.parseInt(request.getParameter("storeId"));					//storeId 만약 링크를 따라왔을때  파마리터값을 storeId에 저장한다.
	System.out.println(storeId + "storeId 디버깅");
	}
	map=fd.filmStockCall(filmId, storeId);
	List<Integer> list = (List<Integer>)(map.get("list"));						//map에 filmStockCall 함수 호출하여 결과값 저장  map의 "list"는 list<Integer> list에 저장 "count"는 int형 count에 저장
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
		<div><input type="number" name="storeId"></div>
		<div>영화번호</div>
		<div><input type="number" name="filmId"></div>
		<button type ="submit">제출</button>
		<h3><div><%=storeId %>지점  영화번호:<%=filmId%>가 현재 가게에 있는 개수 : <%=count %></div></h3>
		<table>
			<thead>
				<th>현재 있는 filmId</th>
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