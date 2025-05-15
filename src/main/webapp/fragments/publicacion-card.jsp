<%-- 
    Document   : publicacion-card
    Created on : 15 may. 2025, 12:43:53
    Author     : Juan - Luis
--%>

<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>

<c:forEach var="pub" items="${listaPublicaciones}">
    <div class="card mb-3">
        <div class="card-body">
            <p>${pub.contenido}</p>
            <small class="text-muted">
                Publicado por 
                <a href="${pageContext.request.contextPath}/SvPerfil?userId=${pub.autorId}">
                    ${pub.autorNombre}
                </a>
                &bull;
                <fmt:formatDate value="${pub.fechaPublicacion}" pattern="dd/MM/yyyy HH:mm" />
            </small>
            <div class="mt-2 d-flex align-items-center">
                <a href="${pageContext.request.contextPath}/SvPublicacion?id=${pub.id}"
                   class="btn btn-link me-3">
                    Ver Detalles
                </a>
                <button class="btn btn-outline-primary btn-sm me-2 like-btn"
                        data-publicacion-id="${pub.id}">
                    <i class="fas fa-thumbs-up"></i>
                    <span class="like-count">${pub.likeCount}</span>
                </button>
                <small class="text-secondary">
                    ${pub.comentarioCount} comentarios
                </small>
            </div>
        </div>
    </div>
</c:forEach>
