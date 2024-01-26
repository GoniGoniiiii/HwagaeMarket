<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 부트스트랩 4.0.0 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<!-- 커스텀 -->
<link rel="stylesheet" href="resources/css/Login.css">

</head>
</head>
<body align="center">


   <%@ include file="Header.jsp" %>
   <br><br><br><br>   
   <a href="#" class="custom-link">SpringFramework Test</a>
   <section class="container mt-3" style="max-width: 530px;">
      <form action="login.do" method="post">
         <div class="form-group">
            <label>이메일</label>
            <input type="text" name="email" class="form-control">
         </div>
         <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="pass" class="form-control">
         </div>
         <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="rememberMe">
                <label class="form-check-label" for="rememberMe">아이디 저장</label>
         </div>
         <div style="text-align: center;">
            <button type="submit" class="btn btn-outline-success btn-block">로그인</button><br><br><br>
            <a class="btn btn-outline-primary " href="insert.do">회원가입</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
            <a class="btn btn-outline-primary" href="../error/error.jsp">아이디찾기</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
            <a class="btn btn-outline-primary" href="../error/error.jsp">비밀번호 찾기</a>
         </div>
      </form>
   </section>

   <br><br><br><br>
<%--    <%@ include file="Footer.jsp" %> --%>

<!-- 제이쿼리 3.2.1 -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>

<!-- 파퍼 1.12.9 -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

<!-- 부트스트랩 4.0.0 자바스크립트 -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>
</html>