<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="dao.ActorInfoDao" %>
<%@ page import ="vo.ActorInfo" %>
<%@ page import ="java.util.*" %>
<%	



//페이징 
 	int currentPage =1;			//현재 페이지
	if(request.getParameter("currentPage")!=null){				//이전,다음 링크를 통해서 들어왔다면 
		currentPage=Integer.parseInt(request.getParameter("currentPage"));		//현재 페이지 값은 링크를 타서 들어 온값으로
	}

	System.out.println(currentPage);
	int rowPerPage=10;			//페이지당 출력	
	int beginRow = (currentPage-1) * rowPerPage; 
	//int lastRow = beginRow+rowPerPage;		
	
	
	
	//ActorInfoDao 호출
	List<ActorInfo> list = new ArrayList<ActorInfo>();
	ActorInfoDao actorInfoDao = new ActorInfoDao();
	list = actorInfoDao.selectActorInfoListByPage(beginRow,rowPerPage);
	

	//페이징
 	int totalCount = actorInfoDao.selectActorInfoTotalRow();		//db 총 데이터 수
	int lastPage =(int)(Math.ceil((double)totalCount / (double)rowPerPage)); 	//총 페이지 수
	/*
	알고리즘 
	select .... limit 0,10"
	1page -> 0
	2page -> 10
	
	?	<--(currentpage-1) *10

*/

	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ActorInfoList</title>
</head>
<body >
	<h1>ActorInfoList</h1>
	<table border=1>
		<thead>
			<tr>
				<th>ActorId</th>
				<th>firstName</th>
				<th>lastName</th>
				<th>filmInfo</th>
			</tr>
		</thead>
		<tbody>
			
			<%for(ActorInfo a : list){ %>
				<tr>
					<td><%=a.getActorId()%></td>
					<td><%=a.getFirstName()%></td>
					<td><%=a.getLastName()%></td>
					<td><%=a.getFilmInfo()%></td>
				</tr>
			<%} %>
			
		</tbody>
	</table>
	<!-- 페이지 목록 표시 부분 -->
	<div colspan="2">															<!-- 페이징 a태그 -->
		
 		<!-- 페이지가 만약 10페이지였으면 이전을 누르면 9페이지가 되고 다음을 누르면 11페이지 -->
			<%
				if(currentPage>1){		//현재페이지가 1이면 이전페이지가 존재해서는 안된다.
			%>
		<a href="<%=request.getContextPath()%>/actorInfoList.jsp?currentPage=<%=currentPage-1%>">이전</a>	
			<%
				}
			%>
			<!--  마지막 페이지 ? 
				10개			 1
				11,12,13~20  2
				21~30 		 3
				31~40		 4
				
				마지막 페이지 = 전체행 /rowPerPage
			 -->
			
			 <%
			 	//
			 	if(currentPage <lastPage){
			 %>
			 <a href="<%=request.getContextPath()%>/actorInfoList.jsp?currentPage=<%=currentPage+1%>">다음</a> 
			 
			 <% 
			 	}
			 %>
			 </div>
</body>
</html> 