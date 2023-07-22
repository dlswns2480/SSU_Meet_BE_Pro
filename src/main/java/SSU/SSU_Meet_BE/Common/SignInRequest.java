package SSU.SSU_Meet_BE.Common;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest (
    @Schema(description = "회원 학번", example = "20192908")
    String studentNumber
){

}