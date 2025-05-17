package com.chattide.web.Servlet.PubCom;

import com.chattide.web.DTO.ComentarioDTO;
import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.ComentarioService;
import com.chattide.web.Service.LikeService;
import com.chattide.web.Service.PublicacionService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvPublicacion", urlPatterns = {"/SvPublicacion"})
public class SvPublicacion extends HttpServlet {

    @Inject
    private PublicacionService publicacionService;

    @Inject
    private ComentarioService comentarioService;

    @Inject
    private LikeService likeService;

    @Override
    public void init() throws ServletException {
        this.publicacionService = new PublicacionService();
        this.comentarioService = new ComentarioService();
        this.likeService = new LikeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SvUtils.disableCache(response);
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Long publId = SvUtils.parseLongParam(request, "id", response);
        if (publId == null) {
            return;
        }

        PublicacionDTO pubDto = publicacionService.findDTOById(publId);
        if (pubDto == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Publicación no encontrada");
            return;
        }

        List<ComentarioDTO> comentarios = comentarioService.findByPublicacion(publId);

        Optional<Likes> optLike = likeService.findByUserAndPublication(usuario.getUsuarioID(), publId);
        Long userLikeId = optLike.map(Likes::getLikesID).orElse(null);

        ServletContext ctx = getServletContext();

        for (ComentarioDTO c : comentarios) {
            c.setAvatarUsuarioUrl(SvUtils.normalizeAvatarLogin(ctx, c.getAvatarUsuarioUrl()));
        }

        request.setAttribute("publicacion", pubDto);
        request.setAttribute("listaComentarios", comentarios);
        request.setAttribute("userLikeId", userLikeId);
        request.setAttribute("likeCount", pubDto.getLikeCount());

        if ("1".equals(request.getParameter("aj"))) {
            request.getRequestDispatcher("/fragments/comentario-list.jsp")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher("/publicacion.jsp")
                    .forward(request, response);
        }
    }
}
