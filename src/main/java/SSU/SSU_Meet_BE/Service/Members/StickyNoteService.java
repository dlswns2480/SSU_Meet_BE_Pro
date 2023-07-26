package SSU.SSU_Meet_BE.Service.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Common.ApiStatus;
import SSU.SSU_Meet_BE.Dto.Members.StickyDetailsDto;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Repository.StickyRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Security.TokenProvider;
import SSU.SSU_Meet_BE.Service.JsoupService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            StickyNote stickyNote = StickyNote.builder().isSold(false).build(); //판매 미완료로 등록
            stickyRepository.save(stickyNote);
//            stickyNote.memberTo(member.get()); //포스트잇 쓴 멤버 설정
            stickyNote.newResister(stickyDetailsDto); // 포스트잇 정보 등록
            member.get().addSticky(stickyNote); //등록자의 sticky리스트에 추가
            return ApiResponse.success("포스트잇 등록 성공");
        }
        else{
            return ApiResponse.error("포스트잇 등록 실패");
        }
    }

    public ApiResponse buyStickyNote(HttpServletRequest request, Long stickyId){
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<StickyNote> sticky = stickyRepository.findById(stickyId);

        if(member.isPresent()){ //isSold가 true인 포스트잇은 메인화면에 없어서 조건문 필요 X
            member.get().buySticky(sticky.get()); // 구매자의 코인 감소
            sticky.get().setIsSold(true); //해당 포스트잇은 판매완료 처리
            StickyDetailsDto stickyDto = StickyDetailsDto.mapFromEntity(sticky.get()); //반환할 포스트잇 DTO로 변환
            return ApiResponse.success("포스트잇 열람 성공", stickyDto);
        }
        else{
            return ApiResponse.error("에러");
        }
    }

}