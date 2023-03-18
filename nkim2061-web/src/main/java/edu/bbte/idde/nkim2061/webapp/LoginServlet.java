package edu.bbte.idde.nkim2061.webapp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String BASE_URL = "/idde-server";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String requestUsername = req.getParameter("username");
        String requestPassword = req.getParameter("password");
        if (requestUsername == null || requestPassword == null) {
            log.info("No username or password provided");
            res.sendRedirect("/idde-server/LoginPage.html");
            return;
        }
        if ("admin".equals(requestUsername) && "asd".equals(requestPassword)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", requestUsername);
            session.setAttribute("password", requestPassword);
            log.info("User logged in with success");
            res.sendRedirect(BASE_URL + "/real-estate-ads-template");
            return;
        }
        log.info("User failed to log in");
        res.sendRedirect(BASE_URL + "/WrongCredentials.html");
    }
}
