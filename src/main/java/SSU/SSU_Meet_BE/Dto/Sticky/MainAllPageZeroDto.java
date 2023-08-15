package SSU.SSU_Meet_BE.Dto.Sticky;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MainAllPageZeroDto {
        private Integer myCoinCount;
        private Integer myStickyCount;
        private long allCount;
        private List<MainIdDto> stickyData = new ArrayList<>();

    public void addBasicCounts(Integer myCoinCount, Integer myStickyCount) {
        this.myCoinCount = myCoinCount;
        this.myStickyCount = myStickyCount;
    }
    public void addAllCount(long allCount) { this.allCount = allCount; }

    public void addMainIdDto(MainIdDto mainIdDto) { this.stickyData.add(mainIdDto); }

}