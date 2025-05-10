<%-- 
    Document   : miGrupo
    Created on : 11 mar. 2025, 20:06:13
    Author     : Joan - Izz
--%>

<%@ include file="header.jsp" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="row">
    <div class="col-md-8">
        <h2>${grupo.nombre}</h2>
        <p>${grupo.descripcion}</p>
        <!-- Formulario para nueva publicación -->
        <form action="PublicarServlet" method="post" class="mb-4">
            <div class="mb-3">
                <textarea class="form-control" name="contenido" placeholder="Escribe tu publicación..." required></textarea>
            </div>
            <input type="hidden" name="idGrupo" value="${grupo.id}">
            <button type="submit" class="btn btn-primary">Publicar</button>
        </form>
        <!-- Listado de publicaciones -->
        <c:forEach var="publicacion" items="${listaPublicaciones}">
            <div class="card mb-3">
                <div class="card-body">
                    <p>${publicacion.contenido}</p>
                    <small class="text-muted">Publicado por ${publicacion.usuarioNombre} el ${publicacion.fecha_publicacion}</small>
                    <div class="mt-2">
                        <a href="publicacion.jsp?id=${publicacion.id}" class="btn btn-link">Ver Detalles</a>
                        <button class="btn btn-outline-primary btn-sm" onclick="darLike(${publicacion.id})">
                            Me gusta (<span id="likeCount${publicacion.id}">${publicacion.likeCount}</span>)
                        </button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="col-md-4">
        <h3>Miembros</h3>
        <ul class="list-group">
            <c:forEach var="miembro" items="${listaMiembros}">
                <li class="list-group-item">${miembro.nombre}</li>
                </c:forEach>
        </ul>
    </div>
</div>
<script>
    function darLike(publicacionId) {
        // Aquí iría la lógica AJAX para actualizar el "me gusta" sin recargar la página.
    }
</script>
<%@ include file="footer.jsp" %>

