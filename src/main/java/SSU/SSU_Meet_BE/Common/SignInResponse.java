package SSU.SSU_Meet_BE.Common;

import SSU.SSU_Meet_BE.Common.MemberType;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignInResponse(
        @Schema(description = "회원 학번", example = "20192908")
        String studentNumber,
        @Schema(description = "토큰 타입", example = "bearer")
        String tokenType,
        @Schema(description = "JWT 토큰", example = "jwtjwtjwtjwtjwtjwtjwtjwt")
        String accessToken	// 추가
){}
