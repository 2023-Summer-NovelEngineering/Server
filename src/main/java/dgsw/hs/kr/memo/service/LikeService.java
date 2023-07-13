package dgsw.hs.kr.memo.service;

import dgsw.hs.kr.memo.dto.LikeByBoardDto;
import dgsw.hs.kr.memo.dto.request.PressLikeRequest;
import dgsw.hs.kr.memo.entity.Board;
import dgsw.hs.kr.memo.entity.Like;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.repository.BoardRepository;
import dgsw.hs.kr.memo.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Like press(Member member, PressLikeRequest request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow();

        if(isExist(member, board)) {
            throw new IllegalArgumentException();
        }

        return likeRepository.save(Like.builder()
                .member(member)
                .board(board)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Transactional(readOnly = true)
    public List<LikeByBoardDto> getByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow();

        return likeRepository.findAllByBoard(board).stream()
                .map(LikeByBoardDto::toDto).toList();
    }

    @Transactional(readOnly = true)
    public boolean isExist(Member member, Board board) {
        return likeRepository.existsByMemberAndBoard(member, board);
    }

    @Transactional(readOnly = true)
    public boolean isExist(Member member, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow();
        return isExist(member, board);
    }

}