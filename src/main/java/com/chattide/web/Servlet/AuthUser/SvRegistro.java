package com.chattide.web.Servlet.AuthUser;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import com.chattide.web.Utilities.Mensajes;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet(name = "SvRegistro", urlPatterns = {"/SvRegistro"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class SvRegistro extends HttpServlet {

    @Inject
    UsuarioService us;

    @Override
    public void init() throws ServletException {
        this.us = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (SvUtils.isNullOrEmpty(nombre, email, password)) {
            response.sendRedirect("registro.jsp?error=" + Mensajes.CAMPOS_VACIOS);
            return;
        }

        if (us.findByEmail(email) != null) {
            response.sendRedirect("registro.jsp?error=" + Mensajes.EMAIL_DUPLICADO);
            return;
        }

        Part filePart = request.getPart("avatar");
        String avatarUrl = null;
        if (filePart != null && filePart.getSize() > 0) {
            String relative = SvUtils.saveUploadedFile(
                    filePart,
                    getServletContext().getRealPath("/images/Subidas")
            );
            avatarUrl = request.getContextPath() + relative;
        } else {
            avatarUrl = request.getContextPath() + "/images/Usuario/DefaultUserAvatar.webp";
        }

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setContrasenia(password);
        u.setAvatar(avatarUrl);

        boolean registroOk = us.create(u);

        if (registroOk) {
            response.sendRedirect("login.jsp?mensaje=" + Mensajes.REGISTRO_EXITOSO);
        } else {
            response.sendRedirect("registro.jsp?error=" + Mensajes.ERROR_REGISTRO);
        }
    }
}
