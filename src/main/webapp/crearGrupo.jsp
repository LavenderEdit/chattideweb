<%-- 
    Document   : crearGrupo
    Created on : 11 mar. 2025, 20:06:03
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<div class="row justify-content-center">
    <div class="col-md-6">
        <h2 class="mb-4">Crear Grupo</h2>
        <form action="CrearGrupoServlet" method="post">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre del Grupo:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción:</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label for="tipo_privacidad" class="form-label">Tipo de Privacidad:</label>
                <select class="form-select" id="tipo_privacidad" name="tipo_privacidad">
                    <option value="publico">Público</option>
                    <option value="privado">Privado</option>
                </select>
            </div>
            <button type="submit" class="btn btn-success">Crear Grupo</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>

