/*package org.example.webApplicationShopSpringBoot.connector;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.webApplicationShopSpringBoot.model.user.Role;

import java.io.IOException;

import static org.example.webApplicationShopSpringBoot.util.NamesUtil.*;


@WebFilter("/*")
public class Filter implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession(false);

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            if (path.equals(LOGIN_JSP) || path.equals("/" + REGISTRATION_JSP) ||
                    (path.equals("/" + AUTHORIZE_USER)) || path.endsWith(SAVE_OR_UPDATE_USER)
                    || path.endsWith(LOGOUT_USER)) {
                chain.doFilter(request, response);
            } else if (session != null && session.getAttribute("userId") != null) {
                Role role = (Role) session.getAttribute("userRole");
                if ((path.startsWith("/client") || path.startsWith("/universal")) && role.name().equals("CLIENT") ||
                        path.startsWith("/administrator") && role.name().equals("ADMINISTRATOR") ||
                        (path.startsWith("/operator") || path.startsWith("/universal")) && role.name().equals("OPERATOR")) {
                    chain.doFilter(request, response);
                } else {
                    HttpServletResponse response1 = (HttpServletResponse) response;
                    response1.sendRedirect(httpRequest.getContextPath() + LOGIN_JSP);
                }
            } else {
                HttpServletResponse response1 = (HttpServletResponse) response;
                response1.sendRedirect(httpRequest.getContextPath() + LOGIN_JSP);
            }
        } finally {
            HibernateUtil.closeEntityManager();
        }
    }
}*/
