package com.devtejas.student_transaction.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.concurrent.Executor;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig implements WebMvcConfigurer {
    @Autowired
    KeyCloakService keycloakService;

    @Autowired
    ServiceInterceptor interceptor;

    @Value("${common.threads}")
    private int commonThreadCount;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/student/**",
            "/course/**",
            "/studentfees/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(
                this.keycloakService.getAuthenticationManagers()::get);

        httpSecurity
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/message").permitAll()
                )
                .oauth2ResourceServer(oauth2Configurer -> oauth2Configurer
                        .authenticationManagerResolver(authenticationManagerResolver));

        startServer();

        return httpSecurity.build();
    }


//    @RequestScope
//    @Bean(name = "requestScopedUser")
//    public UserRequestScope requestScopedUser() {
//        return new UserRequestScope();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.interceptor);
    }

    @Bean("commonExecutor")
    Executor commonTaskExecutor() {
        log.warn("CommonExecutor thread count is {}", this.commonThreadCount);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.commonThreadCount);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("task-common-");
        executor.initialize();
        return executor;
    }

    public void startServer() {
        log.info("securityFilterChain: Start Server");
        keycloakService.initKeycloakRealms();
        log.info("securityFilterChain: End server");
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(true);

    }

    /*
     * @Bean
     * public WebMvcConfigurer corsConfigurer() {
     * return new WebMvcConfigurer() {
     * 
     * @Override
     * public void addCorsMappings(CorsRegistry registry) {
     * registry.addMapping("/api/**") // Specify the path pattern for which CORS
     * should be enabled, e.g., "/api/**" for all paths under "/api"
     * .allowedHeaders("*")
     * .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
     * .allowedOrigins("*");
     * }
     * };
     * }
     */

    /*
     * @Override
     * public void addCorsMappings(CorsRegistry registry) {
     * 
     * registry.addMapping("/api/**")
     * .allowedOrigins("http://127.0.0.1:4200","http://localhost:4200")
     * .allowedMethods("GET", "POST","PUT","DELETE","OPTIONS")
     * .allowedHeaders("*")
     * .allowCredentials(true).maxAge(3600);
     * 
     * // Add more mappings...
     * }
     */

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
