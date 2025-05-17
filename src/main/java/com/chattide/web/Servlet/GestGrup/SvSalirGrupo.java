package com.chattide.web.Servlet.GestGrup;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioGrupoService;
import com.chattide.web.Utilities.Mensajes;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvSalirGrupo", urlPatterns = {"/SvSalirGrupo"})
public class SvSalirGrupo extends HttpServlet {

    @Inject
    UsuarioGrupoService ugs;

    @Override
    public void init() throws ServletException {
        this.ugs = new UsuarioGrupoService();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        Long grupoId = SvUtils.parseLongParam(request, "id", response);
        if (grupoId == null) {
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        long usuarioId = usuario.getUsuarioID();

        boolean ok = ugs.leaveGroup(usuarioId, grupoId);
        if (ok) {
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.SALIO_DEL_GRUPO);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_ELIMINAR_GRUPO);
        }
    }
}
