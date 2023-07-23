package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentNumber(String studentNumber);

}