<%@page import="java.util.List"%>
<%@page import="com.java.web.LoginBean"%>
<%@page import="com.java.web.ListBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
	    <meta charset="UTF-8" content="width=device-width, initial-scale=1">
	    <link rel="stylesheet" type="text/css" href="/resources/css/main.css">
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	    <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"> -->
	    <title>게시판</title>
		<script>
		$(document).ready(function(){
				console.log("tes1");
				console.log("<%=session.getAttribute("nickname")%>");
				var nickname = "<%=session.getAttribute("nickname")%>";
				console.log(nickname);
				var input = document.getElementsByTagName("input");
				var a = document.getElementsByTagName("a");
				if(nickname == "null" | nickname == ""){
					console.log("tes2");	
					$("button").prop("disabled", true);
				
				} else{
					console.log("tes3");
					$("button").prop("disabled", false);
					
				}
				console.log("tes4");
			});
		    
			
			
			function date(){
				 var d = new Date();
			     var currentDate = d.getFullYear() + "년 " + ( d.getMonth() + 1 ) + "월 " + d.getDate() + "일";
			     document.getElementById("date").innerHTML = currentDate;
			}
			
			
		</script>
    </head>
    
    <body>
    <header>
        <h1>Hellow Community World</h1>
    </header>
    <section class="t1">
	    
	    <div class="padding10">
	    	<p >사용자 : <%=session.getAttribute("nickname")%> </p>
	    	
	    	<label id="date"></label>
	    </div>
	    <div class="padding10 width45 log">
	    	<form action="">
	    		<input style="margin-right: 10px" type="submit" formaction="/loginkakao" value="로그인">
	    		<input type="submit" formaction="/logout" value="로그아웃">
	    	</form>
           	
	    </div>
        <div class="padding10 width45 write" id="write">
            <a href="/write" style="float: right;"><button type="button">글 작성</button></a>
        </div>
        
        <div class="t2">
            <table>
                <thead>
                    <tr>
                        <th>번  호</th>
                        <th>제  목</th>
                        <th>내  용</th>
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
							      <!-- <td><input type="checkbox" onclick="checkEvent()" name="checkbox"> </td> -->
							      <td style="text-align: center;"  id="number"><a href="/?abc=<%=list.get(i).getNo()%>"><%=list.get(i).getNo()%></a></td>
							      <td><%=list.get(i).getTitle() %></td>
							      <td><%=list.get(i).getTxt() %></td>
							      <td style="text-align: center;"><%=list.get(i).getNickname()%></td>
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