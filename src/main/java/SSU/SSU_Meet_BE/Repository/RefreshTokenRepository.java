package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByMemberId(Long memberId);
}
