package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.Gender;
import SSU.SSU_Meet_BE.Entity.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsDto {
    private Gender sex; // MALE, FEMALE
    private Integer birthYear;
    private Integer age;
    private String college;
    private String major;
    private Integer height;
    private String instaID;
    private String kakaoId;
    private String phoneNumber;


    private UserDetailsDto(Member member) {
        this.sex = member.getSex();
        this.birthYear = member.getBirthYear();
        this.age = member.getAge();
        this.college = member.getCollege();
        this.major = member.getMajor();
        this.height = member.getHeight();
        this.instaID = member.getInstaId();
        this.kakaoId = member.getKakaoId();
        this.phoneNumber = member.getPhoneNumber();
    }

    public static UserDetailsDto modifyInfo(Member member) {
        return new UserDetailsDto(member);
    }

}
