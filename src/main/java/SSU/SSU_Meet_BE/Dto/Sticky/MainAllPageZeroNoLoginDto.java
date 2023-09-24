package SSU.SSU_Meet_BE.Dto.Sticky;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MainAllPageZeroNoLoginDto {
    private long allStickyCount;
    private List<MainIdDto> stickyData = new ArrayList<>();

    public void addAllStickyCount(long allStickyCount) { this.allStickyCount = allStickyCount; }
    public void addMainIdDto(MainIdDto mainIdDto) { this.stickyData.add(mainIdDto); }
}
