package SSU.SSU_Meet_BE.Service;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public String Login(Member member) {
        System.out.println("service member = " + member);
        Member member1 = memberRepository.save(member);
        return member1.getStudentNumber();
    }
}
