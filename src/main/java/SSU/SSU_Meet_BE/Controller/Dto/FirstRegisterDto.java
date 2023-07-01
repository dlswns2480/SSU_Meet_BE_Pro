package SSU.SSU_Meet_BE.Controller.Dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirstRegisterDto {
    private String studentNumber;

    @Builder
    public FirstRegisterDto(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
