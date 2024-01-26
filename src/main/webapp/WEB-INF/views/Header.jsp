<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <style>

    .navbar {
      position: fixed;
      top: 0;
      width: 100%;
    }
  </style>
    
   
   
   <nav class="navbar navbar-expand-lg navbar-light bg-light" style="height:100px">
      <a class="navbar-brand" href="memberList.do" style="margin-left:50px">SpringFramework Test</a>
   
      <div id="navbar" class="collapse navbar-collapse">
         <ul class="navbar-nav mr-auto">

         </ul>
          
                        <!-- 로그인 이후 -->
            <c:if test="${not empty memberI}">
               <span style="float: right; margin-right: 50px;">${memberI.name }님! 환영합니다~ &nbsp;
                  <a class="btn btn-danger my-2 my-sm-0" href="logout.do">로그아웃</a>
               </span>
            </c:if>

            <c:if test="${empty memberI}">
               
               <span style="text-decoration: none; font-size: 17px; float: right; margin-right: 150px;">로그인 해주세요 &nbsp;
                  <a class="btn btn-outline-success my-2 my-sm-0" href="loginform.do">로그인 </a>
               </span>
            </c:if>

      </div>
   </nav>   