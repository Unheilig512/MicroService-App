package eureka_server.gateway.Filters;

import eureka_server.gateway.Utils.JWTUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JWTUtils jwtUtils;

    public AuthenticationFilter(JWTUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization Header"));
            }
                String token = authHeader.substring(7);
                try {
                    jwtUtils.validateJwtToken(token);

                    UUID userId = jwtUtils.getUserIdFromToken(token);
                    String usernameFromToken = jwtUtils.getUsernameFromToken(token);

                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().headers(httpHeaders ->{
                            httpHeaders.remove("X-User-Id");
                            httpHeaders.remove("X-User-Name");

                            httpHeaders.set("X-User-Id", userId.toString());
                            httpHeaders.set("X-User-Name", usernameFromToken);
                    })
                            .build();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

                    return chain.filter(mutatedExchange);

                } catch (Exception e) {
                    return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token"));
                }

        };
    }
}