<%-- 
    Document   : registro
    Created on : 11 mar. 2025, 20:05:24
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<%
    if (session.getAttribute("usuario") != null) {
        response.sendRedirect(request.getContextPath() + "/SvMisGrupos");
        return;
    }
%>
<div class="row justify-content-center">
    <div class="col-md-6">
        <h2 class="mb-4">Registro de Usuario</h2>
        <form action="/ChattideWeb/SvRegistro" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" placeholder="tunombre" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="ejemplo@gmail.com" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Contraseña:</label>
                <div class="input-group">
                    <input type="password" class="form-control" id="password" name="password" placeholder="contraseña"
                           minlength="8" maxlength="16" required>
                    <button type="button" class="btn btn-outline-secondary toggle-password">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">Avatar (opcional):</label>
                <input type="file" class="form-control check-file" id="avatar" name="avatar">
            </div>
            <button type="submit" class="btn btn-success">Registrarse</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>

