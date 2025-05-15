package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.DTO.UsuarioDTO;
import com.chattide.web.Mapper.UsuarioMapper;
import com.chattide.web.Service.GrupoService;
import com.chattide.web.Service.PublicacionService;
import com.chattide.web.Service.UsuarioGrupoService;
import com.chattide.web.Service.UsuarioService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
    
    @Inject
    PublicacionService publicacionService;

    @Override
    public void init() throws ServletException {
        this.grupoService = new GrupoService();
        this.usuarioGrupoService = new UsuarioGrupoService();
        this.usuarioService = new UsuarioService();
        this.publicacionService   = new PublicacionService();
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

        Long grupoId = SvUtils.parseLongParam(request, "id", response);
        if (grupoId == null) return;

        GrupoDTO grupoDto = grupoService.findDTOById(grupoId);
        List<PublicacionDTO> publicaciones = publicacionService.findDTOByGrupo(grupoId);
        List<UsuarioDTO> miembros = usuarioGrupoService.findByGrupo(grupoId)
                                      .stream()
                                      .map(ug -> UsuarioMapper.toDTO(ug.getUsuario_grupo()))
                                      .collect(Collectors.toList());

        request.setAttribute("grupo", grupoDto);
        request.setAttribute("listaPublicaciones", publicaciones);
        request.setAttribute("miembros", miembros);

        if ("1".equals(request.getParameter("aj"))) {
            request.getRequestDispatcher("/fragments/publicacion-card.jsp")
               .forward(request, response);
        } else {
            request.getRequestDispatcher("/miGrupo.jsp")
               .forward(request, response);
        }
    }
}