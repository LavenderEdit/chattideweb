package com.chattide.web.Servlet.PubCom;

import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.PublicacionService;

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
@WebServlet(name = "SvPublicar", urlPatterns = {"/SvPublicar"})
public class SvPublicar extends HttpServlet {

    PublicacionService publicacionService;
    //ComentarioService comentarioService;
    //UsuarioGrupoService ugService;

    @Override
    public void init() throws ServletException {
        this.publicacionService = new PublicacionService();
        //this.comentarioService = new ComentarioService();
        //this.ugService = new UsuarioGrupoService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String contenido = request.getParameter("contenido");
        String idGrupo = request.getParameter("idGrupo");
        if (contenido == null || contenido.isBlank() || idGrupo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Faltan parámetros: contenido o idGrupo");
            return;
        }

        long grupoId;
        try {
            grupoId = Long.parseLong(idGrupo);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "El parámetro 'idGrupo' no es un número válido.");
            return;
        }

        Publicacion pub = new Publicacion();
        pub.setContenidoText(contenido);
        pub.setFechaPublicacion(new Date());
        pub.setUsuario_publicacion(usuario);
        pub.setGrupo_publicacion(new com.chattide.web.Modelo.Grupo(grupoId));

        boolean ok = publicacionService.create(pub);
        if (!ok) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo crear la publicación.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/SvMiGrupo?id=" + grupoId);
    }
}
