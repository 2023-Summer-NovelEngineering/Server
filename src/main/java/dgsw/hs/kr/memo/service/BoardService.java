package dgsw.hs.kr.memo.service;

import dgsw.hs.kr.memo.dto.BoardWithLikeDto;
import dgsw.hs.kr.memo.dto.BoardWithName;
import dgsw.hs.kr.memo.dto.LikeByBoardDto;
import dgsw.hs.kr.memo.dto.request.BoardCreateRequestDto;
import dgsw.hs.kr.memo.dto.response.MyBoardResponse;
import dgsw.hs.kr.memo.entity.Board;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.repository.BoardRepository;
import dgsw.hs.kr.memo.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    @Transactional(rollbackFor = Exception.class)
    public Board register(Member member, BoardCreateRequestDto request) {
        return boardRepository.save(Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .photo(request.getPhoto())
                .names(request.getNames())
                .member(member)
                .build());
    }

    @Transactional(readOnly = true)
    public BoardWithName findById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow();

        MyBoardResponse myBoardResponse = MyBoardResponse.toResponse(board);

        List<LikeByBoardDto> likeByBoardDtoList = likeRepository.findAllByBoard(board).stream()
                .map(LikeByBoardDto::toDto).toList();

        return new BoardWithName(board.getMember().getName(), new BoardWithLikeDto(board.getMember().getName(), myBoardResponse, likeByBoardDtoList, likeByBoardDtoList.size()));
    }

}