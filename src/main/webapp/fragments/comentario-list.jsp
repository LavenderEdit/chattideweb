<%-- 
    Document   : comentario-list
    Created on : 15 may. 2025, 14:43:53
    Author     : Juan - Luis
--%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:choose>
    <c:when test="${empty listaComentarios}">
        <div class="alert alert-info d-flex align-items-center">
            <i class="fas fa-meh fa-2x me-2"></i>
            <div>
                <strong>Aún no hay comentarios</strong><br/>
                Sé el primero en opinar...
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <c:forEach var="c" items="${listaComentarios}">
            <div class="d-flex mb-3">
                <div class="flex-shrink-0 me-3">
                    <c:choose>
                        <c:when test="${not empty c.avatarUsuarioUrl}">
                            <img src="${c.avatarUsuarioUrl}"
                                 alt="${c.usuarioNombre}"
                                 class="rounded-circle avatar-bg"
                                 onerror="this.onerror=null;
                                 this.src='${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp';"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                                 alt="Avatar por defecto"
                                 class="rounded-circle"
                                 width="40" height="40"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="flex-grow-1">
                    <strong>${c.usuarioNombre}</strong>
                    <span class="text-muted small">
                        &bull;
                        <fmt:formatDate value="${c.fechaComentario}" pattern="dd/MM/yyyy HH:mm"/>
                    </span>
                    <p class="mb-0">${c.contenido}</p>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
