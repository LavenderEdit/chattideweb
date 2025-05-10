package com.chattide.web.Servlet.PubCom;

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
@WebServlet(name = "SvLike", urlPatterns = {"/SvLike"})
public class SvLike extends HttpServlet {

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

    }
}
