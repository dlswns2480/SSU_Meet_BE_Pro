package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


//현규
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "studentNumber", nullable = false, unique = true)
    private String studentNumber;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(String studentNumber, String refreshToken){
        this.studentNumber = studentNumber;
        this.refreshToken = refreshToken;
    }

    public void update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
    }
}
