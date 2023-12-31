package SSU.SSU_Meet_BE.Service.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.MyCoinDto;
import SSU.SSU_Meet_BE.Dto.Members.MyPageDto;
import SSU.SSU_Meet_BE.Dto.Members.SignInDto;
import SSU.SSU_Meet_BE.Common.SignInResponse;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Dto.Sticky.*;
import SSU.SSU_Meet_BE.Entity.Gender;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Repository.PagingRepository;
import SSU.SSU_Meet_BE.Repository.PurchaseRepository;
import SSU.SSU_Meet_BE.Repository.StickyNoteRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Security.TokenProvider;
import SSU.SSU_Meet_BE.Service.JsoupService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final StickyNoteRepository stickyNoteRepository;
    private final PagingRepository pagingRepository;
    private final PurchaseRepository purchaseRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenProvider tokenProvider;
    private final JsoupService jsoupService;

//    @Transactional(readOnly = true)
//    public String getMemberInfo(HttpServletRequest request) {
//        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
//        return tokenProvider.validateTokenAndGetSubject(token);
//    }

    public ApiResponse login(SignInDto signInDto) throws IOException {
        if (jsoupService.crawling(signInDto)) {      // 유세인트 조회 성공하면
            Optional<Member> findUser = memberRepository.findByStudentNumber(signInDto.getStudentNumber());
            if (findUser.isPresent()) { // DB에 있으면
                if (findUser.get().getFirstRegisterCheck().equals(1)) { // 첫 등록 했을 경우
                    return ApiResponse.success("RegisteredUser", makeJWT(signInDto));
                } else { // 첫 등록 안 했을 경우
                    return ApiResponse.success("RequiredFirstRegistration", makeJWT(signInDto));
                }
            } else { // DB에 없으면 Member save 후 개인정보 등록
                Member member = Member.builder().studentNumber(signInDto.getStudentNumber()).build();
                memberRepository.save(member);
                return ApiResponse.success("RequiredFirstRegistration", makeJWT(signInDto));
            }
        } else {                   // 유세인트 조회 실패
            return ApiResponse.error("FailedToLogin");
        }
    }
    @Transactional(readOnly = true)
    public SignInResponse makeJWT(SignInDto request) { // JWT 토큰 발급
        Member member = memberRepository.findByStudentNumber(request.getStudentNumber())
                .filter(it -> it.getStudentNumber().equals(request.getStudentNumber()))
                .orElseThrow(() -> new IllegalArgumentException("학번 오류 발생"));
        String token = tokenProvider.createToken(String.format("%s:%s", member.getId(), member.getType()));	// 토큰 생성
        return new SignInResponse(member.getStudentNumber(),"bearer", token);	// 생성자에 토큰 추가
    }

    public ApiResponse newRegister(HttpServletRequest request, UserDetailsDto userDetailsDto) {
        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            member.get().newRegister(userDetailsDto);
            member.get().changeFirstRegisterCheck(1);
            return ApiResponse.success("SuccessToFirstRegistration");
        } else {
            return ApiResponse.error("CantFindUser");
        }
    }

    public ApiResponse myCoinCount(HttpServletRequest request) {
        Optional<Member> member = getMemberFromToken(request);
        return member.map(value -> ApiResponse.success("SuccessCoinCount", MyCoinDto.builder().myCoinCount(value.getCoin()).build())).orElseGet(() -> ApiResponse.error("ErrorCoinCount"));
    }

    //메인 페이지
    @Transactional(readOnly = true)
    public ApiResponse mainPage(HttpServletRequest request, Pageable pageable) {
        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            Page<StickyNote> allStickyNoteList;
            MainAllDto mainAllDto = new MainAllDto();
            MainAllPageZeroDto mainAllPageZeroDto = new MainAllPageZeroDto();
            Gender gender = member.get().getSex();
            if (gender == Gender.MALE) { // 사용자가 남성일 경우
                allStickyNoteList = pagingRepository.findByGender(Gender.FEMALE, member.get().getMajor(), pageable); // 메인에 여성만 조회
            } else { // 사용자가 여성일 경우
                allStickyNoteList = pagingRepository.findByGender(Gender.MALE, member.get().getMajor(), pageable); // 메인에 남성만 조회
            }
            mainAllPageZeroDto.addBasicCounts(member.get().getNowStickyCount());
            mainAllPageZeroDto.addAllStickyCount(allStickyNoteList.getTotalElements());
            for (StickyNote mainStickyNote : allStickyNoteList) {
                Member stickyNoteMember = mainStickyNote.getMember();
                MainInfoDto mainInfoDto = MainInfoDto.builder()
                        .member(stickyNoteMember)
                        .stickyNote(mainStickyNote)
                        .build();
                MainIdDto mainIdDto = MainIdDto.builder()
                        .stickyNote(mainStickyNote)
                        .mainInfoDto(mainInfoDto)
                        .build();
                mainAllPageZeroDto.addMainIdDto(mainIdDto);
                mainAllDto.addMainIdDto(mainIdDto);
            }
            if (pageable.getPageNumber() == 0) { // 첫번 째 페이지면
                return ApiResponse.success("SuccessFirstMainPageAccess", mainAllPageZeroDto);
            } else {
                return ApiResponse.success("SuccessMainPageUpToOne", mainAllDto);
            }
        }

        return ApiResponse.error("FailMainPage");
    }

    // 마이페이지에서 보유 코인이랑전달
    @Transactional(readOnly = true)
    public ApiResponse myPage(HttpServletRequest request) {

        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            MyPageDto myPageDto = MyPageDto.builder()
                    .myStickyCount(stickyNoteRepository.findMyStickyNoteCount(member.get().getId()))
                    .build();
            return ApiResponse.success("SuccessToAccessMypage", myPageDto);
        }
        return ApiResponse.error("ErrorToAccessMypage");
    }

    // 개인정보 수정 버튼 눌렀을 때
    @Transactional(readOnly = true)
    public ApiResponse startModify(HttpServletRequest request) {
        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                    .member(member.get())
                    .build();
            return ApiResponse.success("UserInformation", userDetailsDto);
        }
        return ApiResponse.error("ErrorUserInformation");
    }

    // 개인정보 수정 완료 버튼 눌렀을 때
    public ApiResponse endModify(HttpServletRequest request, UserDetailsDto userDetailsDto) {
        Optional<Member> member = getMemberFromToken(request);
        member.ifPresent(value -> value.newRegister(userDetailsDto));
        return ApiResponse.success("SuccessToModify");
    }

    // 마이페이지 - 내가 등록한 포스트잇 확인
    @Transactional(readOnly = true)
    public ApiResponse myRegisteredSticky(HttpServletRequest request) {
        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            StickyAllDto stickyAllDto = new StickyAllDto();
            List<StickyNote> findStickyNotes = stickyNoteRepository.findAllByMemberIdWithHobbiesAndIdeals(member.get().getId());
            for (StickyNote findStickyNote : findStickyNotes) {
                StickyInfoDto stickyInfoDto = StickyInfoDto.builder()
                        .member(member.get())
                        .stickyNote(findStickyNote)
                        .build();
                StickyIdDto stickyIdDto = StickyIdDto.builder()
                        .stickyNote(findStickyNote)
                        .stickyInfoDto(stickyInfoDto)
                        .build();
                stickyAllDto.addStickyIdDto((stickyIdDto));
            }
            return ApiResponse.success("ExistRegisterPostIt", stickyAllDto);
        }
        return ApiResponse.error("DoesNotExistRegisterPostIt");
    }

    // 마이페이지 - 내가 구매한 포스트잇 확인
    @Transactional(readOnly = true)
    public ApiResponse myBoughtSticky(HttpServletRequest request) {
        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            StickyAllDto stickyAllDto = new StickyAllDto();
            List<StickyNote> findStickyNotes = stickyNoteRepository.findAllByMemberIdWithPurchases(member.get().getId());
            for (StickyNote findStickyNote : findStickyNotes) {
                StickyInfoDto stickyInfoDto = StickyInfoDto.builder()
                        .member(member.get())
                        .stickyNote(findStickyNote)
                        .build();
                StickyIdDto stickyIdDto = StickyIdDto.builder()
                        .stickyNote(findStickyNote)
                        .stickyInfoDto(stickyInfoDto)
                        .build();
                stickyAllDto.addStickyIdDto((stickyIdDto));
            }
            return ApiResponse.success("ExistBuyPostIt", stickyAllDto);
        }
        return ApiResponse.error("DoesNotExistBuyPostIt");

    }

    // 회원 탈퇴
    public ApiResponse deleteMEmber(HttpServletRequest request) {
        Optional<Member> member = getMemberFromToken(request);
        if (member.isPresent()) {
            memberRepository.delete(member.get());
            return ApiResponse.success("SuccessDeleteUser");
        }
        return ApiResponse.error("ErrorDeleteUser");
    }


    // 내가 구매한 해당 포스트잇 삭제
    public ApiResponse deleteSticky(HttpServletRequest request, Long stickyId) {
        Optional<Member> member = getMemberFromToken(request);
        Optional<StickyNote> stickyNote = stickyNoteRepository.findById(stickyId);
        if (member.isPresent() && stickyNote.isPresent()) {
            purchaseRepository.deleteByBuyerAndStickyNote(member.get(), stickyNote.get());
            return ApiResponse.success("CompletedToRemove");
        }
        return ApiResponse.error("FailedToRemove");
    }

    // JWT 토큰에서 멤버 가져오는 메서드
    @Transactional(readOnly = true)
    public Optional<Member> getMemberFromToken(HttpServletRequest request) {
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        return memberRepository.findById(memberId);
    }

}
