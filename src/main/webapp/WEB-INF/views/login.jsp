<%@page import="com.java.web.LoginBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#logout{
		
	}
</style>
<script>
var session=<%=session.getAttribute("loginSuccess")%>;
console.log("session",<%=session.getAttribute("loginSuccess")%>);
var loginResult=<%=request.getAttribute("login")%>;
var loginSuccess=<%=request.getAttribute("loginSuccess")%>;
console.log(JSON.stringify(loginResult),loginSuccess);
if(JSON.stringify(loginResult)=="{}"){
	console.log(loginResult);
}else{
	console.log(loginResult);
}
/* function login(){
	document.getElementById("login").style.display="none";
	document.getElementById("logout").style.display="block";
}
function logout(){
	document.getElementById("logout").style.display="none";
	document.getElementById("login").style.display="block";
} */
<%LoginBean user= (LoginBean)request.getAttribute("result");%>
</script>
</head>
<body>
<script>
function login(){
	document.getElementById("login").style.display="none";
	document.getElementById("logout").style.display="block";
}
function logout(){
	document.getElementById("logout").style.display="none";
	document.getElementById("login").style.display="block";
}
</script>

	<h1>카카오 로그인</h1>
	<form>
	<button type="submit" id="login" name="login" formaction="/login">로그인</button>
	<input type="submit" id="logout" name="logout" formaction="https://developers.kakao.com/logout" value="로그아웃" onclick="logout()"></button>
	</form>
	<%if(user!=null) {%>
	<p><%=user.getId()%></p>
	<p><%=user.getNickname()%></p>
	<p><%=user.getProfile_image()%></p>
	<p><%=user.getThumbnail_image()%></p>
	<%} %>
</body>
</html>