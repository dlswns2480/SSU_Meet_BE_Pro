package SSU.SSU_Meet_BE.Service.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.StickyDetailsDto;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Repository.StickyRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Security.TokenProvider;
import SSU.SSU_Meet_BE.Service.JsoupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StickyNoteService {
    private final StickyRepository stickyRepository;
    private final MemberRepository memberRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenProvider tokenProvider;
    private final JsoupService jsoupService;

    public ApiResponse resisterStickyNote(HttpServletRequest request, StickyDetailsDto stickyDetailsDto){
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()){ //등록된 회원이 포스트잇 등록을 하려한다면
            StickyNote stickyNote = StickyNote.builder().isSold(false).build();
            stickyRepository.save(stickyNote);
            stickyNote.memberTo(member.get());
            stickyNote.newResister(stickyDetailsDto);
            member.get().addSticky(stickyNote);
            return ApiResponse.success("포스트잇 등록 성공");
        }
        else{
            return ApiResponse.error("포스트잇 등록 실패");
        }

    }
}
