package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Tag(name = "포스트잇 정보 DTO")
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

    public static StickyDetailsDto mapFromEntity(StickyNote stickyNote) {
        StickyDetailsDto dto = new StickyDetailsDto();

        dto.setNickName(stickyNote.getNickName());
        dto.setMbti(stickyNote.getMbti());
        dto.setHobbyFirst(stickyNote.getHobbyFirst());
        dto.setHobbySecond(stickyNote.getHobbySecond());
        dto.setHobbyThird(stickyNote.getHobbyThird());
        dto.setIdeal(stickyNote.getIdeal());
        dto.setIntroduce(stickyNote.getIntroduce());
        return dto;
    }
}
