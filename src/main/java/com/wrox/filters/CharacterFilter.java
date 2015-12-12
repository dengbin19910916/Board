package com.wrox.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤所有的请求，使其字符编码为指定编码。
 *
 * Created by dengb on 2015/8/24.
 */
@WebFilter(filterName = "characterFilter", urlPatterns = "/*", initParams = @WebInitParam(name = "encoding", value = "utf-8"))
public class CharacterFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setDateHeader("Expires", -1);
            ((HttpServletResponse) response).setHeader("Cache_Control", "no-cache");
            ((HttpServletResponse) response).setHeader("Pragma", "no-cache");
        }

        if (this.encoding != null) {
            request.setCharacterEncoding(encoding);
//            response.setContentType("text/html;charset=" + encoding);   // 让浏览器用UTF-8来解析返回的数据
            response.setCharacterEncoding(encoding);    // Servlet用UTF-8转码
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encoding = null;
    }
}
