package SSU.SSU_Meet_BE.Dto.Sticky;

import SSU.SSU_Meet_BE.Entity.StickyNote;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainIdDto {
    private Long stickyId;
    private MainInfoDto stickyData;

    @Builder
    public MainIdDto(StickyNote stickyNote, MainInfoDto mainInfoDto) {
        this.stickyId = stickyNote.getId();
        this.stickyData = mainInfoDto;
    }

}