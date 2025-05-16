package com.chattide.web.Servlet.AuthUser;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import com.chattide.web.Utilities.Mensajes;

import java.io.IOException;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    @Inject
    UsuarioService us;

    @Override
    public void init() throws ServletException {
        this.us = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String correo = request.getParameter("email");
        String contra = request.getParameter("password");

        if (SvUtils.isNullOrEmpty(correo, contra)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        Optional<Usuario> optUsuario = SvUtils.findUsersByEmail(correo, us.findAll());
        if (!optUsuario.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_INEXISTENTE);
            return;
        }

        Usuario user = optUsuario.get();
        if (!user.checkContrasenia(contra)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_CONTRA_INCORRECTA);
            return;
        }

        ServletContext ctx = getServletContext();
        String normalized = SvUtils.normalizeAvatarLogin(ctx, user.getAvatar());
        user.setAvatar(normalized);

        request.getSession().setAttribute("usuario", user);
        SvUtils.respondWithSuccess(
                response,
                HttpServletResponse.SC_OK,
                Mensajes.USUARIO_LOGEADO + user.getNombre()
        );
    }
}
