<%-- 
    Document   : buscarGrupos
    Created on : 11 mar. 2025, 20:05:52
    Author     : Juan - Luis
--%>

<%@ include file="header.jsp" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<h2>Buscar Grupos</h2>
<div class="row">
    <c:forEach var="grupo" items="${listaGrupos}">
        <div class="col-md-4 mb-3">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">${grupo.nombre}</h5>
                    <p class="card-text text-secondary">${grupo.descripcion}</p>
                    <a href="${pageContext.request.contextPath}/SvUnirseGrupo?id=${grupo.id}" class="btn btn-primary">Unirse</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<%@ include file="footer.jsp" %>

