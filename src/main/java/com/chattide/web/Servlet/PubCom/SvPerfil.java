package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.UsuarioDTO;
import com.chattide.web.Mapper.UsuarioMapper;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.PublicacionService;
import com.chattide.web.Service.UsuarioGrupoService;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
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
@WebServlet(name = "SvPerfil", urlPatterns = {"/SvPerfil"})
public class SvPerfil extends HttpServlet {

    @Inject
    UsuarioService usuarioService;
    @Inject
    UsuarioGrupoService ugService;
    @Inject
    PublicacionService pubService;

    @Override
    public void init() throws ServletException {
        this.usuarioService = new UsuarioService();
        this.ugService = new UsuarioGrupoService();
        this.pubService = new PublicacionService();
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

        String userIdParam = request.getParameter("userId");
        long userId;
        try {
            userId = Long.parseLong(userIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario inválido");
            return;
        }

        Usuario usuarioEnt = usuarioService.findById(userId);
        if (usuarioEnt == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            return;
        }
        UsuarioDTO usuarioDTO = UsuarioMapper.toDTO(usuarioEnt);

        long gruposCount = ugService.countByUsuario(userId);
        long publicacionesCount = pubService.countByUsuario(userId);

        request.setAttribute("usuarioPerfil", usuarioDTO);
        request.setAttribute("gruposCount", gruposCount);
        request.setAttribute("publicacionesCount", publicacionesCount);

        request.getRequestDispatcher("/perfil.jsp").forward(request, response);
    }
}
