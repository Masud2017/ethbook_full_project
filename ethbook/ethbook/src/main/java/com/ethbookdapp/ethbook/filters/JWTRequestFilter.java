package com.ethbookdapp.ethbook.filters;

import com.ethbookdapp.ethbook.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(JWTRequestFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String remoteIpAddress = request.getHeader("X-FORWARDED-FOR");
        String remoteAddress = request.getRemoteAddr();

        this.logger.info("Getting request from the ip address : ", remoteAddress);

        final RequestMatcher ignoredPathsUsers = new AntPathRequestMatcher("/users");
        final RequestMatcher ignoredPathsIndex = new AntPathRequestMatcher("/index");
        final RequestMatcher ignoredPathsAuth = new AntPathRequestMatcher("/auth");
        final RequestMatcher ignoredPathsRegister = new AntPathRequestMatcher("/register");
//        final RequestMatcher ignoredPathsRegistration = new AntPathRequestMatcher("/registration");


        if (ignoredPathsUsers.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (ignoredPathsIndex.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (ignoredPathsAuth.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (ignoredPathsRegister.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

//        if (ignoredPathsRegistration.matches(request)) {
//            filterChain.doFilter(request, response);
//            return;
//        }


        String jwtToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;


        if (jwtToken.contains("Bearer")) {
            String[] splitedToken = jwtToken.split(" ");
            token = splitedToken[1];
            userName = this.jwtUtil.getUsernameFromToken(token);

            // now I have to parse the token for the user info

        } else {
            logger.warn("Token does not begin with beaerer");
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            if (this.jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request,response);
    }
}
