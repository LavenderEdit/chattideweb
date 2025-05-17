package com.chattide.web.Servlet.GestGrup;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioGrupoService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvUnirseGrupo", urlPatterns = {"/SvUnirseGrupo"})
public class SvUnirseGrupo extends HttpServlet {

    @Inject
    UsuarioGrupoService ugs;

    @Override
    public void init() throws ServletException {
        this.ugs = new UsuarioGrupoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SvUtils.disableCache(response);

        Long uid = ((Usuario) request.getSession().getAttribute("usuario")).getUsuarioID();
        Long gid = Long.valueOf(request.getParameter("id"));

        if (ugs.joinGroup(uid, gid)) {
            response.sendRedirect(request.getContextPath() + "/SvMisGrupos");
        } else {
            response.sendError(500, "No se pudo unir al grupo");
        }
    }
}
