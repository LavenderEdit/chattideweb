<%-- 
    Document   : miGrupo
    Created on : 11 mar. 2025, 20:06:13
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>

<div class="row">
    <div class="col-md-8">
        <h2>${grupo.nombre}</h2>
        <p>${grupo.descripcion}</p>
        <button type="button" 
                class="btn btn-outline-danger mb-4 exit-group-btn"
                data-group-id="${grupo.id}"
                title="Salir del grupo">
            <i class="fas fa-sign-out-alt"></i>
        </button>

        <!-- Formulario AJAX para publicar -->
        <form id="form-publicar" class="mb-4">
            <div class="input-group">
                <textarea class="form-control" name="contenido"
                          placeholder="Escribe tu publicación..." style="resize: none;" required></textarea>
                <input type="hidden" name="idGrupo" value="${grupo.id}" />
                <button class="btn btn-primary" type="submit">
                    <i class="fas fa-paper-plane"></i>
                </button>
            </div>
        </form>

        <!-- Contenedor de publicaciones -->
        <div id="publicaciones-container">
            <jsp:include page="fragments/publicacion-card.jsp">
                <jsp:param name="listaPublicaciones" value="${listaPublicaciones}"/>
            </jsp:include>
        </div>
    </div>

    <div class="col-md-4">
        <h3>Miembros</h3>
        <ul class="list-group">
            <c:forEach var="miembro" items="${miembros}">
                <li class="list-group-item d-flex align-items-center">
                    <c:choose>
                        <c:when test="${not empty miembro.avatar}">
                            <img src="${miembro.avatar}"
                                 alt="${miembro.nombre}"
                                 class="rounded-circle me-2"
                                 width="40" height="40"
                                 onerror="this.onerror=null;
                                 this.src='${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp';"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                                 alt="Avatar"
                                 class="rounded-circle me-2"
                                 width="40" height="40"/>
                        </c:otherwise>
                    </c:choose>
                    <div>
                        <strong>${miembro.nombre}</strong><br/>
                        <small class="text-muted">${miembro.email}</small>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<script>
    function darLike(publicacionId) {
        // lógica AJAX aquí
    }
</script>

<%@ include file="footer.jsp" %>