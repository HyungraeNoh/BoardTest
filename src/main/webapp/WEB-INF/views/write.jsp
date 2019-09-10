<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판글쓰기</title>
    <link rel="stlyesheet" href="/resources/css/file.css">
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
</head>
<body>
    <div align="center" class="container">
        <div align="left" style="width:600px; border:1px solid #cccccc; padding:20px; padding-left:50px; margin-top:20px; background-color:#ffffff">
            <h4>게시판 글쓰기</h4>
            <hr />
            <form action="" method="post" enctype="multipart/form-data">
	            <div class="form-inline" style="margin-bottom:10px">
	                <label style="width:90px">글제목</label>
	                <input name="title" type="text" class="form-control" style="width:400px" placeholder="글 제목을 입력하세요." />
	            </div>
	            
	            <div class="form-inline" style="margin-bottom:10px">
	                <label style="width:90px">글내용</label>
	                <textarea name="txt" id="txt" class="form-control" style="width:400px; resize:none" rows="5" placeholder="글 내용을 입력하세요."></textarea>
	            </div>
		        <ul id="mainContainer">
		            <li class="subContainer" id="clickMe">
		                <input class="dn" id="file" type="file" name="file" onchange="file_Event(this)" multiple="multiple"><br>
		               
		            </li>
		        </ul>
		        <button type="button" onclick="formList()">확인</button>
		        <input type="submit" value="전송" formaction="/">
            </form>
        </div>
    </div>
</body>
</html>