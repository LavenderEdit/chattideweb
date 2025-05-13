<%@ include file="header.jsp" %>

<h2 class="mb-4">Mis Grupos</h2>
<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
    <c:forEach var="grupo" items="${misGrupos}">
        <div class="col">
            <div class="card h-100 shadow-sm">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">
                        <i class="fas fa-users me-2 text-primary"></i>${grupo.nombre}
                    </h5>
                    <p class="card-text flex-grow-1 text-secondary">${grupo.descripcion}</p>
                    <div class="d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/SvMiGrupo?id=${grupo.id}"
                           class="btn btn-sm btn-primary">
                            <i class="fas fa-sign-in-alt me-1"></i>Entrar
                        </a>
                        <button type="button"
                                class="btn btn-sm btn-outline-danger exit-group-btn"
                                data-group-id="${grupo.id}"
                                title="Salir del grupo">
                            <i class="fas fa-sign-out-alt"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="footer.jsp" %>
