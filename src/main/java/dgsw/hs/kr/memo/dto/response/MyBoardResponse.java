package dgsw.hs.kr.memo.dto.response;

import dgsw.hs.kr.memo.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyBoardResponse {

    private Long id;
    private String photo;
    private String title;
    private String content;
    private String names;
    private LocalDateTime createdDate;

    public static MyBoardResponse toResponse(Board board) {
        return new MyBoardResponse(board.getId(), board.getPhoto(), board.getTitle(),
                board.getContent(), board.getNames(), board.getCreatedDate());
    }

}