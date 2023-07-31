package SSU.SSU_Meet_BE.Dto.Sticky;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class StickyAllDto {
    private List<StickyIdDto> stickyData = new ArrayList<>();

    public void addStickyIdDto(StickyIdDto stickyIdDto) {
        this.stickyData.add(stickyIdDto);
    }

}
