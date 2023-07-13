package dgsw.hs.kr.memo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDTO {
    @Size(min = 4 , max = 15, message = "닉네임은 4 ~ 15자 사이로 입력해주세요")
    private String member_id;

    @Size(min = 8 , max = 20, message = "닉네임은 8 ~ 20자 사이로 입력해주세요")
    private String passwd;

    private String name;
}
