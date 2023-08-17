package SSU.SSU_Meet_BE.Dto.Sticky;

import SSU.SSU_Meet_BE.Entity.StickyNote;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StickyIdDto {
    private Long stickyId;
    private Integer isSold; // 1 팔림,  0 안팔림
    private StickyInfoDto stickyData;

    @Builder
    public StickyIdDto(StickyNote stickyNote, StickyInfoDto stickyInfoDto) {
        this.stickyId = stickyNote.getId();
        this.isSold = stickyNote.getIsSold();
        this.stickyData = stickyInfoDto;
    }

}
