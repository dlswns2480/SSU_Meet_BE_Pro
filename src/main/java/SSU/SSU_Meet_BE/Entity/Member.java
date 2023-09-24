package SSU.SSU_Meet_BE.Entity;

import SSU.SSU_Meet_BE.Common.MemberType;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private MemberType type = MemberType.USER; // USER, ADMIN;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "first_register_check")
    private Integer firstRegisterCheck;

    @Enumerated(EnumType.STRING)
    private Gender sex; // MALE, FEMALE;

    @Column(name = "birth_date")
    private String birthDate; // 생년월일

    private Integer age;

    private Integer height;

    private String college; // 단과 대학

    private String major; // 학과(부)

    @Column(name = "insta_id")
    private String instaId;

    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Integer coin; // 보유 코인 개수

    @Column(name = "now_sticky_count")
    private Integer nowStickyCount; // 등록되어있는 포스트잇 개수

    // 등록한 포스트잇
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StickyNote> stickyNotes = new ArrayList<>(); //일대 다

    // 구매한 포스트잇
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.firstRegisterCheck = this.firstRegisterCheck == null ? 0 : this.firstRegisterCheck;
        this.coin = this.coin == null ? 0 : this.coin;
        this.nowStickyCount = this.nowStickyCount == null ? 0 : this.nowStickyCount;
    }

    // 연관관계 편의 메서드
    public void addSticky(StickyNote stickyNote) {
        stickyNotes.add(stickyNote);
        stickyNote.setMember(this);
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    // 빌더
    @Builder
    public Member(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    // 멤버 기본정보 등록
    public void newRegister(UserDetailsDto userDetailsDto) {
        this.sex = userDetailsDto.getSex(); // MALE, FEMALE
        this.birthDate = userDetailsDto.getBirthDate();
        this.age = userDetailsDto.getAge();
        this.college = userDetailsDto.getCollege();
        this.major = userDetailsDto.getMajor();
        this.height = userDetailsDto.getHeight();
        this.instaId = userDetailsDto.getInstaId();
        this.kakaoId = userDetailsDto.getKakaoId();
        this.phoneNumber = userDetailsDto.getPhoneNumber();
    }

    // 첫 등록 체크
    public void changeFirstRegisterCheck(Integer check) {
        this.firstRegisterCheck = check;
    }

    // 코인 증가
    public void plusCoin() {
        if (this.coin < 3) {
            this.coin += 1;
        }
    }

    // 코인 감소
    public void minusCoin() {
        if (this.coin > 0) {
            this.coin -= 1;
        }
    }
    // 포스트잇 등록할 떄 현재 등록 개수 증가
    public void plusRegisterCount() {
        if (this.nowStickyCount < 3) {
            this.nowStickyCount += 1;
        }
    }
    // 포스트잇 팔렸을 때 현재 등록 개수 감소
    public void minusRegisterCount() {
        if (this.nowStickyCount > 0) {
            this.nowStickyCount -= 1;
        }
    }
}
