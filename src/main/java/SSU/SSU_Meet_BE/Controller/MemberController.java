package SSU.SSU_Meet_BE.Controller;

import SSU.SSU_Meet_BE.Controller.Dto.FirstRegisterDto;
import SSU.SSU_Meet_BE.Entity.LoginInfo;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Service.Jsoup;
import SSU.SSU_Meet_BE.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final Jsoup jsoup;

    @PostMapping("/member/login") //아이디 비밀번호가 body에 담겨서 날아온다고 가정
    public String Login(@RequestBody LoginInfo loginInfo) throws IOException {

        if(!jsoup.crawling(loginInfo)){
            System.out.println("login failed!!!!!!!");
            return "failed";
        }

        FirstRegisterDto memberDto = FirstRegisterDto.builder()
//                .studentNumber(loginInfo.getId())
                .studentNumber("20192928")
                .build();
        Member member = Member.builder()
                .studentNumber(memberDto.getStudentNumber())
                .build();
        System.out.println("controller member = " + member);
        return memberService.Login(member);
    }
}
