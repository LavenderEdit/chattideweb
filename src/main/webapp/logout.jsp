<%-- 
    Document   : logout
    Created on : 11 mar. 2025, 20:06:39
    Author     : Joan - Izz
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>

