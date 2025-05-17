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
<<div class="container py-5 text-center">
    <div class="card shadow-lg border-0 p-5 mx-auto" style="max-width: 600px;">
        <h1 class="fw-bold mb-3 text-primary">Bienvenido a Chattide</h1>
        <p class="lead text-muted mb-4">La red social para conectar, compartir ideas y crear comunidad.</p>
        <div class="d-flex justify-content-center gap-3">
            <a href="login.jsp" class="btn btn-primary btn-lg px-4 py-2 rounded-3">Iniciar Sesión</a>
            <a href="registro.jsp" class="btn btn-success btn-lg px-4 py-2 rounded-3">Registrarse</a>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
