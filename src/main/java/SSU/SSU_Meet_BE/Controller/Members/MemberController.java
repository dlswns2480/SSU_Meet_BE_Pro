package SSU.SSU_Meet_BE.Controller.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.SignInDto;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Service.JsoupService;
import SSU.SSU_Meet_BE.Service.Members.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;
    private final JsoupService jsoupService;
    private final MemberRepository memberRepository;

    //    @Operation(summary = "회원 정보 조회")
//    @PostMapping("/getdetail")
//    public ApiResponse getMemberInfo(HttpServletRequest request) {
//        return ApiResponse.success("정보 얻기 성공", memberService.getMemberInfo(request));
//    }
    @Operation(summary = "로그인 & JWT 토큰 발급")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody SignInDto signInDto) throws IOException {
        return memberService.login(signInDto);
    }

    @Operation(summary = "개인정보 등록")
    @PostMapping("/new")
    public ApiResponse newRegister(HttpServletRequest request, @RequestBody UserDetailsDto userDetailsDto) {
        return memberService.newRegister(request, userDetailsDto);
    }

    @Operation(summary = "메인페이지")
    @GetMapping("/main")
    public ApiResponse mainPage(HttpServletRequest request, Pageable pageable) {
        return memberService.mainPage(request, pageable);
    }

    @Operation(summary = "마이페이지")
    @GetMapping("/mypage")
    public ApiResponse myPage(HttpServletRequest request) {
        return memberService.myPage(request);
    }

    @Operation(summary = "개인정보수정 하기 버튼 클릭")
    @GetMapping("/mypage/modify")
    public ApiResponse startModify(HttpServletRequest request) {
        return memberService.startModify(request);
    }

    @Operation(summary = "개인정보수정 완료 버튼 클릭")
    @PostMapping("/mypage/modify")
    public ApiResponse endModify(HttpServletRequest request, @RequestBody UserDetailsDto userDetailsDto) {
        return memberService.endModify(request, userDetailsDto);
    }

    @Operation(summary = "내가 등록한 포스트잇 확인")
    @GetMapping("/mypage/sticky-list")
    public ApiResponse myRegisteredSticky(HttpServletRequest request) {
        return memberService.myRegisteredSticky(request);
    }

    @Operation(summary = "내가 구매한 포스트잇 확인")
    @GetMapping("/mypage/buy-list")
    public ApiResponse myBoughtSticky(HttpServletRequest request) {
        return memberService.myBoughtSticky(request);
    }

    @Operation(summary = "내가 구매한 포스트잇 삭제")
    @DeleteMapping("/mypage/buy-list/{stickyId}")
    public ApiResponse deleteSticky(HttpServletRequest request, @PathVariable Long stickyId) {
        return memberService.deleteSticky(request, stickyId);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public ApiResponse deleteMember(HttpServletRequest request) {
        return memberService.deleteMEmber(request);
    }

}


