package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickyRepository extends JpaRepository<StickyNote, Long> {

}
