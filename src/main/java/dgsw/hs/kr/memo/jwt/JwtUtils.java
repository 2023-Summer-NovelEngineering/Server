package dgsw.hs.kr.memo.jwt;

import dgsw.hs.kr.memo.dto.response.LoginResponse;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.enums.Role;
import dgsw.hs.kr.memo.repository.MemberRepository;
import dgsw.hs.kr.memo.service.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private final MemberRepository memberRepository;

    public LoginResponse getTokens(String memberId, Role role) {
        return LoginResponse.builder()
                .accessToken(generateToken(memberId, role, TokenType.ACCESS))
                .refreshToken(generateToken(memberId, role, TokenType.REFRESH))
                .build();
    }

    public String generateToken(String memberId, Role role,  TokenType tokenType) {
        String secretKey = "";

        Date currentAt = new Date();
        Calendar expiredAt = Calendar.getInstance();
        expiredAt.setTime(currentAt);

        if(tokenType.equals(TokenType.ACCESS)) {
            expiredAt.add(Calendar.DATE, 7);
            secretKey = jwtProperties.getAccessKey();
        }

        if(tokenType.equals(TokenType.REFRESH)) {
            expiredAt.add(Calendar.DATE, 30);
            secretKey = jwtProperties.getRefreshKey();
        }

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, tokenType)
                .setSubject(memberId)
                .claim("authority", role)
                .setIssuedAt(new Date())
                .setExpiration(expiredAt.getTime())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Member validateToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtProperties.getAccessKey()).parseClaimsJws(token).getBody();

        return memberRepository.findByMemberId(claims.getSubject())
                .orElseThrow();
    }

    public String extract(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders("Authorization");

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.startsWith(type)) {
                return value.substring(type.length()).trim();
            }
        }

        return null;
    }

}