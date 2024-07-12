package com.explore.gateway.filter;

import com.explore.gateway.service.JwtService;
import com.explore.gateway.validator.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtService jwtService;

    public JwtAuthFilter() {
        super((Config.class));
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Required authorization header");
                }

                String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if(header != null && header.startsWith("Bearer ")){
                    header = header.substring(7);
                }
                try{
                    jwtService.validateToken(header);
                    request = exchange.getRequest()
                            .mutate()
                            .header("username",jwtService.extractUsername(header))
                            .header("role", jwtService.extractRoles(header))
                            .build();
                }catch(Exception e){
                    throw new RuntimeException("Invalid token");
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config{

    }
}
