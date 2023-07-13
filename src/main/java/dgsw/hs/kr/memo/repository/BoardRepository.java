package dgsw.hs.kr.memo.repository;


import dgsw.hs.kr.memo.entity.Board;
import dgsw.hs.kr.memo.entity.Member;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"member"})
    Optional<Board> findById(Long id);

    @EntityGraph(attributePaths = {"member"})
    List<Board> findByMember(@NotNull Member member);

}