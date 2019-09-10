<%@page import="java.util.List"%>
<%@page import="com.java.web.LoginBean"%>
<%@page import="com.java.web.ListBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
    <meta charset="UTF-8" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <title>파일 업로드</title>
	<script>

	function checkEvent(index){
		// 체크박스 목록 가져오기
		var check = document.getElementsByTag("tbody tr");
	}
	
	 /* --------------------------------------------------------------------------- */
    
		function write(){
			var i = 0;
			if(i =1){
				System.out.println("로그인이 안될시 글쓰기로 안넘어가게 하는 예외처리");
			}
			location.href = "D:\\IDE\\workspace\\20190906\\src\\main\\webapp\\WEB-INF\\views\\wirte.jsp";
		}
	</script>
    </head>
    
    <body>
    <header>
        <h1>Hellow Community World</h1>
    </header>
    <section class="t1">
        <div class="button">
            <p style="color: white;">사용자 : </p>
            <a href="/write"><button type="button">글 작성</button></a>
        </div>
        <div class="t2">
            <table>
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>한줄평</th>
                        <th>작성자</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    List<ListBean> list = (List<ListBean>) request.getAttribute("list");
                    System.out.println(list);
						if(list == null){
							System.out.println("없다");
						} else {
							for(int i = 0; i < list.size(); i++){
					%>
								<tr>
							      <%-- <td><input type="checkbox" onclick="checkEvent(<%=i %>)" name="checkbox"> </td> --%>
							      <td><%=list.get(i).getNo() %></td>
							      <td><%=list.get(i).getTxt() %></td>
							      <td></td>
							    </tr>
					<%
							}
						}
					%>
				</tbody>
            </table>
        </div>
    </section>
    <footer>
        <h3>Gudi Academy</h3>
    </footer>
</body>
</html>