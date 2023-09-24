package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "stickyNote")
@Getter
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sticky_note_id")
    private StickyNote stickyNote;

    private String hobby;

    // 빌더
    @Builder
    public Hobby(String hobby) {
        this.hobby = hobby;
    }

    // 연관관계 편의 메서드
    public void setStickyNote(StickyNote stickyNote) {
        this.stickyNote = stickyNote;
    }

}