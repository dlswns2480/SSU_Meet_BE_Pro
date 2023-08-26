package SSU.SSU_Meet_BE.Dto.Members;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDto {
    private String studentNumber;
    private String password;
}