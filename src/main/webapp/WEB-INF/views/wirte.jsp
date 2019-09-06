<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판글쓰기</title>
    <link href="/resource/css/bootstrap.css" rel="stylesheet" />
</head>
 
<body style="background-color:#E6FFFF">
    <!-- 일정크기만큼 유지하면서 중앙으로 정렬 -->
    <div align="center" class="container">
        <div align="left" style="width:600px; border:1px solid #cccccc; padding:20px; padding-left:50px; margin-top:20px; background-color:#ffffff">
            <h4>게시판 글쓰기</h4>
            <hr />
            <div class="form-inline" style="margin-bottom:10px">
                <label style="width:90px">글제목</label>
                <input type="text" class="form-control" style="width:400px" placeholder="글 제목을 입력하세요." />
            </div>
            
            <div class="form-inline" style="margin-bottom:10px">
                <label style="width:90px">글내용</label>
                <textarea id="txt" class="form-control" style="width:400px; resize:none" rows="5" placeholder="글 내용을 입력하세요."></textarea>
            </div>
            
            <div class="form-inline" style="margin-bottom:10px">
                <div class="form-group">
                    <label style="width:90px">첨부파일</label>
                </div>
                <div class="form-group">
                    <input type="file" style="width:400px" placeholder="첨부파일을 선택하세요." />
                </div>
            </div>
            
            <!-- <div class="form-inline" style="margin-bottom:10px">
                <label style="width:90px">작성자</label>
                <input type="text" class="form-control" style="width:200px" placeholder="작성자를 입력하세요." />
            </div>
            
            <div class="form-inline" style="margin-bottom:10px">
                <label style="width:90px">암호</label>
                <input type="password" class="form-control" style="width:200px" placeholder="암호를 입력하세요." />
            </div> -->
            
            <div align="center" style="margin-bottom:10px">
            <input type="text">
                <input type="button" style="margin-right:10px" class="btn btn-success" value="글쓰기">
                <input type="button" class="btn btn-primary" value="글목록">
            </div>
            
        </div>
    </div>
    
</body>
</html>