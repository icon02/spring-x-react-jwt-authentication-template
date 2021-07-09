package io.github.icon02.springbootauthenticationtemplate.config;

import io.github.icon02.springbootauthenticationtemplate.filter.CorsFilter;
import io.github.icon02.springbootauthenticationtemplate.filter.JwtRequestFilter;
import io.github.icon02.springbootauthenticationtemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;
    private final CorsFilter corsFilter;
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter, UserService userService, CorsFilter corsFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userService = userService;
        this.corsFilter = corsFilter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                    "/auth/**",
                    "/public/**",
                    "/test/**",
                    "/account/create"
                );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll() // required for CORS-preflight
                .antMatchers("/admin/**").hasAuthority("admin")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(corsFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }
}
