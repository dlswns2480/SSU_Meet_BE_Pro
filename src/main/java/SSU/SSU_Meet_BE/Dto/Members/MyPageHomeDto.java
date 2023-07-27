package SSU.SSU_Meet_BE.Dto.Members;


import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder(builderMethodName = "MyPageDtoBuilder")
public class MyPageHomeDto {
    private Integer coin;
    private Integer upload_cnt;

    public static MyPageHomeDto get_info(Member member){
        MyPageHomeDto myPageHomeDto = new MyPageHomeDto();
        myPageHomeDto.coin = member.getCoin();
        int cnt = member.getStickyNotes().size();
        for(StickyNote note : member.getStickyNotes()){
            if(note.getIsSold()){
                cnt --;
            }
        }
        myPageHomeDto.upload_cnt = cnt;
        return myPageHomeDto;
    }
}
