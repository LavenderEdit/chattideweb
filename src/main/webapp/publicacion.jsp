<%-- 
    Document   : publicacion
    Created on : 11 mar. 2025, 20:06:23
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<div class="row">
    <div class="col-lg-8">

        <div class="card mb-4 shadow-sm">
            <div class="card-body">
                <p class="fs-5 text-secondary">${publicacion.contenido}</p>
                <div class="text-secondary small mb-2">
                    Publicado por 
                    <a href="${pageContext.request.contextPath}/SvPerfil?userId=${publicacion.autorId}">
                        ${publicacion.autorNombre}
                    </a>
                    &bull; 
                    <fmt:formatDate 
                        value="${publicacion.fechaPublicacion}" 
                        pattern="dd/MM/yyyy HH:mm"
                        />
                </div>
                <button class="btn btn-sm me-2 like-btn
                        ${userLikeId != null ? 'btn-primary liked' : 'btn-outline-primary'}"
                        data-publicacion-id="${publicacion.id}"
                        data-like-id="${userLikeId}">
                    <i class="fas fa-thumbs-up"></i>
                    <span class="like-count">${publicacion.likeCount}</span>
                </button>
            </div>
        </div>

        <h4>Comentarios (<span>${publicacion.comentarioCount}</span>)</h4>
        <form id="form-comentario" class="mb-4">
            <div class="input-group">
                <textarea class="form-control" name="contenido" placeholder="Escribe un comentario..." style="resize: none;" required></textarea>
                <input type="hidden" name="idPublicacion" value="${publicacion.id}" />
                <button class="btn btn-primary" type="submit">
                    <i class="fas fa-paper-plane"></i>
                </button>
            </div>
        </form>

        <!-- Contenedor de comentarios -->
        <div id="comentarios-container">
            <jsp:include page="fragments/comentario-list.jsp">
                <jsp:param name="listaComentarios" value="${listaComentarios}"/>
            </jsp:include>
        </div>

    </div>
</div>
<%@ include file="footer.jsp" %>