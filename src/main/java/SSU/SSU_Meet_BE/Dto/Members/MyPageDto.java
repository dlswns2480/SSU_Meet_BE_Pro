package SSU.SSU_Meet_BE.Dto.Members;

import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Tag(name = "마이페이지 DTO")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageDto {
    private Integer coin; //보유 코인 개수
    private Integer size; // 등록한 포스트잇 개수

    public static MyPageDto mapFromEntity(Member member) {
        MyPageDto dto = new MyPageDto();
        Integer minus = 0;
        dto.coin = member.getCoin();
        for(StickyNote note : member.getStickyNotes()){
            if(note.getIsSold() == true)
                minus++;
        }
        dto.size = member.getStickyNotes().size() - minus;
        return dto;
    }
}
