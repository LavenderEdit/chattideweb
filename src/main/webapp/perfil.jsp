<%-- 
    Document   : perfil
    Created on : 16 may. 2025, 14:50:19
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>

<div class="container my-5">
    <div class="row">
        <div class="col-md-4 text-center">
            <c:choose>
                <c:when test="${not empty usuarioPerfil.avatar}">
                    <img src="${usuarioPerfil.avatar}"
                         alt="${usuarioPerfil.nombre}"
                         class="rounded-circle mb-3"
                         width="150" height="150"
                         onerror="this.onerror=null;
                         this.src='${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp';"/>
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                         alt="Avatar por defecto"
                         class="rounded-circle mb-3"
                         width="150" height="150"/>
                </c:otherwise>
            </c:choose>

            <h3>${usuarioPerfil.nombre}</h3>
            <p class="text-muted">${usuarioPerfil.email}</p>

            <ul class="list-group mt-4 text-start">
                <li class="list-group-item">
                    <strong>Grupos:</strong> ${gruposCount}
                </li>
                <li class="list-group-item">
                    <strong>Publicaciones:</strong> ${publicacionesCount}
                </li>
            </ul>
        </div>

        <div class="col-md-8">
            <h4>Bienvenido al perfil de ${usuarioPerfil.nombre}</h4>
            <p class="text-muted">
                Aquí podrás ver la actividad, editar tu información y mucho más.
            </p>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>