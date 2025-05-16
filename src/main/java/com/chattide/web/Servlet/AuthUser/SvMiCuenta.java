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
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuario = us.findById(usuario.getUsuarioID());

        ServletContext ctx = getServletContext();

        UsuarioDTO dto = UsuarioMapper.toDTO(usuario);
        dto.setAvatar(SvUtils.normalizeAvatar(ctx, dto.getAvatar()));
        session.setAttribute("usuarioDTO", dto);

        getServletContext()
                .getRequestDispatcher("/miCuenta.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        String nuevoNombre = request.getParameter("nombre");
        String nuevoEmail = request.getParameter("email");

        if (SvUtils.isNullOrEmpty(nuevoNombre, nuevoEmail)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        Usuario oldUser = (Usuario) session.getAttribute("usuario");

        if (!nuevoEmail.equals(oldUser.getEmail()) && us.findByEmail(nuevoEmail) != null) {
            response.sendError(HttpServletResponse.SC_CONFLICT, Mensajes.USUARIO_EMAIL_EXISTE);
            return;
        }

        Part avatarPart = request.getPart("avatar");
        String avatarUrl = oldUser.getAvatar();
        if (avatarPart != null && avatarPart.getSize() > 0) {
            String rel = SvUtils.saveUploadedFile(
                    avatarPart,
                    getServletContext().getRealPath("/images/Subidas")
            );
            avatarUrl = request.getContextPath() + rel;
        }

        oldUser.setNombre(nuevoNombre);
        oldUser.setEmail(nuevoEmail);
        oldUser.setAvatar(avatarUrl);

        boolean ok = us.update(oldUser);
        if (!ok) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_SERVIDOR);
            return;
        }

        SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_DATOS_MODIFICADOS);
    }
}
