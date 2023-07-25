package SSU.SSU_Meet_BE.Entity;

import SSU.SSU_Meet_BE.Dto.Members.StickyDetailsDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "hobby_first", length = 20)
    private String hobbyFirst;

    @Column(name = "hobby_second", length = 20)
    private String hobbySecond;

    @Column(name = "hobby_third", length = 20)
    private String hobbyThird;

    private String ideal; // 이상형

    @Column(length = 255)
    private String introduce; //한줄 소개

    @Column(name = "is_sold")
    private Boolean isSold; // 판매 여부 : True -> 팔림, False -> 안팔림

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 다대 일

    // 연관관계 편의 메서드
    public void memberTo(Member member) {
        this.member = member;
    }

    @Builder
    public StickyNote(boolean isSold) {
        this.isSold = isSold;
    }

    public void newResister(StickyDetailsDto stickyDetailsDto){
        this.nickName = stickyDetailsDto.getNickName();
        this.mbti = stickyDetailsDto.getMbti();
        this.hobbyFirst = stickyDetailsDto.getHobbyFirst();
        this.hobbySecond = stickyDetailsDto.getHobbySecond();
        this.hobbyThird = stickyDetailsDto.getHobbyThird();
        this.ideal = stickyDetailsDto.getIdeal();
        this.introduce = stickyDetailsDto.getIntroduce();
    }









}
