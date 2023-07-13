package dgsw.hs.kr.memo.controller;

import dgsw.hs.kr.memo.annotation.CheckToken;
import dgsw.hs.kr.memo.dto.request.PressLikeRequest;
import dgsw.hs.kr.memo.dto.response.ResponseData;
import dgsw.hs.kr.memo.entity.Like;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @CheckToken
    @PostMapping
    public ResponseData<Like> press(@RequestAttribute Member member, @RequestBody PressLikeRequest request) {
        Like like = likeService.press(member, request);

        return new ResponseData<>(
                HttpStatus.CREATED,
                "좋아요 생성 완료",
                like
        );
    }

    @CheckToken
    @GetMapping("/{boardId}")
    public ResponseData<Boolean> isPressed(@RequestAttribute Member member, @PathVariable Long boardId) {
        System.out.println("boardId : " + boardId);
        Boolean isPressed = likeService.isExist(member, boardId);

        return new ResponseData<>(
                HttpStatus.OK,
                "좋아요 유무 판별 완료",
                isPressed
        );
    }

}