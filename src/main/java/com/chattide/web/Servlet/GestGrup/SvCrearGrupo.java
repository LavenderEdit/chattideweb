package com.chattide.web.Servlet.GestGrup;

import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Service.GrupoService;
import com.chattide.web.Utilities.Enum.TipoPrivacidad;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvCrearGrupo", urlPatterns = {"/SvCrearGrupo"})
public class SvCrearGrupo extends HttpServlet {

    @Inject
    GrupoService gs;

    @Override
    public void init() throws ServletException {
        this.gs = new GrupoService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String tipo = request.getParameter("tipo_privacidad");

        if (nombre == null || descripcion == null || tipo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos");
            return;
        }

        Grupo g = new Grupo();
        g.setNombre(nombre);
        g.setDescripcionText(descripcion);
        g.setTipoPrivacidad(TipoPrivacidad.valueOf(tipo.toUpperCase()));

        gs.create(g);
        response.sendRedirect("SvMisGrupos");
    }
}
