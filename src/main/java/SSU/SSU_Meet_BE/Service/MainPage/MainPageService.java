//package SSU.SSU_Meet_BE.Service.MainPage;
//
//
//import SSU.SSU_Meet_BE.Common.ApiResponse;
//import SSU.SSU_Meet_BE.Repository.MemberRepository;
//import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
//import SSU.SSU_Meet_BE.Security.TokenProvider;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class MainPageService {
//    private final MemberRepository memberRepository;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final TokenProvider tokenProvider;
//
//    public ApiResponse logout(HttpServletRequest request) throws Exception{
//        //jwt 토큰 유효한지 검사
//        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
//        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
//
//    }
//}
