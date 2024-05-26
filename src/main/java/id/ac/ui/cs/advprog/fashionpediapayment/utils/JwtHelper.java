package id.ac.ui.cs.advprog.fashionpediapayment.utils;

import id.ac.ui.cs.advprog.fashionpediapayment.auth.model.User;
import id.ac.ui.cs.advprog.fashionpediapayment.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JwtHelper {
    @Autowired
    private static JwtService jwtService;

    public static Long getUserIdFromToken(String jwtToken) {
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String tokenWithoutBearer = jwtToken.replace("Bearer ", "");
        return getUserIdFromTokenWithCheckUserRepository(tokenWithoutBearer);
    }

    private static Long getUserIdFromTokenWithCheckUserRepository(String token){
        User user = jwtService.extractUser(token);
        if (!jwtService.isTokenValid(token, user)) {
            throw new RuntimeException("Invalid token");
        }
        return user.getId().longValue();
    }
}