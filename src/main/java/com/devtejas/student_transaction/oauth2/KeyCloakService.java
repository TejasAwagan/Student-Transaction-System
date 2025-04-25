package com.devtejas.student_transaction.oauth2;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class KeyCloakService {
    Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();

    @Value("${realms}")
    private List<String> realms;

    @Value("${authServerUrl}")
    private String authServerUrl;

    @Async(value = "commonExecutor")
    public void initKeycloakRealms() {
        realms.forEach(item -> {
            System.out.println("Realm: " + item);
            try {
                addManager(getAuthenticationManagers(),
                        authServerUrl + "realms/" + item);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        });

    }

    public void addManager(Map<String, AuthenticationManager> authenticationManagers, String issuer) {

        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(
                JwtDecoders.fromIssuerLocation(issuer));
        authenticationManagers.put(issuer, authenticationProvider::authenticate);
    }

}


