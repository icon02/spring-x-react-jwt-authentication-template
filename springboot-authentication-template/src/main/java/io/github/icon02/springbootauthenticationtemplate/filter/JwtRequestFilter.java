package io.github.icon02.springbootauthenticationtemplate.filter;

import io.github.icon02.springbootauthenticationtemplate.exception.JwtExpiredException;
import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.github.icon02.springbootauthenticationtemplate.service.UserService;
import io.github.icon02.springbootauthenticationtemplate.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Touched JwtRequestFilter");
        final String tokenHeader = request.getHeader("Authorization");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) System.out.println("Header  " + headerNames.nextElement());
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String jwt = tokenHeader.substring(7); // tokenHeader without "Bearer "
            String email = jwtUtil.extractUsername(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getByEmail(email);

                if (jwtUtil.isTokenExpired(jwt))
                    throw new JwtExpiredException(jwt, user);

                if (jwtUtil.validateToken(jwt, user)) {
                    // updates lastActive value in user
                    // userService.userMakesRequest(user);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
