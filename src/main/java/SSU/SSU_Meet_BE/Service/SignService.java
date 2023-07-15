package SSU.SSU_Meet_BE.Service;

import SSU.SSU_Meet_BE.Security.TokenProvider;
import SSU.SSU_Meet_BE.Dto.SignIn.Request.SignInDto;
import SSU.SSU_Meet_BE.Dto.SignIn.Response.SignInResponse;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SignService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;	// 추가

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInDto request) {
        Member member = memberRepository.findByStudentNumber(request.getStudentNumber())
                .filter(it -> it.getStudentNumber().equals(request.getStudentNumber()))
                .orElseThrow(() -> new IllegalArgumentException("학번 오류 발생"));
        String token = tokenProvider.createToken(String.format("%s:%s", member.getId(), member.getType()));	// 토큰 생성
        return new SignInResponse(member.getStudentNumber(), member.getType(), token);	// 생성자에 토큰 추가
    }
}
