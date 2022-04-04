<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>


<%
	//페이징
	int currentPage =1; //현재 페이지( 첫페이지는 1로 초기화)
	if(request.getParameter("currentPage")!=null){
		currentPage=Integer.parseInt(request.getParameter("currentPage"));			//링크를 통해 들어온 파라미터값이 있으면 그 값을 받는다.
	}
	System.out.println(currentPage+" 현재페이지 디버깅");
	int rowPerPage = 10;	//페이지당 나열할 데이터 수
	int beginRow = (currentPage-1) * rowPerPage;	//페이지의 첫데이터 행 

	//CustomerDao 호출
	List<Customer> list = new ArrayList<Customer>();	//다형성
	CustomerDao customerDao = new CustomerDao();			//customerDao 객체 생성
	list=customerDao.selectCustomerListByPage(beginRow,rowPerPage);			//selectCustomerListByPage() 호출하여 list에 리턴값 저장
	int totalRow=customerDao.selectTotalRow();								//데이터의 총 개수 저장
	int lastPage = (int)(Math.ceil((double)totalRow/(double)rowPerPage));	//총개수에 페이지당 나열수를 나누고 그값을 올림하여서 마지막페이지 생성
																			//0으로 떨어질때에만 나눠진 값 아닐 시 +1
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>customerList</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>CustomerId</th>
				<th>customerName</th>
				<th>customerAddress</th>
				<th>customerZipcode</th>
				<th>customerPhone</th>
				<th>customerCountry</th>
				<th>customerNotes</th>
				<th>customerSID</th>
			</tr>
		</thead>
		<tbody>
			<%for(Customer c : list){ %>
				<tr>
					<td><%=c.getCustomerId() %></td>
					<td><%=c.getCustomerName() %></td>
					<td><%=c.getCustomerAddress() %></td>
					<td><%=c.getCustomerZipcode() %></td>
					<td><%=c.getCustomerPhone() %></td>
					<td><%=c.getCustomerCountry() %></td>
					<td><%=c.getCustomerNotes() %></td>
					<td><%=c.getCustomerSID()%></td>
				</tr>
			<%} %>
		</tbody>
	</table>
	<div>
	<!-- 페이징  a태그로 받기-->
	<% if(currentPage>1){	//현재 페이지가 1보다 크면(적어도 2페이지 이상일 떄 ) 이전 페이지 태그 생긴다.  %> 
		<a href="<%=request.getContextPath() %>/customerList.jsp?currentPage=<%=currentPage-1%>">이전</a>
		
	<% 										  
	} 
	%>
	<%if (currentPage<lastPage){ %>		<!-- 현재페이지가 마지막페이지보다 작을때만 다음 페이지 태그가 생긴다 -->
		<a href="<%=request.getContextPath()%>/customerList.jsp?currentPage=<%=currentPage+1 %>">다음</a>
	<%
	}
	%>
	</div>
</body>
</html>