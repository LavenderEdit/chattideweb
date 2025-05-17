package com.chattide.web.Servlet.PubCom;

import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.LikeService;
import com.chattide.web.Service.PublicacionService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import com.chattide.web.Utilities.Mensajes;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvLike", urlPatterns = {"/SvLike"})
@MultipartConfig
public class SvLike extends HttpServlet {

    @Inject
    LikeService ls;

    @Inject
    PublicacionService ps;

    @Override
    public void init() throws ServletException {
        this.ls = new LikeService();
        this.ps = new PublicacionService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Long pubId = SvUtils.parseLongParam(request, "idPub", response);
        if (pubId == null) {
            return;
        }

        Publicacion pub = ps.findById(pubId);
        if (pub == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_NOT_FOUND,
                    Mensajes.ERROR_SERVIDOR);
            return;
        }

        try {
            Likes lk = new Likes();
            lk.setUsuario(usuario);
            lk.setPublicacion(pub);
            ls.create(lk);

            long total = ls.countByPublication(pubId);
            Map<String, Object> data = Map.of(
                    "newLikeId", lk.getLikesID(),
                    "newCount", total
            );
            SvUtils.respondWithJsonObject(response,
                    HttpServletResponse.SC_CREATED,
                    true,
                    Mensajes.LIKE,
                    Map.of(),
                    data
            );
        } catch (IOException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof java.sql.SQLIntegrityConstraintViolationException) {
                SvUtils.respondWithError(response,
                        HttpServletResponse.SC_CONFLICT,
                        Mensajes.LIKE_YA_PUESTO);
            } else {
                SvUtils.respondWithError(response,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        Mensajes.ERROR_SERVIDOR);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED,
                    Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Long pubId = SvUtils.parseLongParam(request, "idPub", response);
        if (pubId == null) {
            return;
        }

        Optional<Likes> likeOpt = ls.findByUserAndPublication(usuario.getUsuarioID(), pubId);
        if (likeOpt.isEmpty()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_NOT_FOUND,
                    Mensajes.LIKE_QUITADO_YA);
            return;
        }

        Likes like = likeOpt.get();
        boolean deleted = ls.deleteLike(like.getLikesID());
        if (!deleted) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    Mensajes.ERROR_SERVIDOR);
            return;
        }

        long total = ls.countByPublication(pubId);
        Map<String, Object> data = Map.of("newCount", total);

        SvUtils.respondWithJsonObject(response,
                HttpServletResponse.SC_OK,
                true,
                Mensajes.NO_LIKE,
                Map.of(),
                data
        );
    }
}
