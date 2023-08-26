package SSU.SSU_Meet_BE.Repository;

import SSU.SSU_Meet_BE.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//현규
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByStudentNumber(String studentNumber);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
