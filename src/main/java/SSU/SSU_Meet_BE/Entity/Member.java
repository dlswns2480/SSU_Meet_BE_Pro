package SSU.SSU_Meet_BE.Entity;

import SSU.SSU_Meet_BE.Common.MemberType;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
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

    @Enumerated(EnumType.STRING)
    private MemberType type = MemberType.USER; // USER, ADMIN;

    @Column(name = "student_number")
    private String studentNumber;

    @Column(name = "first_register_check")
    private Integer firstRegisterCheck;

    @Enumerated(EnumType.STRING)
    private Gender sex; // MALE, FEMALE;

    @Column(name = "birth_year")
    private Integer birthYear;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<StickyNote> stickyNotes = new ArrayList<>(); //일대 다

    @PrePersist
    public void prePersist() {
        this.firstRegisterCheck = this.firstRegisterCheck == null ? 0 : this.firstRegisterCheck;
        this.coin = this.coin == null ? 0 : this.coin;
    }

    // 연관관계 편의 메서드
    public void addSticky(StickyNote stickyNote) {
        stickyNotes.add(stickyNote);
        stickyNote.memberTo(this);
    }

    @Builder
    public Member(String studentNumber) {
        this.studentNumber = studentNumber;

    }

    public void newRegister(UserDetailsDto userDetailsDto) {
        this.sex = userDetailsDto.getSex(); // MALE, FEMALE
        this.birthYear = userDetailsDto.getBirthYear();
        this.age = userDetailsDto.getAge();
        this.college = userDetailsDto.getCollege();
        this.major = userDetailsDto.getMajor();
        this.height = userDetailsDto.getHeight();
        this.instaId = userDetailsDto.getInstaID();
        this.kakaoId = userDetailsDto.getKakaoId();
        this.phoneNumber = userDetailsDto.getPhoneNumber();
    }

    public void changeFirstRegisterCheck(Integer check) {
        this.firstRegisterCheck = check;
    }
}
