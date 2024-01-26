<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Result</title>
    <!-- 부트스트랩 4.0.0 -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
        crossorigin="anonymous">
</head>
<body align="center">

<%@ include file="../Header.jsp" %>
<br><br><br><br>

<div class="container mt-3" style="text-align: center;">
    <table class="table table-bordered table-hover">
        <tr>
            <td colspan="7">
                <h1><a href="memberList.do">회원 리스트</a></h1>
            </td>
        </tr>
        <c:forEach var="member" items="${searchResult}" begin="0" step="1">
            <tr>
                <td class="align-middle">${member.id }</td>
                <td class="align-middle"><a href="member.do?id=${member.id}">${member.name}</a></td>
                <td class="align-middle">${member.email }</td>
                <td class="align-middle">${member.comdept }</td>
                <td class="align-middle">${member.birth }</td>
                <td class="align-middle">${member.tel }</td>
                <td class="align-middle"><a href="delete.do?id=${member.id }" class="btn btn-danger">삭제</a></td>
            </tr>
         </c:forEach>
       </table>

    <form action="search.do" method="get" class="form-inline mt-4 mb-3">
         <select name="searchType" class="form-control mx-1 mt-2">
            <option value="name">이름</option>
            <option value="tel">전화번호</option>
        </select>
            <input type="text" name="searchKey" class="form-control mx-1 mt-2" style="width: 250px;" placeholder = "이름을 입력하세요." required="required">
           <button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
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


<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        var searchTypeSelect = document.querySelector('select[name="searchType"]');
        var searchKeyInput = document.querySelector('input[name="searchKey"]');

        searchTypeSelect.addEventListener('change', function () {
            if (this.value === 'tel') {
                searchKeyInput.placeholder = "'-'를 포함하여 모두 입력하세요.";
            }
        });
        searchTypeSelect.addEventListener('change', function () {
            if (this.value === 'name') {
                searchKeyInput.placeholder = "이름을 입력하세요.";
            }
        });
        
    });
</script>

</body>
</html>