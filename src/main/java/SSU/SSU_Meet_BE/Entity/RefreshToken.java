package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public RefreshToken(Long memberId, String refreshToken){
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String newRefreshToken){
        this.refreshToken = newRefreshToken;
    }

}