package com.chattide.web.Servlet.GestGrup;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioGrupoService;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado");
        }
        String param = request.getParameter("id");
        if (param == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta id de grupo");
            return;
        }

        long grupoId;
        long usuarioId;
        try {
            grupoId = Long.parseLong(param);
            usuarioId = ((Usuario) session.getAttribute("usuario")).getUsuarioID();
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID Invalido");
            return;
        }

        boolean ok = ugs.leaveGroup(usuarioId, grupoId);

        if (ok) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo salir del grupo");
        }
    }
}
