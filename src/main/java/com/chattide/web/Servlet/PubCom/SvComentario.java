package com.chattide.web.Servlet.PubCom;

import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.ComentarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvComentario", urlPatterns = {"/SvComentario"})
public class SvComentario extends HttpServlet {

    ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        this.comentarioService = new ComentarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contenido = request.getParameter("contenido");
        Date fechaComentario = new Date();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String contenidoCo = request.getParameter("contenido");
        String idPub = request.getParameter("idPublicacion");
        if (contenido == null || contenido.isBlank() || idPub == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Parámetros 'contenido' o 'idPublicacion' faltantes o vacíos.");
            return;
        }

        long publicacionId;
        try {
            publicacionId = Long.parseLong(idPub);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "El parámetro 'idPublicacion' no es un número válido.");
            return;
        }

        Comentario comentario = new Comentario();
        comentario.setContenidoText(contenido);
        comentario.setFechaComentario(new Date());
        comentario.setUsuario_comentario(usuario);
        comentario.setPublicacion_comentario(new Publicacion(publicacionId));

        boolean creado = comentarioService.create(comentario);

        response.setContentType("application/json;charset=UTF-8");
        if (creado) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(
                    "{\"success\":true,\"message\":\"Comentario creado con éxito.\"}"
            );
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al crear el comentario.");
        }
    }
}
