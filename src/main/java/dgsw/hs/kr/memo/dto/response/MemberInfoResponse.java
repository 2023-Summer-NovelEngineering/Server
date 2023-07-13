package dgsw.hs.kr.memo.dto.response;

import dgsw.hs.kr.memo.dto.BoardWithLikeDto;
import dgsw.hs.kr.memo.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberInfoResponse {
    private Member member;
    private List<BoardWithLikeDto> boardList;
}