package SSU.SSU_Meet_BE.Service.Sticky;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Sticky.StickyRegisterDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.Purchase;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Exception.InvalidTokenException;
import SSU.SSU_Meet_BE.Exception.TokenExpiredException;
import SSU.SSU_Meet_BE.Repository.StickyNoteRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Service.Members.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StickyService {

    private final MemberService memberService;
    private final StickyNoteRepository stickyNoteRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 포스트잇 등록
    public ApiResponse newRegister(HttpServletRequest request, StickyRegisterDto stickyRegisterDto) {
        try {
            String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

            if (accessToken == null) {
                return ApiResponse.error("NoAccessToken"); // 액세스 토큰 없으니 프론트에선 로그인 api로 보내
            }
            Optional<Member> member = memberService.getMemberFromToken(request);

            if (member.isPresent()) {
                if (member.get().getNowStickyCount() > 2) { // 포스트잇 등록 개수 조건 체크
                    return ApiResponse.error("ExceedingPostItRegistrations");
                }

                StickyNote stickyNote = StickyNote.builder().stickyRegisterDto(stickyRegisterDto).build();
                member.get().addSticky(stickyNote); // 연관관계 메서드
                member.get().plusCoin(); //등록 시 코인 증가 (조건 plusCoin 안에서 고려)
                member.get().plusRegisterCount(); // 등록 시 포스트잇 등록 개수 증가
            }
            return ApiResponse.success("SuccessToRegisterPostIt");
        } catch (TokenExpiredException e) { // 액세스 토큰이 만료되었을 경우
            log.error("Access token expired: " + e.getMessage());
            return ApiResponse.error("Access token expired");
        } catch (InvalidTokenException e) { // 액세스 토큰이 유효하지 않을 경우
            log.error("Invalid access token: " + e.getMessage());
            return ApiResponse.error("Invalid access token");
        } catch (Exception e) {
            log.error("Error while processing token: " + e.getMessage());
            return ApiResponse.error("Token processing error");
        }

    }

    // 포스트잇 구매
    public ApiResponse buySticky(HttpServletRequest request, Long stickyId) {
        try {
            String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

            if (accessToken == null) {
                return ApiResponse.error("NoAccessToken"); // 액세스 토큰 없으니 프론트에선 로그인 api로 보내
            }
            Optional<Member> member = memberService.getMemberFromToken(request);

            Optional<StickyNote> stickyNote = stickyNoteRepository.findById(stickyId);

            if (member.isPresent() && stickyNote.isPresent()) {
                // 조건 체크
                if (member.get().getCoin() < 1) { // 코인이 부족한 경우
                    return ApiResponse.error("NotEnoughCoin");
                }
                if (stickyNote.get().getIsSold() == 1) { // 팔린 포스트잇일 경우
                    return ApiResponse.error("AlreadySold");
                }
                // 멤버 코인, 포스트잇 팔림 상태 변경
                member.get().minusCoin(); // -1
                stickyNote.get().sold(); // 0 -> 1

                // 포스트잇 구매 (연관관계)
                Purchase purchase = Purchase.builder()
                        .buyer(member.get())
                        .stickyNote(stickyNote.get())
                        .build();

                // 구매 연관관계 편의 메서드 등록
                purchase.addMemberStickyNote(member.get(), stickyNote.get());

                // 포스트잇 주인
                Member registerMember = stickyNote.get().getMember();

                // 포스트잇 주인 등록 갯수 마이너스
                registerMember.minusRegisterCount();
            }
            return ApiResponse.success("SuccessToBuyPostIt");
        } catch (TokenExpiredException e) { // 액세스 토큰이 만료되었을 경우
            log.error("Access token expired: " + e.getMessage());
            return ApiResponse.error("Access token expired");
        } catch (InvalidTokenException e) { // 액세스 토큰이 유효하지 않을 경우
            log.error("Invalid access token: " + e.getMessage());
            return ApiResponse.error("Invalid access token");
        } catch (Exception e) {
            log.error("Error while processing token: " + e.getMessage());
            return ApiResponse.error("Token processing error");
        }

    }
}