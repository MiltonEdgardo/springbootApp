package com.springboot.springbootApp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${security.jwt.secret}")
    private String key;
    @Value("${security.jwt.issuer}")
    private String issuer;
    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    //private final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    public String create(String id, String subject) {

        Algorithm algorithm = Algorithm.HMAC256(key);

        return JWT.create()
                .withKeyId(id)
                .withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ttlMillis))
                .sign(algorithm);
    }

    public String getValue(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    public String getKey(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getKeyId();
    }


    private DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
