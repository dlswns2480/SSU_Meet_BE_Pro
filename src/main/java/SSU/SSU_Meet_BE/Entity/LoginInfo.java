package SSU.SSU_Meet_BE.Entity;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class LoginInfo {
    private String id;
    private String pwd;
}
