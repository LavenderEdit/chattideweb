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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main-style.css?v=1">
    </head>
    <body data-context-path="${pageContext.request.contextPath}" data-grupo-id="${param.id}">
        <script>
            window.APP_CONTEXT_PATH = document.body.dataset.contextPath;
            window.addEventListener('pageshow', function (event) {
                if (event.persisted) {
                    window.location.reload();
                }
            });
        </script>
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
            <div class="container">
                <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/index.jsp">
                    Chattide
                </a>
                <button class="navbar-toggler" type="button"
                        data-bs-toggle="collapse" data-bs-target="#mainNav"
                        aria-controls="mainNav" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="mainNav">
                    <ul class="navbar-nav ms-auto align-items-center">

                        <%-- Si no hay usuario logeado --%>
                        <c:if test="${empty sessionScope.usuario}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">
                                    Iniciar Sesión
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/registro.jsp">
                                    Registrarse
                                </a>
                            </li>
                        </c:if>

                        <%-- Si hay usuario logeado --%>
                        <c:if test="${not empty sessionScope.usuario}">
                            <%-- Dropdown de Grupos --%>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="groupsDropdown"
                                   role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Grupos
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="groupsDropdown">
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

                            <%-- Perfil de usuario con dropdown --%>
                            <c:set var="user" value="${sessionScope.usuario}" />
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle d-flex align-items-center"
                                   href="#" id="userDropdown" role="button"
                                   data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${not empty user.avatar}">
                                            <img src="${user.avatar}"
                                                 alt="Avatar"
                                                 class="rounded-circle me-2 avatar-bg"
                                                 onerror="this.onerror=null;
                                                 this.src='${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp';"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                                                 alt="Avatar"
                                                 class="rounded-circle me-2"
                                                 width="30" height="30"/>
                                        </c:otherwise>
                                    </c:choose>
                                    ${user.nombre}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                                    <li>
                                        <a class="dropdown-item"
                                           href="${pageContext.request.contextPath}/SvMiCuenta">
                                            Mi Cuenta
                                        </a>
                                    </li>
                                    <li><hr class="dropdown-divider"/></li>
                                    <li>
                                        <a class="dropdown-item text-danger"
                                           href="${pageContext.request.contextPath}/logout.jsp">
                                            Cerrar Sesión
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
