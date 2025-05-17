package com.chattide.web.Servlet.GestGrup;

import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.GrupoService;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.Enum.TipoPrivacidad;
import com.chattide.web.Utilities.Mensajes;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvCrearGrupo", urlPatterns = {"/SvCrearGrupo"})
@MultipartConfig
public class SvCrearGrupo extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(SvCrearGrupo.class.getName());

    @Inject
    private GrupoService grupoService;

    @Inject
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        if (grupoService == null) {
            grupoService = new GrupoService();
        }
        if (usuarioService == null) {
            usuarioService = new UsuarioService();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String tipo = request.getParameter("tipo_privacidad");

        if (SvUtils.isNullOrEmpty(nombre, descripcion, tipo)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        Usuario sessUser = (Usuario) session.getAttribute("usuario");
        Usuario creador = usuarioService.findById(sessUser.getUsuarioID());
        if (creador == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.ERROR_USUARIO_NO_ENCONTRADO);
            return;
        }

        Grupo grupo = new Grupo();
        grupo.setNombre(nombre.trim());
        grupo.setDescripcion(descripcion.trim());
        try {
            grupo.setTipoPrivacidad(TipoPrivacidad.valueOf(tipo.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, "Tipo de privacidad inválido");
            return;
        }

        boolean ok = grupoService.createGrupoWithConnection(grupo, creador);
        if (ok) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.GRUPO_CREADO);
            } else {
                response.sendRedirect("/SvMisGrupos");
            }
        } else {
            LOG.log(Level.SEVERE, "Fallo al crear grupo para usuario {0}", creador.getUsuarioID());
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_CREAR_GRUPO);
        }
    }
}
