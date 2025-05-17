package com.chattide.web.Servlet.PubCom;

import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.PublicacionService;
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
@WebServlet(name = "SvPublicar", urlPatterns = {"/SvPublicar"})
@MultipartConfig
public class SvPublicar extends HttpServlet {

    private PublicacionService publicacionService;

    @Override
    public void init() {
        this.publicacionService = new PublicacionService();
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
        String idParam = request.getParameter("idGrupo");
        Long grupoId = Long.valueOf(idParam);
        if (grupoId == null || contenido == null || contenido.isBlank()) {
            return;
        }

        Publicacion pub = new Publicacion();
        pub.setContenido(contenido.trim());
        pub.setAutor(usuario);
        pub.setGrupo(new Grupo(grupoId));

        boolean created = publicacionService.create(pub);

        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        if (isAjax) {
            response.setContentType("application/json;charset=UTF-8");
            if (created) {
                SvUtils.respondWithJson(response, HttpServletResponse.SC_OK, true,
                        "Publicación creada", Map.of(
                                "id", pub.getPublicacionID(),
                                "contenido", pub.getContenido(),
                                "fecha", pub.getFechaPublicacion()
                        ));
            } else {
                SvUtils.respondWithJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        false, "Error creando publicación", null);
            }
        } else {
            if (!created) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "No se pudo crear la publicación.");
            } else {
                response.sendRedirect(request.getContextPath() + "/SvMiGrupo?id=" + grupoId);
            }
        }
    }
}
