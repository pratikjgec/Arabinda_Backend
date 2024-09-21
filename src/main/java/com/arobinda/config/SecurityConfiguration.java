package com.arobinda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.arobinda.webtoken.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
        	.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Enable CORS
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(registry -> {
                // Allow access to static resources
                registry.requestMatchers("/images/**", "/css/**", "/js/**").permitAll();
                registry.requestMatchers("/authenticate","/home","/complain","/health","/generateOTP","/complainRegister","/getAboutUs","/getNotice","/getComplainByComplainId").permitAll();
                registry.requestMatchers("/admin/**").hasRole("ADMIN");
                registry.requestMatchers("/user/**").hasRole("USER");
                registry.anyRequest().authenticated();
            })
            .formLogin(AbstractAuthenticationFilterConfigurer::disable)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);  // Allow credentials like cookies
        configuration.addAllowedOriginPattern("*");  // Allow all origins (use addAllowedOrigin() for specific origins)
        configuration.addAllowedHeader("*");  // Allow all headers
        configuration.addAllowedMethod("*");  // Allow all HTTP methods (GET, POST, etc.)
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply CORS settings to all endpoints
        return source;
    }
    
    @Bean
     AuthenticationManager authenticationManager() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	      provider.setUserDetailsService(userDetailsService);
	      provider.setPasswordEncoder(passwordEncoder());
	    //  return provider;
        return new ProviderManager(provider);
    }
     
    @Bean
     UserDetailsService userDetailsService() {
        return userDetailsService;
    }

//    @Bean
//     AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    @Bean
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
