package dgsw.hs.kr.memo.dto.request;

import dgsw.hs.kr.memo.jwt.JwtProperties;

public class MemberInfoRequestDto {
    private final JwtProperties jwtProperties;

    public MemberInfoRequestDto(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }
}
