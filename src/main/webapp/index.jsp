<%-- 
    Document   : index
    Created on : 10 mayo 2025, 20:05:06
    Author     : Juan - Luis
--%>
<%@ include file="header.jsp" %>
<%
    if (session.getAttribute("usuario") != null) {
        response.sendRedirect(request.getContextPath() + "/SvMisGrupos");
        return;
    }
%>  

<div class="d-flex align-items-center justify-content-center">
    <div class="card shadow-lg p-5 mx-3">
        <h1 class="fw-bold mb-3 text-primary text-center">Bienvenido a Chattide</h1>
        <p class="lead text-muted mb-4 text-center">
            Conecta, comparte ideas y crece tu comunidad.
        </p>
        <div class="d-grid gap-3">
            <a href="login.jsp" class="btn btn-primary btn-lg">Iniciar Sesión</a>
            <a href="registro.jsp" class="btn btn-outline-primary btn-lg">Registrarse</a>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
