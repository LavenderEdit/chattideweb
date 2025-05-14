package com.chattide.web.Servlet.GestGrup;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.Service.GrupoService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvBuscarGrupos", urlPatterns = {"/SvBuscarGrupos"})
public class SvBuscarGrupos extends HttpServlet {

    @Inject
    GrupoService gs;

    @Override
    public void init() throws ServletException {
        this.gs = new GrupoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SvUtils.disableCache(response);

        var session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        Long usuarioId = ((com.chattide.web.Modelo.Usuario) session.getAttribute("usuario"))
                .getUsuarioID();

        List<GrupoDTO> listaGrupos = gs.findAvailableDTOs(usuarioId);
        request.setAttribute("listaGrupos", listaGrupos);
        getServletContext()
                .getRequestDispatcher("/buscarGrupos.jsp")
                .forward(request, response);
    }
}
