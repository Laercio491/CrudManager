
package controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain filterChain)
            throws IOException, ServletException {

        var httpReq = (HttpServletRequest) req;
        var httpResp = (HttpServletResponse) resp;

        System.out.println("Filtro: " + httpReq.getRequestURI());

        boolean instrutorIsLogged =
                httpReq.getSession(false) != null &&
                httpReq.getSession(false).getAttribute("instrutor_logged") != null;

        String uri = httpReq.getRequestURI();

        boolean isPublicResource =
                uri.endsWith(".css") ||
                uri.endsWith(".js") ||
                uri.endsWith(".png") ||
                uri.endsWith(".jpg") ||
                uri.endsWith(".jpeg") ||
                uri.endsWith(".ico");

        boolean isLogin =
                httpReq.getServletPath().equals("/login");

        boolean isLogout =
                httpReq.getServletPath().equals("/logout");

        if (instrutorIsLogged || isLogin || isLogout || isPublicResource) {
            filterChain.doFilter(httpReq, httpResp);
        } else {
            httpResp.sendRedirect(httpReq.getContextPath() + "/login");
        }
    }
}
