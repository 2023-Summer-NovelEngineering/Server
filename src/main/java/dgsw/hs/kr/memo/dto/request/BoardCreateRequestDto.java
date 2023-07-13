package dgsw.hs.kr.memo.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequestDto {

    private String photo;
    private String title;
    private String content;
    private String names;


}
