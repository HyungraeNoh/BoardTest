<%@page import="com.java.web.LoginBean"%>
<%@page import="com.java.web.ListBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8" content="width=device-width, initial-scale=1">
    <title>파일 업로드</title>
    <link rel="shortcut icon" type="image/x-icon" href="/resources/img/favicon.png">
    <!-- <link rel="stylesheet" href="/resources/css/file.css"> -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">

	var checkIndex = -1;
	function checkEvent(index){
		// 체크박스 목록 가져오기
		var check = document.getElementsByName("checkbox");			
		
		// 체크박스 전체 해제 처리
		for(var i = 0; i < check.length; i++){
			check[i].checked = false;				
		}
		
		// 체크박스가 다른것이 선택 되었을때 체크 
		if(index != checkIndex) {
			check[index].checked = true;
			var txt = document.getElementsByTagName("tr")[index + 1].getElementsByTagName("td")[2].textContent;
			document.getElementById("text").value = txt;
			document.getElementsByName("index")[0].value = index;
			document.getElementById("update").classList.remove('disabled');
			document.getElementById("delete").classList.remove('disabled');
		} else {
			document.getElementById("text").value = "";
			document.getElementsByName("index")[0].value = "";
			document.getElementById("update").classList.add('disabled');
			document.getElementById("delete").classList.add('disabled');
		}
		
		// 체크박스 On & Off (자기 자신를 두번 눌렀을때 발생)
		if(check[index].checked) checkIndex = index;
		else checkIndex = -1;
	}
	
	 /* --------------------------------------------------------------------------- */
    </script>
   
    <script>
    	var dt = new DataTransfer();
    	function formList(){
    		console.log(dt);
    	}
		function file_Event(obj){
			console.log(obj.files);
			for (var i = 0; i < obj.files.length; i++) {
				var fileName = obj.files[i].name;
				var ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
				console.log(fileName, ext);
				var extLower = ext.toLowerCase();
				var text = "";
				if("txt" == extLower) {
					text = "텍스트";
				} else if("pdf" == extLower || "html" == extLower){
					text = "문서";
				} else if("jpg" == extLower || "jpeg" == extLower || "png" == extLower) {
					text = "이미지";
				} else {
					continue;
				}
				var node = document.createElement("LI");
				var textnode = document.createTextNode(text);
				node.appendChild(textnode);
				node.classList.add("itemContainer");
				document.getElementById("mainContainer").appendChild(node);
				dt.items.add(obj.files[i]);
			}
		}
	</script>
	
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
</head>
<body>
	<h1>카카오 로그인</h1>
	<form>
	<button type="submit" id="login" name="login" formaction="/login">로그인</button>
	<input type="submit" id="logout" name="logout" formaction="https://developers.kakao.com/logout" value="로그아웃" onclick="logout()"></button>
	</form>

	<div>
	<p>파일 업로드</p>
	   <form action="" method="post" enctype="multipart/form-data">
	       <ul id="mainContainer">
	           <li class="subContainer" id="clickMe">
	           	<label for="file">+</label>
	           	<div class="dn">
	               <input  id="file" type="file" name="file" onchange="file_Event(this)" multiple="multiple"/><br>
	           	</div>
	           </li>
	       </ul>
	       <button type="button" onclick="formList()">확인</button>
	       <input type="submit" value="전송">
	   </form>
   </div>
<hr>
<div class="container">
	<p>게시판</p>
	<h1 class="text-center">구디아카데미</h1>
	<form id="edit">
	  <div class="form-group row">
	    <div class="col-xs-2">
	    	<p >한줄평  :</p>
	    </div>
	    <div class="col-xs-7">
	    	<input type="text" class="form-control" id="text" name="txt" placeholder="입력하세요." autocomplete="off">
	    	<input type="hidden" name="index">
	    </div>
	    <div class="col-xs-1">
	    	<button type="submit" class="btn btn-primary" id="submit" formaction="/insert">추가</button>
	    </div>
	    <div class="col-xs-1">
	    	<button type="submit" class="btn btn-success disabled" id="update" formaction="/update">수정</button>
	    </div>
	    <div class="col-xs-1">
	    	<button type="submit" class="btn btn-danger disabled" id="delete" formaction="/delete">삭제</button>
	    </div>
	  </div>
	</form>
</div>
<div class="container">
	<table class="table table-striped">
	  <thead>
	    <tr>
	      <th>선택</th>
	      <th>번호</th>
	      <th>한줄평</th>
	    </tr>
	  </thead>
	  <tbody>
<%
	ListBean[] list = (ListBean[]) request.getAttribute("list");
	if(list == null){
		System.out.println("없다");
	} else {
		for(int i = 0; i < list.length; i++){
%>
			<tr>
		      <td><input type="checkbox" onclick="checkEvent(<%=i %>)" name="checkbox"> </td>
		      <td><%=list[i].getNo() %></td>
		      <td><%=list[i].getTxt() %></td>
		    </tr>
<%
		}
	}
%>
	  </tbody>
	</table>
</div>

</body>
</html>