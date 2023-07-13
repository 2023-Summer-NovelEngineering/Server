package dgsw.hs.kr.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardWithName {

    private String memberName;
    private BoardWithLikeDto boardWithLikeDto;

}