package com.ftn.upp.security.auth;

import com.ftn.upp.security.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {

        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username;
        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            // uzmi username iz tokena
            username = tokenUtils.getUsernameFromToken(authToken);

            if (username != null) {
                // uzmi user-a na osnovu username-a
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // proveri da li je prosledjeni token validan
                if (tokenUtils.validateToken(authToken, userDetails)) {
                    // kreiraj autentifikaciju
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    authentication.setAuthenticated(true);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
