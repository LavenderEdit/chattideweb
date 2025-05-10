<%-- 
    Document   : header
    Created on : 10 mayo 2025, 14:04:35
    Author     : Joan - Izz
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Chattide es una red social moderna y dinámica que te conecta a través de grupos de interés, publicaciones interactivas y comentarios en tiempo real. Disfruta de una experiencia intuitiva, diseño responsive y actualizaciones fluidas con AJAX, en un entorno seguro y fácil de usar.">
        <title>Chattide</title>
        <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/Logos/Logo-Chattide-FondoClaro.ico" type="image/x-icon"/>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main-style.css"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">Chattide</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <%-- Si el usuario ha iniciado sesión, mostramos opciones de usuario; de lo contrario, opciones de login/registro --%>
                        <% if (session.getAttribute("usuario") != null) { %>
                        <li class="nav-item"><a class="nav-link" href="miCuenta.jsp">Mi Cuenta</a></li>
                        <li class="nav-item"><a class="nav-link" href="misGrupos.jsp">Mis Grupos</a></li>
                        <li class="nav-item"><a class="nav-link" href="logout.jsp">Cerrar Sesión</a></li>
                            <% } else { %>
                        <li class="nav-item"><a class="nav-link" href="login.jsp">Iniciar Sesión</a></li>
                        <li class="nav-item"><a class="nav-link" href="registro.jsp">Registrarse</a></li>
                            <% }%>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container mt-4">
