package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.Gender;
import SSU.SSU_Meet_BE.Entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsDto {
    private Gender sex; // MALE, FEMALE
    private String birthDate;
    private Integer age;
    private String college;
    private String major;
    private Integer height;
    private String instaId;
    private String kakaoId;
    private String phoneNumber;

    @Builder
    public UserDetailsDto(Member member) {
        this.sex = member.getSex();
        this.birthDate = member.getBirthDate();
        this.age = member.getAge();
        this.college = member.getCollege();
        this.major = member.getMajor();
        this.height = member.getHeight();
        this.instaId = member.getInstaId();
        this.kakaoId = member.getKakaoId();
        this.phoneNumber = member.getPhoneNumber();
    }
}