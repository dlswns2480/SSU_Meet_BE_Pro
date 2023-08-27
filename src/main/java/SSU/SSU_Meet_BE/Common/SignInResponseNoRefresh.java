package SSU.SSU_Meet_BE.Common;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponseNoRefresh (
    @Schema(description = "회원 학번", example = "20192908")
    String studentNumber,
    @Schema(description = "토큰 타입", example = "bearer")
    String tokenType,
    @Schema(description = "access 토큰", example = "aacccceessss")
    String accessToken // 추가
){}