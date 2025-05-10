package com.chattide.web.Servlet.GestGrup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvCrearGrupo", urlPatterns = {"/SvCrearGrupo"})
public class SvCrearGrupo extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // Aquí ira un inicializador de un servicio
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String tipoPrivacidad = request.getParameter("tipo_privacidad");
    }
}
