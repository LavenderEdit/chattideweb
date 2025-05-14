<%-- 
    Document   : publicacion
    Created on : 11 mar. 2025, 20:06:23
    Author     : Joan - Izz
--%>

<%@ include file="header.jsp" %>

<div class="row">
    <div class="col-md-8">
        <h2>Detalle de Publicación</h2>
        <div class="card mb-3">
            <div class="card-body">
                <p>${publicacion.contenido}</p>
                <small class="text-muted">
                    Publicado por 
                    <a href="${pageContext.request.contextPath}/SvPerfil?userId=${publicacion.autorId}">
                        ${publicacion.autorNombre}
                    </a>
                    el ${publicacion.fechaPublicacion}
                </small>
                <div class="mt-2">
                    <button class="btn btn-outline-primary btn-sm"
                            onclick="darLike(${publicacion.id})">
                        Me gusta (
                        <span id="likeCount">${publicacion.likeCount}</span>
                        )
                    </button>
                </div>
            </div>
        </div>
        <!-- Sección de comentarios -->
        <h3>Comentarios (${publicacion.comentarioCount})</h3>
        <form action="${pageContext.request.contextPath}/SvComentario" method="post" class="mb-4">
            <div class="mb-3">
                <textarea class="form-control"
                          name="contenido"
                          placeholder="Escribe un comentario..."
                          required></textarea>
            </div>
            <input type="hidden" name="idPublicacion" value="${publicacion.id}">
            <button type="submit" class="btn btn-primary">Comentar</button>
        </form>
        <hr>
        <c:forEach var="comentario" items="${listaComentarios}">
            <div class="mb-2">
                <strong>${comentario.usuarioNombre}:</strong>
                ${comentario.contenido}
                <small class="text-muted">
                    (${comentario.fechaComentario})
                </small>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    function darLike(publicacionId) {
        // Lógica AJAX para actualizar "me gusta".
    }
</script>
<%@ include file="footer.jsp" %>