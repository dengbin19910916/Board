package com.wrox.filters;

import com.wrox.entities.UserPrincipal;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

/**
 * 当用户未登录时被重定向到登录界面。
 *
 * Created by dengb on 2015/9/9.
 */
@WebFilter(filterName = "authenticationFilter", urlPatterns = {"/chat", "/chat/*", "/ticket", "/ticket/*", "/session", "/session/*"})
public class AuthenticationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        final Principal principal = UserPrincipal.getPrincipal(session);
        if (principal == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
        } else chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) request) {
                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }
        }, response);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
