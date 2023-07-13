package dgsw.hs.kr.memo.controller;

import dgsw.hs.kr.memo.dto.BoardWithLikeDto;
import dgsw.hs.kr.memo.dto.BoardWithName;
import dgsw.hs.kr.memo.dto.response.ResponseData;
import dgsw.hs.kr.memo.annotation.CheckToken;
import dgsw.hs.kr.memo.dto.request.BoardCreateRequestDto;
import dgsw.hs.kr.memo.entity.Board;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @CheckToken
    @PostMapping
    public ResponseData<Board> create(@RequestAttribute Member member,
                                      @RequestBody BoardCreateRequestDto boardCreateRequestDto) {
        Board board = boardService.register(member, boardCreateRequestDto);
        return new ResponseData<>(
                HttpStatus.CREATED,
                "게시글 생성 성공",
                board
        );
    }

    @GetMapping("/{boardId}")
    public ResponseData<BoardWithName> getById(@PathVariable Long boardId) {
        BoardWithName boardWithName = boardService.findById(boardId);

        return new ResponseData<>(
                HttpStatus.OK,
                "게시글 단건 조회 성공",
                boardWithName
        );
    }

}