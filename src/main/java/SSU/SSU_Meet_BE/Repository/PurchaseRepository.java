package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.Purchase;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    void deleteByBuyerAndStickyNote(Member buyer, StickyNote stickyNote);

}
