package com.chattide.web.Servlet.PubCom;

import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.ComentarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvComentario", urlPatterns = {"/SvComentario"})
@MultipartConfig
public class SvComentario extends HttpServlet {

    private ComentarioService comentarioService;

    @Override
    public void init() throws ServletException {
        this.comentarioService = new ComentarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Debe iniciar sesión");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String contenido = request.getParameter("contenido");
        Long publicacionId = SvUtils.parseLongParam(request, "idPublicacion", response);

        if (publicacionId == null || contenido == null || contenido.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos inválidos");
            return;
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setPublicacionID(publicacionId);

        Comentario coment = new Comentario();
        coment.setContenido(contenido.trim());
        coment.setAutor(usuario);
        coment.setPublicacion(publicacion);

        boolean creado = comentarioService.create(coment);

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (isAjax) {
            response.setContentType("application/json;charset=UTF-8");
            if (creado) {
                SvUtils.respondWithJson(response,
                        HttpServletResponse.SC_CREATED,
                        true,
                        "Comentario creado con éxito",
                        Map.of(
                                "id", coment.getComentarioID(),
                                "contenido", coment.getContenido(),
                                "fecha", coment.getFechaComentarioDate()
                        )
                );
            } else {
                SvUtils.respondWithJson(response,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        false,
                        "Error creando comentario",
                        null
                );
            }
        } else {
            if (!creado) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Ocurrió un error al crear el comentario");
            } else {
                response.sendRedirect(request.getContextPath() + "/SvPublicacion?id=" + publicacionId);
            }
        }
    }
}
