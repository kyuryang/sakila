<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="dao.*" %>
<%@ page import ="java.util.*" %>
<%
	FilmDao fd = new FilmDao();
	Map<String,Object> map =new HashMap<>();
	
	int filmId = 0;
	if(request.getParameter("filmId")!=null){
	filmId= Integer.parseInt(request.getParameter("filmId"));					//링크를 따라 들어왔을시 파라미터 값이 null이 아니면 filmId에 정수형으로 변환 후 값을 넣어준다
	System.out.println(filmId + "filmId 디버깅");
	}
	int storeId = 0;
	if(request.getParameter("storeId")!=null){
	storeId= Integer.parseInt(request.getParameter("storeId"));					//링크를 따라 들어왔을시 파라미터 값이 null이 아니면 storeId에 정수형으로 변환 후 값을 넣어준다
	System.out.println(storeId + "storeId 디버깅");
	}
	map=fd.filmNotStockCall(filmId, storeId);									//filmNotStockCall 가게에 재고가 없는것을 출력해주는 프로시저를 함수화하여 호출 후 값을 map에 저장
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
	<form method="post" action="<%=request.getContextPath() %>/filmNotInStock.jsp">
		<div>가게Id</div>
		<div><input type="number" name="storeId"></div>
		<div>영화번호</div>
		<div><input type="number" name="filmId"></div>
		<button type ="submit">제출</button>
		<h3><div><%=storeId %>지점  영화번호:<%=filmId%>가 현재 가게에 없는 개수 : <%=count %></div></h3>
		<table>
			<thead>
				<th>현재 없는 film</th>
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