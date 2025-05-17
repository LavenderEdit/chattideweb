<%-- 
    Document   : miCuenta
    Created on : 11 mar. 2025, 20:05:32
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<div class="container my-5">
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card shadow-sm border-0 p-4 text-center">
                <h2 class="fw-bold mb-4">Mi Cuenta</h2>
                <c:set var="user" value="${sessionScope.usuarioDTO}"/>
                <c:choose>
                    <c:when test="${not empty user.avatar}">
                        <img src="${user.avatar}"
                             alt="Avatar"
                             class="rounded-circle mb-4 mx-auto"
                             style="width: 180px; height: 180px; object-fit: cover;"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/images/Usuario/DefaultUserAvatar.webp"
                             alt="Avatar"
                             class="rounded-circle mb-4 mx-auto"
                             style="width: 180px; height: 180px; object-fit: cover;"/>
                    </c:otherwise>
                </c:choose>

                <p class="mb-2"><strong>Nombre:</strong> ${user.nombre}</p>
                <p class="text-muted"><strong>Email:</strong> ${user.email}</p>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm border-0 p-4">
                <h3 class="fw-bold mb-4">Editar Perfil</h3>
                <form id="form-user"
                      enctype="multipart/form-data"
                      class="mt-3">
                    <div class="mb-4">
                        <label for="nombre" class="form-label fw-semibold">Nombre</label>
                        <input type="text" id="nombre" name="nombre"
                               value="${user.nombre}"
                               class="form-control rounded-3" required/>
                    </div>
                    <div class="mb-4">
                        <label for="email" class="form-label fw-semibold">Email</label>
                        <input type="email" id="email" name="email"
                               value="${user.email}"
                               class="form-control rounded-3" required/>
                    </div>
                    <div class="mb-4">
                        <label for="avatar" class="form-label fw-semibold">Avatar</label>
                        <input type="file" id="avatar" name="avatar"
                               class="form-control rounded-3 check-file"/>
                    </div>
                    <button type="submit" class="btn btn-success px-4 py-2 rounded-3">
                        Guardar Cambios
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>