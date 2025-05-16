package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.DTO.UsuarioDTO;
import com.chattide.web.Mapper.UsuarioMapper;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.GrupoService;
import com.chattide.web.Service.PublicacionService;
import com.chattide.web.Service.UsuarioGrupoService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;

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
    PublicacionService publicacionService;

    @Override
    public void init() throws ServletException {
        this.grupoService = new GrupoService();
        this.usuarioGrupoService = new UsuarioGrupoService();
        this.publicacionService = new PublicacionService();
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
        if (grupoId == null) {
            return;
        }

        GrupoDTO grupoDto = grupoService.findDTOById(grupoId);

        Long usuarioId = ((Usuario) session.getAttribute("usuario")).getUsuarioID();
        List<PublicacionDTO> publicaciones = publicacionService
                .findDTOByGrupoYUsuario(grupoId, usuarioId);

        List<UsuarioDTO> miembros = usuarioGrupoService.findByGrupo(grupoId).stream()
                .map(ug -> UsuarioMapper.toDTO(ug.getUsuario_grupo()))
                .collect(Collectors.toList());

        ServletContext ctx = getServletContext();
        String ctxPath = request.getContextPath();
        long timestamp = System.currentTimeMillis();

        for (UsuarioDTO u : miembros) {
            String a = u.getAvatar();
            if (a != null && !a.isBlank()) {
                String rel = a.startsWith(ctxPath)
                        ? a.substring(ctxPath.length())
                        : a;
                String realPath = ctx.getRealPath(rel);
                if (realPath != null && new File(realPath).isFile()) {
                    u.setAvatar(ctxPath + rel + "?v=" + timestamp);
                } else {
                    u.setAvatar(null);
                }
            }
        }

        for (PublicacionDTO p : publicaciones) {
            String aa = p.getAutorAvatar();
            if (aa != null && !aa.isBlank()) {
                String rel = aa.startsWith(ctxPath)
                        ? aa.substring(ctxPath.length())
                        : aa;
                String realPath = ctx.getRealPath(rel);
                if (realPath != null && new File(realPath).isFile()) {
                    p.setAutorAvatar(ctxPath + rel + "?v=" + timestamp);
                } else {
                    p.setAutorAvatar(null);
                }
            }
        }

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
