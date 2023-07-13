package dgsw.hs.kr.memo.dto;

import dgsw.hs.kr.memo.dto.response.MyBoardResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardWithLikeDto {

    private String writerName;
    private MyBoardResponse board;
    private List<LikeByBoardDto> likeList;
    private int likeCount;

}