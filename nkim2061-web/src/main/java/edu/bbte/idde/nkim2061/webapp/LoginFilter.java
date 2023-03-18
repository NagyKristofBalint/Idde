package edu.bbte.idde.nkim2061.webapp;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter("/real-estate-ads-template/*")
public class LoginFilter extends HttpFilter {
    private static final String BASE_URL = "/idde-server";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        log.info("Filtering request");
        HttpSession session = req.getSession();
        if (session.getAttribute("username") != null && session.getAttribute("password") != null) {
            log.info("User is logged in");
            chain.doFilter(req, res);
            return;
        }
        log.info("User is not logged in");
        res.sendRedirect(BASE_URL + "/LoginPage.html");
    }
}
