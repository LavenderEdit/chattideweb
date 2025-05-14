package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.ComentarioDTO;
import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.Service.ComentarioService;
import com.chattide.web.Service.PublicacionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvPublicacion", urlPatterns = {"/SvPublicacion"})
public class SvPublicacion extends HttpServlet {

    PublicacionService publicacionService;
    ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        this.publicacionService = new PublicacionService();
        this.comentarioService = new ComentarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        Long publId;
        try {
            publId = Long.parseLong(idParam);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            return;
        }
        
        PublicacionDTO pubDto = publicacionService.findDTOById(publId);
        if (pubDto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Publicación no encontrada");
            return;
        }
        
        List<ComentarioDTO> comentarios = comentarioService
            .findByPublicacion(publId);
        
        request.setAttribute("publicacion", pubDto);
        request.setAttribute("listaComentarios", comentarios);
        request.getRequestDispatcher("/publicacion.jsp")
           .forward(request, response);
    }

}
