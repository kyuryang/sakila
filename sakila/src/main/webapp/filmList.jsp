<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%@ page import="vo.*" %>
<%
	int currentPage=1;	//현재 페이지
	int beginRow=0;		//해당 화면의 첫번째 데이터 행
	int rowPerPage=10;	//페이지당 데이터 행 수
	if(request.getParameter("currentPage")!=null){									//파라미터 값을 받은게 null이 아니면 
		currentPage=Integer.parseInt(request.getParameter("currentPage"));			//beginRow에 정수형 변환 후 파라미터값 저장
		
	}
	beginRow=(currentPage-1) *rowPerPage;											//첫페이지의 데이터 첫 행 알고리즘 1 -> 0~9 2->10~19
	//System.out.println(currentPage);											
	
	

	
	
	List<FilmInfo> list = new ArrayList<>();									//FilmDao형 List 생성
	FilmDao fd = new FilmDao();													//FilmDao 객체 생성
	list=fd.selectFilmListByPage(beginRow,rowPerPage);							//위의 beginRow와 rowPerPage를 받는 페이징 메서드 호출 후 결과 값을 list에 저장
	int count =fd.filmTotalRow();
	int lastPage = (count/rowPerPage) +1; 										//마지막 페이지는 총 데이터에서 페이지당 개수를 나눠주고 +1
	if(count%rowPerPage==0){													//만약 개수가 0으로 나누어 떨어지면 -1
		lastPage--;
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>film_list view</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>FID</th>
				<th>title</th>
				<th>description</th>
				<th>category</th>
				<th>price</th>
				<th>length</th>
				<th>rating</th>
				<th>actors</th>
			</tr>
		</thead>
		<tbody>
			<%for(FilmInfo f : list) {%>
			<tr>
			<td><%=f.getFID() %></td>
			<td><%=f.getTitle() %></td>
			<td><%=f.getDescription() %></td>
			<td><%=f.getCategory() %></td>
			<td><%=f.getPrice() %></td>
			<td><%=f.getLength() %></td>
			<td><%=f.getRating() %></td>
			<td><%=f.getActors() %></td>
			</tr>
			<%} %>
		</tbody>
	</table>																	<!-- 페이징 버튼 형식으로 처리  --> 
	<form method="post" action="<%=request.getContextPath() %>/filmList.jsp">	<!-- post방식 action발생 시 filmList로 이동 -->
	<% if(currentPage>1){%>														<!--  현재 페이지가 2이상이면 이전 버튼 생긴다 -->
		<button type = "submit" value ="<%=currentPage-1%>" name = "currentPage" >이전</button>		>	<!-- 이전button누를시 currentPage에서 -1값을 받음 -->
	<%
		} if(currentPage<lastPage){%>															<!-- lastPage보다 작으면 다음버튼이 생긴다. -->
		<button type = "submit" value ="<%=currentPage+1%>" name = "currentPage" >다음</button><!-- 다음button 누를 시 currentPage에서 +1값을 받음 -->
		
	<%
		}
	%>
	 </form>
</body>
</html>