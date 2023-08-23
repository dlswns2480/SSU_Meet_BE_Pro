package SSU.SSU_Meet_BE.Dto.Sticky;

import SSU.SSU_Meet_BE.Entity.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainInfoDto {
    private String birthDate;
    private Integer age;
    private String college;
    private String major;
    private Integer height;
    private String nickname;
    private String mbti;
    private List<String> hobbies = new ArrayList<>();
    private List<String> ideals = new ArrayList<>();
    private String introduce;

    @Builder
    public MainInfoDto(Member member, StickyNote stickyNote) {
        this.birthDate = member.getBirthDate();
        this.age = member.getAge();
        this.college = member.getCollege();
        this.major = member.getMajor();
        this.height = member.getHeight();
        this.nickname = stickyNote.getNickName();
        this.mbti = stickyNote.getMbti();
        this.introduce = stickyNote.getIntroduce();

        List<String> hobbies = stickyNote.getHobbies().stream()
                .map(Hobby::getHobby)
                .toList();
        this.hobbies.addAll(hobbies);

        List<String> ideals = stickyNote.getIdeals().stream()
                .map(Ideal::getIdealType)
                .toList();
        this.ideals.addAll(ideals);
    }
}
