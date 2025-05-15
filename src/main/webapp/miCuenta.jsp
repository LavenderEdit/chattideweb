<%-- 
    Document   : miCuenta
    Created on : 11 mar. 2025, 20:05:32
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<div class="row mt-4">
    <div class="col-md-4">
        <h2>Mi Cuenta</h2>
        <c:set var="user" value="${sessionScope.usuarioDTO}"/>
        <c:choose>
            <c:when test="${not empty user.avatar}">
                <img src="${user.avatar}"
                     alt="Avatar"
                     class="img-thumbnail mb-3"
                     width="200" height="200"
                     onerror="this.onerror=null;
                     this.src='${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp';"/>
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                     alt="Avatar"
                     class="img-thumbnail mb-3"
                     width="200" height="200"/>
            </c:otherwise>
        </c:choose>

        <p><strong>Nombre:</strong> ${user.nombre}</p>
        <p><strong>Email:</strong> ${user.email}</p>
    </div>

    <div class="col-md-8">
        <h3>Editar Perfil</h3>
        <form id="form-user"
              enctype="multipart/form-data"
              class="mt-3">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" id="nombre" name="nombre"
                       value="${user.nombre}"
                       class="form-control" required/>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" id="email" name="email"
                       value="${user.email}"
                       class="form-control" required/>
            </div>
            <div class="mb-3">
                <label for="avatar" class="form-label">Avatar</label>
                <input type="file" id="avatar" name="avatar"
                       class="form-control check-file"/>
            </div>
            <button type="submit" class="btn btn-success">
                Guardar Cambios
            </button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>