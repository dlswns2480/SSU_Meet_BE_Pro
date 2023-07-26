package SSU.SSU_Meet_BE.Service.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Common.ApiStatus;
import SSU.SSU_Meet_BE.Dto.Members.SignInDto;
import SSU.SSU_Meet_BE.Common.SignInResponse;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Security.TokenProvider;
import SSU.SSU_Meet_BE.Service.JsoupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenProvider tokenProvider;
    private final JsoupService jsoupService;

//    @Transactional(readOnly = true)
//    public String getMemberInfo(HttpServletRequest request) {
//        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
//        return tokenProvider.validateTokenAndGetSubject(token);
//    }

    public ApiResponse login(SignInDto signInDto) throws IOException {
        if (jsoupService.crawling(signInDto)) {      // 유세인트 조회 성공하면
            Optional<Member> findUser = memberRepository.findByStudentNumber(signInDto.getStudentNumber());
            if (findUser.isPresent()) { // DB에 있으면
                if (findUser.get().getFirstRegisterCheck().equals(1)) { // 첫 등록 했을 경우
                    return ApiResponse.success("메인 접속", makeJWT(signInDto));
                } else { // 첫 등록 안 했을 경우
                    return ApiResponse.success("개인정보 등록 필요", makeJWT(signInDto));
                }
            } else { // DB에 없으면 Member save 후 개인정보 등록
                Member member = Member.builder().studentNumber(signInDto.getStudentNumber()).build();
                memberRepository.save(member);
                return ApiResponse.success("개인정보 등록 필요", makeJWT(signInDto));
            }
        } else {                   // 유세인트 조회 실패
            return ApiResponse.error("유세인트 로그인 실패!");
        }
    }
    @Transactional(readOnly = true)
    public SignInResponse makeJWT(SignInDto request) { // JWT 토큰 발급
        Member member = memberRepository.findByStudentNumber(request.getStudentNumber())
                .filter(it -> it.getStudentNumber().equals(request.getStudentNumber()))
                .orElseThrow(() -> new IllegalArgumentException("학번 오류 발생"));
        String token = tokenProvider.createToken(String.format("%s:%s", member.getId(), member.getType()));	// 토큰 생성
        return new SignInResponse(member.getStudentNumber(),"bearer", token);	// 생성자에 토큰 추가
    }

    public ApiResponse newRegister(HttpServletRequest request, UserDetailsDto userDetailsDto) {
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            member.get().newRegister(userDetailsDto);
            member.get().changeFirstRegisterCheck(1);
//            member.get().setCoin(3);
            return ApiResponse.success("개인정보 등록 성공");
        } else {
            return ApiResponse.error("회원을 찾을 수 없습니다");
        }
    }

}
