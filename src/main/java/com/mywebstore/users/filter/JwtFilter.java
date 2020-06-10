package com.mywebstore.users.filter;

import com.mywebstore.users.config.MyUserDetailsService;
import com.mywebstore.users.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
          final String authorizationHeader = httpServletRequest.getHeader("Authorization");

          String username = null;
          String jwt_token = null;
          if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){//Extract the username and the jwt
              jwt_token = authorizationHeader.substring(7);
              username = jwtUtil.extractUsername(jwt_token);
          }
          if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){//Check if the username exists and it's not been authenticated before
              UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(jwt_token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
          }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
