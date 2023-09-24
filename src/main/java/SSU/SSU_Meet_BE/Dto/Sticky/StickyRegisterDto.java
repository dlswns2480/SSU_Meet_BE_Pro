package SSU.SSU_Meet_BE.Dto.Sticky;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StickyRegisterDto {
    private String nickname;
    private String mbti;
    private List<String> hobbies;
    private List<String> ideals;
    private String introduce;
}