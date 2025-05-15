package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.ComentarioDTO;
import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.Service.ComentarioService;
import com.chattide.web.Service.PublicacionService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvPublicacion", urlPatterns = {"/SvPublicacion"})
public class SvPublicacion extends HttpServlet {

    private PublicacionService publicacionService;
    private ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        this.publicacionService = new PublicacionService();
        this.comentarioService = new ComentarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SvUtils.disableCache(response);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Long publId = SvUtils.parseLongParam(request, "id", response);
        if (publId == null) {
            return;
        }

        PublicacionDTO pubDto = publicacionService.findDTOById(publId);
        if (pubDto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Publicación no encontrada");
            return;
        }
        List<ComentarioDTO> comentarios = comentarioService.findByPublicacion(publId);
        request.setAttribute("publicacion", pubDto);
        request.setAttribute("listaComentarios", comentarios);

        if ("1".equals(request.getParameter("aj"))) {
            request.getRequestDispatcher("/fragments/comentario-list.jsp")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher("/publicacion.jsp")
                    .forward(request, response);
        }
    }
}
