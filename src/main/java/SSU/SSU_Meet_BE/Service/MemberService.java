package SSU.SSU_Meet_BE.Service;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    @Autowired private final MemberRepository memberRepository;
    @Autowired  private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired private final TokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public String getMemberInfo(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.parseBearerToken(request);

        return tokenProvider.validateTokenAndGetSubject(token);
    }


    public void save(Member member){
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMember(Long memberId){
        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }





}
