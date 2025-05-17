<%-- 
    Document   : perfil
    Created on : 16 may. 2025, 14:50:19
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>

<div class="container my-5">
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card shadow-sm border-0 text-center p-4">
                <c:choose>
                    <c:when test="${not empty usuarioPerfil.avatar}">
                        <img src="${usuarioPerfil.avatar}"
                             alt="${usuarioPerfil.nombre}"
                             class="rounded-circle mb-3 mx-auto"
                             style="width: 160px; height: 160px; object-fit: cover;"
                             onerror="this.onerror=null;
                             this.src='${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp';"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                             alt="Avatar por defecto"
                             class="rounded-circle mb-3 mx-auto"
                             style="width: 160px; height: 160px; object-fit: cover;"/>
                    </c:otherwise>
                </c:choose>

                <h3 class="fw-bold mb-2">${usuarioPerfil.nombre}</h3>
                <p class="text-muted mb-4">${usuarioPerfil.email}</p>

                <ul class="list-group list-group-flush w-100">
                    <li class="list-group-item d-flex justify-content-between align-items-center py-3">
                        <strong>Grupos</strong>
                        <span class="badge bg-primary rounded-pill">${gruposCount}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center py-3">
                        <strong>Publicaciones</strong>
                        <span class="badge bg-primary rounded-pill">${publicacionesCount}</span>
                    </li>
                </ul>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm border-0 p-4">
                <h4 class="fw-bold mb-3">Bienvenido al perfil de ${usuarioPerfil.nombre}</h4>
                <p class="text-muted lead">
                    Explora la actividad, información y más detalles de este perfil.
                </p>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>