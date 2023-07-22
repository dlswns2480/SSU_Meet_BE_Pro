package SSU.SSU_Meet_BE.Controller;

import SSU.SSU_Meet_BE.Dto.ApiResponse;
import SSU.SSU_Meet_BE.Dto.SignIn.Request.SignInDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Service.JsoupService;
import SSU.SSU_Meet_BE.Service.MemberService;
import SSU.SSU_Meet_BE.Service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@Tag(name="회원가입, 로그인, 개인정보등록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class HomeController {
    private final MemberService memberService;
    private final SignService signService;
    private final JsoupService jsoupService;
    private final MemberRepository memberRepository;

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ApiResponse login(@RequestBody SignInDto signInDto) throws IOException {
        if (jsoupService.crawling(signInDto)){      // 유세인트 조회 성공
            Optional<Member> findUser = memberRepository.findByStudentNumber(signInDto.getStudentNumber());
            if (findUser.isPresent()) {     // 우리 DB에 있으면
                System.out.println("DB에있당");
                return ApiResponse.success("바로 메인 접속", signService.signIn(signInDto));
            } else {                       // 우리 DB에 없으면
                Member member = Member.builder().studentNumber(signInDto.getStudentNumber()).build();
                memberRepository.save(member);
                System.out.println("DB에없당");
                return ApiResponse.success("추가 정보 등록", signService.signIn(signInDto));
            }
        } else {                   // 유세인트 조회 실패
            return ApiResponse.error("유세인트 로그인 실패!");
        }
    }

    @Operation(summary = "회원 정보 조회")
    @PostMapping("/getdetail")
    public ApiResponse getMemberInfo(HttpServletRequest request) {
        return ApiResponse.success("정보 얻기 성공", memberService.getMemberInfo(request));
    }

    
    @Operation(summary = "개인정보 등록")
    @PostMapping("/new")
    public void saveMemberInfo(@RequestBody Member member){
        memberService.join(member);
    }










}
