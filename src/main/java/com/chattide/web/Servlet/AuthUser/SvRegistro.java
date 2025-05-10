package com.chattide.web.Servlet.AuthUser;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvRegistro", urlPatterns = {"/SvRegistro"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class SvRegistro extends HttpServlet {

    UsuarioService us;

    @Override
    public void init() throws ServletException {
        this.us = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Aquí ira cualquier otra lógica necesaria
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Part filePart = request.getPart("avatar");
        String avatarPath = SvUtils.guardarArchivo(request, filePart, "images/Subidas");

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setContrasenia(password);
        u.setAvatar(avatarPath);

        boolean registroExitoso = us.create(u);

        if (registroExitoso) {
            response.sendRedirect("login.jsp?mensaje=Registro exitoso, inicia sesión");
        } else {
            response.sendRedirect("registro.jsp?error=Error al registrar usuario");
        }
    }
}
