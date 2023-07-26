package SSU.SSU_Meet_BE.Controller.Members;

import SSU.SSU_Meet_BE.Common.ApiStatus;
import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.SignInDto;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Service.JsoupService;
import SSU.SSU_Meet_BE.Service.Members.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

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

    @Operation(summary = "내가 등록한 포스트잇 목록")
    @GetMapping("/mypage/sticky-list")
    public ApiResponse findResisterList(HttpServletRequest request){
        return memberService.findResisterList(request);
    }

    @Operation(summary = "내가 구입한 포스트잇 목록")
    @GetMapping("/mypage/buy-list")
    public ApiResponse findBuyList(HttpServletRequest request){
        return memberService.findBuyList(request);
    }
}
