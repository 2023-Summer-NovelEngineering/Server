package dgsw.hs.kr.memo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private String value;
}
