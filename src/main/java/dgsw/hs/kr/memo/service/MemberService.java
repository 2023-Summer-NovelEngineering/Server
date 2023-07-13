package dgsw.hs.kr.memo.service;

import dgsw.hs.kr.memo.dto.BoardWithLikeDto;
import dgsw.hs.kr.memo.dto.LikeByBoardDto;
import dgsw.hs.kr.memo.dto.request.MemberLogInRequestDTO;
import dgsw.hs.kr.memo.dto.request.MemberSaveRequestDTO;
import dgsw.hs.kr.memo.dto.response.LoginResponse;
import dgsw.hs.kr.memo.dto.response.MemberInfoResponse;
import dgsw.hs.kr.memo.dto.response.MyBoardResponse;
import dgsw.hs.kr.memo.entity.Board;
import dgsw.hs.kr.memo.entity.Like;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.enums.Role;
import dgsw.hs.kr.memo.jwt.JwtUtils;
import dgsw.hs.kr.memo.repository.BoardRepository;
import dgsw.hs.kr.memo.repository.LikeRepository;
import dgsw.hs.kr.memo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final JwtUtils jwtUtils;

    @Transactional(rollbackFor = Exception.class)
    public Member join(MemberSaveRequestDTO memberSaveRequestDTO) {

        Member member = Member.builder()
                .memberId(memberSaveRequestDTO.getMember_id())
                .passwd(memberSaveRequestDTO.getPasswd())
                .name(memberSaveRequestDTO.getName())
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(MemberLogInRequestDTO memberLogINRequestDTO) {
        Member member = memberRepository.findByMemberId(memberLogINRequestDTO.getMember_id())
                .orElseThrow();

        return jwtUtils.getTokens(member.getMemberId(), member.getRole());
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMy(Member member) {
        List<Board> boardList = boardRepository.findByMember(member);
        return getResponse(member, boardList);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getAllWithBoard(Member member) {
        List<Board> boardList = boardRepository.findAll();
        return getResponse(member, boardList);
    }

    private MemberInfoResponse getResponse(Member member, List<Board> boardList) {
        List<BoardWithLikeDto> boardWithLikeDtoList = new ArrayList<>();

        for(Board board : boardList) {
            MyBoardResponse myBoardResponse = MyBoardResponse.toResponse(board);

            List<Like> likeList = likeRepository.findAllByBoard(board);

            List<LikeByBoardDto> likeByBoardDtoList = likeList.stream()
                    .map(LikeByBoardDto::toDto).toList();

            boardWithLikeDtoList.add(new BoardWithLikeDto(board.getMember().getName(), myBoardResponse, likeByBoardDtoList, likeByBoardDtoList.size()));
        }

        return MemberInfoResponse.builder()
                .member(member)
                .boardList(boardWithLikeDtoList)
                .build();
    }

}
