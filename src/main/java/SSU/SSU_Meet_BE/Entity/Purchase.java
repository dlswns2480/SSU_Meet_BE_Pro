package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"stickyNote","buyer"})
@Getter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_member_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticky_note_id")
    private StickyNote stickyNote;

    private LocalDateTime purchaseDate;

    // 빌더
    @Builder
    public Purchase(Member buyer, StickyNote stickyNote) {
        this.buyer = buyer;
        this.stickyNote = stickyNote;
        this.purchaseDate = LocalDateTime.now();
    }

    // 연관관계 편의 메서드
    public void addMemberStickyNote(Member buyer, StickyNote stickyNote) {
        buyer.addPurchase(this);
        stickyNote.addPurchase(this);
    }
}