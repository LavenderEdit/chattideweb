package com.chattide.web.Servlet.PubCom;

import com.chattide.web.Service.LikeService;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvLikeCount", urlPatterns = {"/SvLikeCount"})
public class SvLikeCount extends HttpServlet {

    @Inject
    private LikeService ls;

    public void init() {
        ls = new LikeService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SvUtils.disableCache(response);
        response.setContentType("application/json;charset=UTF-8");
        Long pubId = SvUtils.parseLongParam(request, "idPub", response);
        if (pubId == null) {
            return;
        }
        long count = ls.countByPublication(pubId);
        SvUtils.respondWithJsonObject(response,
                HttpServletResponse.SC_OK, true, "OK", Map.of(), Map.of("count", count));
    }
}
