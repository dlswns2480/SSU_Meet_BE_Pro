package SSU.SSU_Meet_BE.Dto.Members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyCoinDto {
    private Integer myCoinCount;

    @Builder
    public MyCoinDto(Integer myCoinCount) {
        this.myCoinCount = myCoinCount;
    }
}
