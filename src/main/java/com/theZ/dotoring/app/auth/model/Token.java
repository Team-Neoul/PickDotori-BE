package com.theZ.dotoring.app.auth.model;

import com.theZ.dotoring.app.auth.AuthConstants;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class Token {


    private static String secretKey;

    @Value("${jwt.secretKey}")
    public void setSecretKey(String secret) {
        secretKey = secret;
    }

    @Value("${jwt.accessTokenExp}")
    private Long accessTokenExp;

    @Value("${jwt.refreshTokenExp}")
    private Long refreshTokenExp;

    public String generateAccessToken(MemberAccount memberAccount, Instant nowTime) { // withExpireAt()은 Date 객체를 써야함으로 claim에 exp로 직접적어줌
        Date validity = new Date(nowTime.toEpochMilli()
                + accessTokenExp);
        log.info("validity : " + validity);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setIssuer("Dotoring") // 토큰 발긊자
                .setSubject(memberAccount.getLoginId()) // 토큰에서 사용자에 대한 식별 값
                .setAudience(memberAccount.getMemberType().toString())
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .setId(makeUUID()) // JWT 자체 식별자
                .claim("tokenType","accessToken")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String generateRefreshToken(MemberAccount memberAccount, Instant nowTime) { // withExpireAt()은 Date 객체를 써야함으로 claim에 exp로 직접적어줌
        Date validity = new Date(nowTime.toEpochMilli()
                + refreshTokenExp);
        log.info("validity : " + validity);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                .setIssuer("Dotoring")
                .setSubject(memberAccount.getLoginId())
                .setAudience(memberAccount.getMemberType().toString())
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .setId(makeUUID()) // JWT 자체 식별자
                .claim("tokenType","refreshToken")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private String makeUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    public static Claims getClaimsFormToken(String token) {
        token = token.replace(AuthConstants.TOKEN_TYPE, "");
        Object oClaims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(secretKey))
                .build()
                .parse(token)
                .getBody();
        return (Claims)oClaims;
    }

    public String getSubjectFormToken(String token) {
        token = token.replace(AuthConstants.TOKEN_TYPE, "");
        Object oClaims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(secretKey))
                .build()
                .parse(token)
                .getBody();
        Claims claims = (Claims)oClaims;
        return claims.getSubject();
    }

    public static boolean isValidAccessToken(String accessToken) {
        try {
            Claims claims = getClaimsFormToken(accessToken);
            log.info("expireTime :" + claims.getExpiration());
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            throw new JwtException("잘못된 엑세스 토큰입니다.");
        }
    }

    public static boolean isValidRefreshToken(String refreshToken) {
        try {
            Claims claims = getClaimsFormToken(refreshToken);
            log.info("expireTime :" + claims.getExpiration());
            log.info("email :" + claims.get("email"));
            log.info("role :" + claims.get("role"));
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "만료된 Refresh 토큰입니다.");
        } catch (JwtException e) {
            throw new JwtException("잘못된 Refresh 토큰입니다.");
        }
    }

}
