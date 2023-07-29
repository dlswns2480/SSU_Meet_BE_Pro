package SSU.SSU_Meet_BE.Service.Sticky;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Sticky.StickyRegisterDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Service.Members.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StickyService {

    private final MemberService memberService;

    // 포스트잇 등록
    public ApiResponse newRegister(HttpServletRequest request, StickyRegisterDto stickyRegisterDto) {
        Optional<Member> member = memberService.getMemberFromToken(request);
        StickyNote stickyNote = StickyNote.builder().stickyRegisterDto(stickyRegisterDto).build();
        member.ifPresent(value -> value.addSticky(stickyNote));
        return ApiResponse.success("포스트잇 등록 성공");
    }
}
