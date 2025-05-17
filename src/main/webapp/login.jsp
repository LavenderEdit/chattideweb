<%-- 
    Document   : login
    Created on : 11 mar. 2025, 20:05:17
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
        <h2 class="mb-4">Iniciar Sesión</h2>
        <form id="form-log">
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" autocomplete="on" name="email" placeholder="tuemail@ejemplo.com" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Contraseña:</label>
                <div class="input-group">
                    <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña"
                           minlength="8" maxlength="16" required>
                    <button type="button" class="btn btn-outline-secondary toggle-password">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>
            <button type="submit" id="btn-log" class="btn btn-primary">Entrar</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>

