package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickyNoteRepository extends JpaRepository<StickyNote, Long> {
    @Query("SELECT s FROM StickyNote s JOIN s.hobbies JOIN FETCH s.ideals WHERE s.member.id = :memberId")
    List<StickyNote> findAllByMemberIdWithHobbiesAndIdeals(@Param("memberId") Long memberId);

    @Query("SELECT p.stickyNote FROM Purchase p WHERE p.buyer.id = :memberId")
    List<StickyNote> findAllByMemberIdWithPurchases(@Param("memberId") Long memberId);
}
