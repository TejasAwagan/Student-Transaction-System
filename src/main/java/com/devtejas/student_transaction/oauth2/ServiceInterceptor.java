package com.devtejas.student_transaction.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ServiceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try{
            if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
                String username = jwt.getClaimAsString("preferred_username");
                System.out.println("Username from token: " + username);

                // You can also set this as request attribute for controllers to use
                request.setAttribute("username", username);
            }

            return true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return  false;
        }

    }
}
