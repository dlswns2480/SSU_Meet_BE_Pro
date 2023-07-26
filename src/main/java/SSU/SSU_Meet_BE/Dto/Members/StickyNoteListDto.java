package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.StickyNote;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StickyNoteListDto {
    private List<StickyDetailsDto> notesList = new ArrayList<>();
    public static StickyNoteListDto mapFromEntity(StickyDetailsDto stickyDetailsDto) {
        StickyNoteListDto dto = new StickyNoteListDto();
        dto.notesList.add(stickyDetailsDto);
        return dto;
    }
}
