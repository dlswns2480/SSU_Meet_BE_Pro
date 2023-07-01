package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "stickyNotes")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "first_register_check")
    private Integer firstRegisterCheck;

//    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender sex; // MALE, FEMALE;

//    @Column(nullable = false)
    private Integer age;

//    @Column(nullable = false)
    private Double height;

    @Column(name = "insta_id")
    private String instaId;

    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "today_purchase_count")
    private Integer todayPurchaseCount; // 오늘 구매한 횟수 (스케쥴러로 자정 체크)

    @Column(name = "total_choiced_count")
    private Long totalChoicedCount; // 총 선택받은 횟수

    @Column(name = "total_purchase_count")
    private Long totalPurchaseCount; // 총 구매한 횟수

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StickyNote> stickyNotes = new ArrayList<>(); //일대 다

    @PrePersist
    public void prePersist() {
        this.firstRegisterCheck = this.firstRegisterCheck == null ? 0 : this.firstRegisterCheck;
    }

    @Builder
    public Member(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
