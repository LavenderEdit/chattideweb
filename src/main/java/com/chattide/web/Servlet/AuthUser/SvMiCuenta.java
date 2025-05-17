package com.chattide.web.Servlet.AuthUser;

import com.chattide.web.DTO.UsuarioDTO;
import com.chattide.web.Mapper.UsuarioMapper;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import com.chattide.web.Utilities.Mensajes;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvMiCuenta", urlPatterns = {"/SvMiCuenta"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class SvMiCuenta extends HttpServlet {

    @Inject
    UsuarioService us;

    @Override
    public void init() throws ServletException {
        this.us = new UsuarioService();
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

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuario = us.findById(usuario.getUsuarioID());

        UsuarioDTO dto = UsuarioMapper.toDTO(usuario);
        String avatarUrl = dto.getAvatar();
        if (avatarUrl != null && !avatarUrl.isBlank()) {
            dto.setAvatar(SvUtils.normalizeAvatarLogin(getServletContext(), avatarUrl));
        }

        session.setAttribute("usuario", usuario);
        session.setAttribute("usuarioDTO", dto);

        request.getRequestDispatcher("/miCuenta.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SvUtils.disableCache(response);
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        long userId = ((Usuario) session.getAttribute("usuario")).getUsuarioID();
        String newName = request.getParameter("nombre");
        String newMail = request.getParameter("email");

        if (SvUtils.isNullOrEmpty(newName, newMail)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        Usuario existing = us.findByEmail(newMail);
        if (existing != null && existing.getUsuarioID() != userId) {
            response.sendError(HttpServletResponse.SC_CONFLICT, Mensajes.USUARIO_EMAIL_EXISTE);
            return;
        }

        Part avatarPart = request.getPart("avatar");
        String avatarUrl = null;
        if (avatarPart != null && avatarPart.getSize() > 0) {
            String rel = SvUtils.saveUploadedFile(
                    avatarPart,
                    getServletContext().getRealPath("/images/Subidas")
            );
            avatarUrl = request.getContextPath() + rel;
        }

        boolean ok = us.updateBasicInfo(userId, newName, newMail, avatarUrl);
        if (!ok) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_SERVIDOR);
            return;
        }

        Usuario updated = us.findById(userId);
        UsuarioDTO dto = UsuarioMapper.toDTO(updated);
        if (dto.getAvatar() != null && !dto.getAvatar().isBlank()) {
            dto.setAvatar(SvUtils.normalizeAvatarLogin(getServletContext(), dto.getAvatar()));
        }

        session.setAttribute("usuario", updated);
        session.setAttribute("usuarioDTO", dto);

        SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_DATOS_MODIFICADOS);
    }
}
