package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.Gender;
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

}
