<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 부트스트랩 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<!-- 커스텀 -->
<link rel="stylesheet" href="../css/custom.css">
</head>
<body align="center">

   <%@ include file="../Header.jsp" %>
   <br><br><br><br>
   
   <div class="container mt-5">
        <h1 class="text-center mb-2">회원정보</h1>
        <form action="update.do" method="post">
        
            <div class="form-group row">
                <label for="id" class="col-sm-2 col-form-label">번 호</label>
                <div class="col-sm-10">
                    <input type="text" name="id" value="${user.id}" readonly class="form-control">
                </div>
            </div>
            
            <div class="form-group row">
                <label for="name" class="col-sm-2 col-form-label">이 름</label>
                <div class="col-sm-10">
                    <input type="text" name="name" value="${user.name}" class="form-control">
                </div>
            </div>
                        
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">이메일</label>
                <div class="col-sm-10">
                    <input type="text" name="email" value="${user.email}" class="form-control">
                </div>
            </div>

            <div class="form-group row">
                <label for="tel" class="col-sm-2 col-form-label">전화번호</label>
                <div class="col-sm-10">
                    <input type="text" name="tel" value="${user.tel}" class="form-control">
                </div>
            </div>
            
            <div class="form-group row">
            
                <label for="comdept" class="col-sm-2 col-form-label">회사</label>
                <div class="col-sm-10">
                    <input type="text" name="comdept" value="${user.comdept}" class="form-control">
                </div>
            </div>

            <div class="form-group row">
                <label for="birth" class="col-sm-2 col-form-label">생일</label>
                <div class="col-sm-10">
                    <input type="text"  name="birth" value="${user.birth}" class="form-control" readonly="readonly">
                </div>
            </div>
            
            
            <div class="form-group row">
                <label for="memo" class="col-sm-2 col-form-label">메모</label>
                <div class="col-sm-10">
                    <input type="text" name="memo" value="${user.memo}" class="form-control">
                </div>
            </div>

            
            <div class="form-group row">
                <div class="col-sm-12 text-center">
                    <input type="submit" class="btn btn-primary" value="저장"></button>&nbsp;&nbsp;
                    <input type="button" class="btn btn-danger" value="삭제" onclick="location.href='delete.do?id=${user.id}">&nbsp;&nbsp;
                    <input type="reset" class="btn btn-secondary" value="취소" onclick="location.href='list.do'">
                </div>
            </div>
        </form>
    </div>
   
   
   
   <br><br><br><br>
   <%@ include file="../Footer.jsp" %>
<!-- 제이쿼리 자바스크립트 추가 -->
<script src="../js/jquery.min.js"></script>
<!-- 파퍼 -->
<script src="../js/popper.js"></script>
<!-- 부트스트랩 -->
<script src="../js/bootstrap.min.js"></script>

</body>
</html>