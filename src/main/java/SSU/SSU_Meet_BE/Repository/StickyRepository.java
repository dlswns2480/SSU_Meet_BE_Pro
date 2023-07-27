package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyRepository  extends JpaRepository<Member, Long> {
    /** */

}
