<%-- 
    Document   : misGrupos
    Created on : 11 mar. 2025, 20:06:30
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<h2>Mis Grupos</h2>
<div class="row">
    <c:forEach var="grupo" items="${misGrupos}">
        <div class="col-md-4 mb-3">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">${grupo.nombre}</h5>
                    <p class="card-text">${grupo.descripcion}</p>
                    <a href="${pageContext.request.contextPath}/SvMiGrupo?id=${grupo.id}"
                       class="btn btn-primary">Entrar</a>
                    <button class="btn btn-outline-danger btn-sm"
                            onclick="salirGrupo(${grupo.id}, this)">
                        Salir
                    </button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<script>
    function salirGrupo(grupoId, btn) {
        if (!confirm('¿Seguro que quieres salir de este grupo?'))
            return;
        fetch(`${'${pageContext.request.contextPath}'}/SvSalirGrupo?id=${grupoId}`, {
                    method: 'DELETE'
                }).then(res => {
                    if (res.status === 204) {
                        // elimina la tarjeta de la UI
                        btn.closest('.col-md-4').remove();
                    } else {
                        alert('Error al salir del grupo');
                    }
                }).catch(err => {
                    console.error(err);
                    alert('Error de red');
                });
            }
</script>

<%@ include file="footer.jsp" %>
