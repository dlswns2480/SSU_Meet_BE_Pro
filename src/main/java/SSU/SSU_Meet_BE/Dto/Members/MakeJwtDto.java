package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Common.MemberType;
import lombok.Data;

@Data
public class MakeJwtDto {
    private Long id;
    private String studentNumber;
    private MemberType type;

    public MakeJwtDto(Long id, String studentNumber, MemberType type) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.type = type;
    }
}
