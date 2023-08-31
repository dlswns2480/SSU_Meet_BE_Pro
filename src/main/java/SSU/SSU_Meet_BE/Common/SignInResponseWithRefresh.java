package SSU.SSU_Meet_BE.Common;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponseWithRefresh(
        @Schema(description = "회원 학번", example = "20192908")
        String studentNumber,
        @Schema(description = "토큰 타입", example = "bearer")
        String tokenType,
        @Schema(description = "Access 토큰", example = "access.access.access")
        String accessToken,
        @Schema(description = "Refresh 토큰", example = "refresh.refresh.refresh")
        String refreshToken
){}
