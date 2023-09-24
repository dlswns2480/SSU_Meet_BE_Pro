package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "stickyNote")
@Getter
public class Ideal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ideal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticky_note_id")
    private StickyNote stickyNote;

    @Column(name = "ideal_type")
    private String idealType;

    // 빌더
    @Builder
    public Ideal(String idealType) {
        this.idealType = idealType;
    }
    // 연관관계 편의 메서드
    public void setStickyNote(StickyNote stickyNote) {
        this.stickyNote = stickyNote;
    }

}