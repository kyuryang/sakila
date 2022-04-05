<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="dao.*" %>
<%@ page import ="java.util.*" %>
<%
	CustomerDao cDao = new CustomerDao();
	
	int purchase = 0;
	if(request.getParameter("purchase")!=null){
		purchase=Integer.parseInt(request.getParameter("purchase"));
	}
	double amount = 0.0;
	if(request.getParameter("amount")!=null){
		amount=Double.parseDouble(request.getParameter("amount"));
	}
	List<Map<String,Object>> list = cDao.rewardsReport(purchase, amount);
	
	

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이달의 상위 고객 뽑기</title>
</head>
<body>
	<h1>이달의 상위 고객 뽑기</h1>
	<form method="post" action="<%=request.getContextPath() %>/rewardsReport.jsp">
		<div>최소 구매 횟수</div>
		<div><input type="text" name="purchase"></div>
		<div>최소 구매 가격</div>
		<div><input type="text" name="amount"></div>
		<button type ="submit">제출</button>
		<h3>한달 구매 횟수:<%=purchase %> 이상  한달 구매 가격:<%=amount%>이상  </h3>
		
		<table border="1">
			<thead>
				<tr>
					<th>customerId</th>
					<th>store_id</th>
					<th>first_name</th>
					<th>last_name</th>
					<th>email</th>
					<th>address_id</th>
					<th>active</th>
					<th>create_date</th>
					<th>last_update</th>
				</thead>
			<tbody>
				<%for(int i =0; i<list.size(); i=i+1){ %>
					<tr>
						<td><%=list.get(i).get("customerId")%></td>
						<td><%=list.get(i).get("storeId")%></td>
						<td><%=list.get(i).get("firstName")%></td>
						<td><%=list.get(i).get("lastName")%></td>
						<td><%=list.get(i).get("email")%></td>
						<td><%=list.get(i).get("addressId")%></td>
						<td><%=list.get(i).get("active")%></td>
						<td><%=list.get(i).get("createDate")%></td>
						<td><%=list.get(i).get("lastUpdate")%></td>
					</tr>
				<%} %>
			</tbody>
		</table>
	</form>
</body>
</html>