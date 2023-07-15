package SSU.SSU_Meet_BE.Dto.SignIn.Response;

import SSU.SSU_Meet_BE.Common.MemberType;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "회원 학번", example = "20192908")
        String studentNumber,
        @Schema(description = "회원 유형", example = "USER")
        MemberType type,
        String token	// 추가
) {
}
