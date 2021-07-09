package io.github.icon02.springbootauthenticationtemplate.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        // to allow jwt-token in header
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Authorization");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "authorization");

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
