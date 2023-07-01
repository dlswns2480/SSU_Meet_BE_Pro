package SSU.SSU_Meet_BE.Controller;

import SSU.SSU_Meet_BE.Controller.Dto.FirstRegisterDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/login")
    public String Login() {
        FirstRegisterDto memberDto = FirstRegisterDto.builder()
                .studentNumber("20192908")
                .build();
        Member member = Member.builder()
                .studentNumber(memberDto.getStudentNumber())
                .build();
        System.out.println("controller member = " + member);
        return memberService.Login(member);

    }
}
