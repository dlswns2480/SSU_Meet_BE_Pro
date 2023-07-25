package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StickyDetailsDto {

    private String nickName;
    private String mbti;
    private String hobbyFirst;
    private String hobbySecond;
    private String hobbyThird;
    private String ideal;
    private String introduce;
}
