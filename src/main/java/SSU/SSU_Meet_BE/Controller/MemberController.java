package SSU.SSU_Meet_BE.Controller;

import SSU.SSU_Meet_BE.Dto.ApiResponse;
import SSU.SSU_Meet_BE.Service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name="로그인 후 사용할 수 있는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회")
    @PostMapping("/getdetail")
    public ApiResponse getMemberInfo(HttpServletRequest request) {
        return ApiResponse.success("정보 얻기 성공", memberService.getMemberInfo(request));
    }




}
