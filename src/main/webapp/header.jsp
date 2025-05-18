<%-- 
    Document   : header
    Created on : 11 mar. 2025, 09:50:30
    Author     : Juan - Luis
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="description" content="Chattide es una red social moderna...">
        <title>Chattide</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/Logos/Logo-Chattide-FondoClaro.ico"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main-style.css?v=3">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">
    </head>
    <body data-context-path="${pageContext.request.contextPath}" data-grupo-id="${param.id}">
        <script>
            window.APP_CONTEXT_PATH = document.body.dataset.contextPath;
            window.addEventListener('pageshow', function (event) {
                if (event.persisted) {
                    window.location.reload();
                }
            });
            (function () {
                const theme = localStorage.getItem('chattide-theme') || 'light';
                if (theme === 'dark')
                    document.body.classList.add('theme-dark');
                if (theme === 'orange')
                    document.body.classList.add('theme-orange');
            })();
        </script>
        <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top border-bottom">
            <div class="container">

                <a class="navbar-brand fw-bold fs-3 text-primary" 
                   href="${pageContext.request.contextPath}/index.jsp">
                    Chattide
                </a>

                <button class="navbar-toggler p-2" type="button"
                        data-bs-toggle="collapse" data-bs-target="#mainNav"
                        aria-controls="mainNav" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="mainNav">
                    <ul class="navbar-nav ms-auto align-items-center gap-3">

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle px-3 py-2 rounded" href="#" id="themeDropdown"
                               role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-adjust"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="themeDropdown">
                                <li><button class="dropdown-item" data-theme="light">Light Mode</button></li>
                                <li><button class="dropdown-item" data-theme="dark">Dark Mode</button></li>
                                <li><button class="dropdown-item" data-theme="orange">Orange Dark</button></li>
                            </ul>
                        </li>

                        <c:if test="${empty sessionScope.usuario}">
                            <li class="nav-item">
                                <a class="nav-link px-3 py-2 rounded" 
                                   href="${pageContext.request.contextPath}/login.jsp">
                                    Iniciar Sesión
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link px-3 py-2 rounded"
                                   href="${pageContext.request.contextPath}/registro.jsp">
                                    Registrarse
                                </a>
                            </li>
                        </c:if>

                        <c:if test="${not empty sessionScope.usuario}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle px-3 py-2 rounded" href="#" 
                                   id="groupsDropdown" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    <i class="fas fa-users me-1"></i> Grupos
                                </a>
                                <ul class="dropdown-menu shadow-sm" aria-labelledby="groupsDropdown">
                                    <li>
                                        <a class="dropdown-item" 
                                           href="${pageContext.request.contextPath}/SvMisGrupos">
                                            Mis Grupos
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" 
                                           href="${pageContext.request.contextPath}/SvBuscarGrupos">
                                            Buscar Grupos
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" 
                                           href="${pageContext.request.contextPath}/crearGrupo.jsp">
                                            Crear Grupo
                                        </a>
                                    </li>
                                </ul>
                            </li>

                            <c:set var="user" value="${sessionScope.usuario}" />
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle d-flex align-items-center px-3 py-2 rounded" href="#" 
                                   id="userDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${not empty user.avatar}">
                                            <div class="avatar-bg me-2" 
                                                 style="background-image:url('${user.avatar}');"></div>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                                                 alt="Avatar" class="rounded-circle me-2 avatar-bg"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="fw-medium">${user.nombre}</span>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="userDropdown">
                                    <li>
                                        <a class="dropdown-item" 
                                           href="${pageContext.request.contextPath}/SvMiCuenta">
                                            <i class="fas fa-user-cog me-2"></i> Mi Cuenta
                                        </a>
                                    </li>
                                    <li><hr class="dropdown-divider"/></li>
                                    <li>
                                        <a class="dropdown-item text-danger" 
                                           href="${pageContext.request.contextPath}/logout.jsp">
                                            <i class="fas fa-sign-out-alt me-2"></i> Cerrar Sesión
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </c:if>

                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-4">