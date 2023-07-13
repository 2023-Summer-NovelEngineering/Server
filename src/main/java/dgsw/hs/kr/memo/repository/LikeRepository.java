package dgsw.hs.kr.memo.repository;

import dgsw.hs.kr.memo.entity.Board;
import dgsw.hs.kr.memo.entity.Like;
import dgsw.hs.kr.memo.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByMemberAndBoard(Member member, Board board);

    @EntityGraph(attributePaths = {"member"})
    List<Like> findAllByBoard(Board board);

}