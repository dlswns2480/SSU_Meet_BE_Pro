package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.Gender;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PagingRepository extends PagingAndSortingRepository<StickyNote, Long> {
    @Query("SELECT s FROM StickyNote s JOIN FETCH s.member WHERE s.member.sex = :gender and s.isSold = 0 and s.member.major <> :major order by s.id DESC")
    Page<StickyNote> findByGender(@Param("gender") Gender gender, @Param("major") String major, Pageable pageable);
}