package SSU.SSU_Meet_BE.Dto.SignIn.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInDto {
    private String studentNumber;
    private String pwd;
}
