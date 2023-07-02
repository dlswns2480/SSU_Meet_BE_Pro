package SSU.SSU_Meet_BE.Service;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public String Login(Member member) {
        System.out.println("service member = " + member);

        Optional<Member> member1 = memberRepository.findBystudentNumber(member.getStudentNumber());
        if (member1.isPresent()) { //내부에 값이 있을 경우 true 리턴
            Member check_Member = member1.get();
            System.out.println("test succeed");
            return check_Member.getStudentNumber();
        } else { //내부가 null인경우 false리턴
            Member check_Member = member1.get();
            Member result_Member = memberRepository.save(check_Member);
            return result_Member.getStudentNumber();
        }
    }
}
