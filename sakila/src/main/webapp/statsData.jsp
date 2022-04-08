<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dao.*" %>    
    
<%
	StatsDataDao statsDataDao = new StatsDataDao();
	List<Map<String,Object>> amountByCustomer = statsDataDao.amountByCoustomer();					//1. customer별 총 amount
	List<Map<String,Object>> filmCountByRentalRate = statsDataDao.filmCountByRentalRate();			//2. rental_rate별 영화 개수
	List<Map<String,Object>> filmCountByRating =statsDataDao.filmCountByRating();					//3. rating별 영화 개수
	List<Map<String,Object>> filmCountByLanguage =statsDataDao.filmCountByLanguage();				//4. language별 영화 개수
	List<Map<String,Object>> filmCountByLength =statsDataDao.filmCountByLength();					//9. length별 영화 개수 (구간, 기준을 정해서)
	List<Map<String,Object>> filmCountByActor =statsDataDao.filmCountByActor();						//#6. actor별 영화 출연 횟수	
																								
	List<Map<String,Object>> filmCountByStore =statsDataDao.filmCountByStore();						//##7 store별 영화 소지 개수
	List<Map<String,Object>> rentalCountByStaff =statsDataDao.rentalCountByStaff();					//#staff별 고객에게 rental 횟수
	List<Map<String,Object>> rentalCountingByCustomer =statsDataDao.rentalCountingByCustomer();	//고객별 빌린 횟수
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>amountByCustomer</h1>
	<table>
		<thead>
			<tr>
				<th>customerId</th>
				<th>name</th>
				<th>total</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : amountByCustomer){%>
				<tr>
					<td><%=m.get("customerId")%></td>
					<td><%=m.get("name")%></td>
					<td><%=m.get("total")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
	<h1>filmCountByRentalRate</h1>
	<table>
		<thead>
			<tr>
				<th>rentalRate</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : filmCountByRentalRate){%>
				<tr>
					<td><%=m.get("rentalRate")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
	<h1>filmCountByRating</h1>
	<table>
		<thead>
			<tr>
				<th>rating</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : filmCountByRating){%>
				<tr>
					<td><%=m.get("rating")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
	<h1>language별 영화 갯수</h1>
	<table>
		<thead>
			<tr>
				<th>name</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : filmCountByLanguage){%>
				<tr>
					<td><%=m.get("name")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
		<h1>length별 영화 갯수</h1>
	<table>
		<thead>
			<tr>
				<th>length</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : filmCountByLength){%>
				<tr>
					<td><%=m.get("length")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
			<h1>고객별 빌린 횟수</h1>
	<table>
		<thead>
			<tr>
				<th>customerId</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : rentalCountingByCustomer){%>
				<tr>
					<td><%=m.get("customerId")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
				<h1>actor별 영화 출연 횟수</h1>
	<table>
		<thead>
			<tr>
				<th>customerId</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : filmCountByActor){%>
				<tr>
					<td><%=m.get("actorId")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
		
	</table>
	<h1>store별 영화 소지 개수</h1>
		<table>
		<thead>
			<tr>
				<th>customerId</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : filmCountByStore){%>
				<tr>
					<td><%=m.get("storeId")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
		<h1>staff별 고객에게 rental 횟수</h1>
		<table>
		<thead>
			<tr>
				<th>customerId</th>
				<th>count</th>
			</tr>
		</thead>
		<tbody>
			<% for(Map<String,Object> m : rentalCountByStaff){%>
				<tr>
					<td><%=m.get("staffId")%></td>
					<td><%=m.get("count")%></td>
				</tr>	
			<%} %>
		</tbody>
	</table>
</body>
</html>