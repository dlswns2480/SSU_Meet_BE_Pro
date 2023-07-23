package SSU.SSU_Meet_BE.Controller;


import SSU.SSU_Meet_BE.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "회원가입 및 로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v")
public class SingInController {
    private final SignService signService;
    private final JsoupService jsoupService;
    private final MemberRepository memberRepository;


}
