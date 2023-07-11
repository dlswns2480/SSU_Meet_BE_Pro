package SSU.SSU_Meet_BE.Controller;

import SSU.SSU_Meet_BE.Controller.Dto.FirstRegisterDto;
//import SSU.SSU_Meet_BE.Entity.LoginInfo;
import SSU.SSU_Meet_BE.Entity.Member;

import SSU.SSU_Meet_BE.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/member/login") //아이디 비밀번호가 body에 담겨서 날아온다고 가정
//    public String Login(@RequestBody LoginInfo loginInfo) throws IOException {
    public String Login() throws IOException{
        System.out.println("controller start");
        if(!crawling()){
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


//    public boolean crawling(LoginInfo loginInfo) throws IOException {
    public boolean crawling() throws IOException {
        Connection.Response loginPageResponse =
                org.jsoup.Jsoup.connect("https://smartid.ssu.ac.kr/Symtra_sso/smln.asp?apiReturnUrl=https%3A%2F%2Fsaint.ssu.ac.kr%2FwebSSO%2Fsso.jsp")
                        .timeout(3000)
                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                        .header("Accept-Encoding","gzip, deflate, br")
                        .header("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control","no-cache")
                        .header("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"")
                        .header("sec-ch-ua-mobile", "?0")
                        .header("sec-ch-ua-platform", "\"Windows\"")
                        .header("Sec-Fetch-Dest", "document")
                        .header("Sec-Fetch-Mode", "navigate")
                        .header("Sec-Fetch-Site", "same-site")
                        .method(Connection.Method.GET)
                        .execute();


        //로그인페이지에서 담겨있는 쿠키
        Map<String, String> loginTryCookie = loginPageResponse.cookies();
        System.out.println("loginTryCookie = " + loginTryCookie);
        // 로그인 페이지에서 로그인에 함께 전송하는 토큰 얻어내기
        Document loginPageDocument = loginPageResponse.parse();
        String in_tp_bit = loginPageDocument.select("input[name=in_tp_bit]").first().val();
        String rqst_caus_cd = loginPageDocument.select("input[name=rqst_caus_cd]").first().val();


        // Window, Chrome의 User Agent.
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36";

        // 로그인 창에서 로그인 정보(아이디,비번,특수토큰) 담아서 전송할 폼 데이터
        Map<String, String> data = new HashMap<>();
        data.put("in_tp_bit", in_tp_bit); // 로그인 페이지에서 얻은 토큰들
        data.put("rqst_caus_cd", rqst_caus_cd);
//        data.put("userid", loginInfo.getId());
//        data.put("pwd", loginInfo.getPwd());
        data.put("userid", ""); // 테스트용
        data.put("pwd", ""); //테스트용


        Connection.Response response = org.jsoup.Jsoup.connect("https://smartid.ssu.ac.kr/Symtra_sso/smln_pcs.asp")
                .userAgent(userAgent)
                .timeout(3000)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Encoding","gzip, deflate, br")
                .header("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Sec-Fetch-Site", "same-site")
                .header("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Windows\"")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site","same-site")
                .header("Sec-Fetch-User", "?1")
                .header("Upgrade-Insecure-Request", "1")
                .cookies(loginTryCookie)
                .data(data)
                .method(Connection.Method.POST)
                .execute();

        System.out.println("===================================");
        Map<String,String> loginsucceed = response.cookies();
        System.out.println("--------------"+loginsucceed);

        if(!Objects.equals(loginsucceed.get("sToken"), "")){
            System.out.println("login success");
            return true;
        }
        else{
            return false;
        }
    }
}
