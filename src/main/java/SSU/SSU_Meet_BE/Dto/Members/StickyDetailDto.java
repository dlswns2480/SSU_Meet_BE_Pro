package SSU.SSU_Meet_BE.Dto.Members;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Tag(name = "포스트잇 등록 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StickyDetailDto {
    private String nickName;
    private String mbti;
    private String hobbyFirst;
    private String hobbySecond;
    private String hobbyThird;
    private String ideal;
    private String introduce;


}
