package SSU.SSU_Meet_BE.Dto.Members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageDto {
    private Integer myStickyCount;

    @Builder
    public MyPageDto(Integer myStickyCount) {
        this.myStickyCount = myStickyCount;
    }
}