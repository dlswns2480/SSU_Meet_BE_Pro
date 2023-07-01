package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "member")
@Getter
public class StickyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticky_note_id")
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    private String mbti;

    private String hobby;

    private String introduce; //한줄 소개

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 다대 일
}
