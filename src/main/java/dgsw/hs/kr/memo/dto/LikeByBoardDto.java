package dgsw.hs.kr.memo.dto;

import dgsw.hs.kr.memo.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeByBoardDto {

    private Long id;
    private String memberId;

    public static LikeByBoardDto toDto(Like like) {
        return new LikeByBoardDto(like.getId(), like.getMember().getMemberId());
    }

}