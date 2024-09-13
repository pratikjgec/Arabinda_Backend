package com.arobinda.webtoken;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arobinda.config.MyUserDetailsService;
import com.arobinda.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MyUserDetailsService myUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 String authHeader = request.getHeader("Authorization");
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	        
	        String jwt = authHeader.substring(7);
	        
	        String username = jwtService.extractUsername(jwt);
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
	            if (userDetails != null && jwtService.isTokenValid(jwt)) {
	                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	                        username,
	                        userDetails.getPassword(),
	                        userDetails.getAuthorities()
	                );
	                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	            } 
	            else {
	                sendErrorResponse(response, HttpStatus.UNAUTHORIZED.value(), "Token not valid");
	                return;
	            }
	           
	        }
 
	            filterChain.doFilter(request, response);
	        

	}

	private void sendErrorResponse(HttpServletResponse response, int status, String msg) throws IOException {
			response.setStatus(status);
		    response.setContentType("application/json");
		    ApiResponse apiResponse = new ApiResponse(status, msg);
		    ObjectMapper mapper = new ObjectMapper();
		    String jsonResponse = mapper.writeValueAsString(apiResponse);
		    response.getWriter().write(jsonResponse);
		    response.getWriter().flush();
		
	}
}
