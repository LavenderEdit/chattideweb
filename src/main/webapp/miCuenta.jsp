<%-- 
    Document   : micuenta
    Created on : 11 mar. 2025, 20:05:32
    Author     : Joan - Izz
--%>

<%@ include file="header.jsp" %>
<div class="row">
    <div class="col-md-4">
        <h2>Mi Cuenta</h2>
        <!-- Mostrar avatar y datos actuales -->
        <img src="<%= session.getAttribute("avatar") != null ? session.getAttribute("avatar") : "./Recursos/Usuario/DefaultUserAvatar.webp"%>" 
             alt="Avatar" class="img-thumbnail mb-3">
        <p><strong>Nombre:</strong> <%= session.getAttribute("nombre")%></p>
        <p><strong>Email:</strong> <%= session.getAttribute("email")%></p>
    </div>
    <div class="col-md-8">
        <h3>Editar Perfil</h3>
        <form action="MiCuentaServlet" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<%= session.getAttribute("nombre")%>" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= session.getAttribute("email")%>" required>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">Cambiar Avatar:</label>
                <input type="file" class="form-control" id="avatar" name="avatar">
            </div>
            <button type="submit" class="btn btn-primary">Actualizar Perfil</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>

