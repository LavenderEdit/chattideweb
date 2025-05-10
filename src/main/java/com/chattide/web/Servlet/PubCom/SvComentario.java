package com.chattide.web.Servlet.PubCom;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvComentario", urlPatterns = {"/SvComentario"})
public class SvComentario extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        // Aquí ira el instanciador a un servicio
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contenido = request.getParameter("contenido");
        Date fechaComentario = new Date();
    }
}
