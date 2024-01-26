<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
        body {
            /* height: 100vh; /* 화면 전체 높이를 차지하도록 설정 */ */
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            margin: 0; /* body의 기본 마진 제거 */
        }
        section {
            border: 1px solid gray;
            padding: 70px 30px;
            border-radius: 15px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        
</style>
<!-- 부트스트랩 -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body align="center">
<%@ include file="../Header.jsp" %>
   <br><br><br><br><br>
   
   <section class="container mt-3" style="max-width: 530px;">
      <form action="insert.do" method="post">
         <div class="form-group">
            <label>이름</label>
            <input type="text" name="name" class="form-control">
         </div>
         <div class="form-group">
            <label>이메일</label>
            <input type="email" name="email" class="form-control">
         </div>
         <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="pass" class="form-control">
         </div>
         <div class="form-group">
            <label>회사</label>
            <input type="text" name="comdept" class="form-control">
         </div>
         <div class="form-group">
            <label>생일</label>
            <input type="date" name="birth" class="form-control">
         </div>
         <div class="form-group">
            <label>전화번호</label>
            <input type="tel" name="tel" class="form-control">
         </div>
         <div class="form-group">
            <label>메모</label>
            <input type="text" name="memo" class="form-control">
         </div>
         <button type="submit" class="btn btn btn-outline-primary ">회원가입</button>
         <button type="reset" class="btn btn btn-outline-danger " onclick="window.location.href='http://localhost:8080/biz/loginform.do'">취소</button>
         
      </form>
   </section>

   <br><br>
    <%@ include file="../Footer.jsp" %>
<!-- 제이쿼리 자바스크립트 추가 -->
<script src="../js/jquery.min.js"></script>
<!-- 파퍼 -->
<script src="../js/popper.js"></script>
<!-- 부트스트랩 -->
<script src="../js/bootstrap.min.js"></script>

</body>
</html>