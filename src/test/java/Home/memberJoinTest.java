package Home;


import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Service.MemberService;
import SSU.SSU_Meet_BE.Service.StickyService;
import SSU.SSU_Meet_BE.SsuMeetBeApplication;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsuMeetBeApplication.class)
@Transactional
public class memberJoinTest {

    @Autowired private MemberService memberService;
    @Autowired private StickyService stickyService;

    @Test
    public void 개인정보등록(){
        Member member = new Member("20192935");
        Long memberId = memberService.join(member);
        List<Member> members = memberService.findMembers();
        Assertions.assertEquals(members.size(), 1);


    }
}
