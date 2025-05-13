package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.DTO.UsuarioDTO;
import com.chattide.web.Mapper.UsuarioMapper;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.GrupoService;
import com.chattide.web.Service.UsuarioGrupoService;
import com.chattide.web.Service.UsuarioService;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvMiGrupo", urlPatterns = {"/SvMiGrupo"})
public class SvMiGrupo extends HttpServlet {

    @Inject
    GrupoService grupoService;

    @Inject
    UsuarioGrupoService usuarioGrupoService;

    @Inject
    UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        this.grupoService = new GrupoService();
        this.usuarioGrupoService = new UsuarioGrupoService();
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        var session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Long usuarioId = ((Usuario) session.getAttribute("usuario")).getUsuarioID();

        Long grupoId = null;
        try {
            grupoId = Long.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de grupo inválido");
            return;
        }

        boolean pertenece = usuarioGrupoService.isMember(usuarioId, grupoId);
        if (!pertenece) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No perteneces a este grupo");
            return;
        }

        GrupoDTO grupoDTO = grupoService.findDTOById(grupoId);
        request.setAttribute("grupo", grupoDTO);

        var relaciones = usuarioGrupoService.findByGrupo(grupoId);
        List<UsuarioDTO> miembros = relaciones.stream()
                .map(ug -> usuarioService.findById(ug.getUsuario_grupo().getUsuarioID()))
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());

        request.setAttribute("miembros", miembros);

        getServletContext()
                .getRequestDispatcher("/miGrupo.jsp")
                .forward(request, response);
    }
}
