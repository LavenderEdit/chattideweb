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
        <form action="${pageContext.request.contextPath}/SvPublicar" method="post" class="mb-4">
            <div class="mb-3">
                <textarea class="form-control" name="contenido" placeholder="Escribe tu publicación..." required></textarea>
            </div>
            <input type="hidden" name="idGrupo" value="${grupo.id}">
            <button type="submit" class="btn btn-primary">Publicar</button>
        </form>

        <c:forEach var="publicacion" items="${listaPublicaciones}">
            <div class="card mb-3">
                <div class="card-body">
                    <p>${publicacion.contenido}</p>
                    <small class="text-muted">
                        Publicado por ${publicacion.usuarioNombre} el ${publicacion.fecha_publicacion}
                    </small>
                    <div class="mt-2">
                        <a href="${pageContext.request.contextPath}/SvPublicacion?id=${publicacion.id}"
                           class="btn btn-link">Ver Detalles</a>
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
            <c:forEach var="miembro" items="${miembros}">
                <li class="list-group-item d-flex align-items-center">
                    <c:choose>
                        <c:when test="${not empty miembro.avatar}">
                            <img src="${miembro.avatar}"
                                 alt="${miembro.nombre}" class="rounded-circle me-2" width="40" height="40"/>
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                                 alt="Avatar" class="rounded-circle me-2" width="40" height="40"/>
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


