package SSU.SSU_Meet_BE.Entity;

import SSU.SSU_Meet_BE.Dto.Sticky.StickyRegisterDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member", "hobbies", "ideals"})
@Getter
public class StickyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticky_note_id")
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    private String mbti;

    @OneToMany(mappedBy = "stickyNote", cascade = CascadeType.ALL)
    private List<Hobby> hobbies = new ArrayList<>();

    @OneToMany(mappedBy = "stickyNote", cascade = CascadeType.ALL)
    private List<Ideal> ideals = new ArrayList<>(); // 이상형

    @Column(length = 255)
    private String introduce; //한줄 소개

    @Column(name = "is_sold")
    private Integer isSold; // 판매 여부 : 1 -> 팔림, 0 -> 안팔림

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 다대 일

    @OneToMany(mappedBy = "stickyNote", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.isSold = this.isSold == null ? 0 : this.isSold;
    }

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    public void addIdeal(Ideal ideal) {
        ideals.add(ideal);
        ideal.setStickyNote(this);
    }

    public void addHobby(Hobby hobby) {
        hobbies.add(hobby);
        hobby.setStickyNote(this);
    }

    // 포스트잇 등록 빌더
    @Builder
    public StickyNote (StickyRegisterDto stickyRegisterDto) {
        this.nickName = stickyRegisterDto.getNickname();
        this.mbti = stickyRegisterDto.getMbti();
        List<String> hobbies = stickyRegisterDto.getHobbies();
        for (String hobby : hobbies) {
            Hobby newHobby = Hobby.builder().hobby(hobby).build();
            this.addHobby(newHobby);
        }
        List<String> ideals = stickyRegisterDto.getIdeals();
        for (String ideal : ideals) {
            Ideal newIdeal = Ideal.builder().idealType(ideal).build();
            this.addIdeal(newIdeal);
        }
        this.introduce = stickyRegisterDto.getIntroduce();
    }

    // 포스트잇 팔림 상태 변경
    public void sold() {
        this.isSold = 1;
    }

}
