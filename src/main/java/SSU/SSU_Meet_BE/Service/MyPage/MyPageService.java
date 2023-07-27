package SSU.SSU_Meet_BE.Service.MyPage;


import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.MyPageHomeDto;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Entity.Member;
import SSU.SSU_Meet_BE.Repository.MemberRepository;
import SSU.SSU_Meet_BE.Security.JwtAuthenticationFilter;
import SSU.SSU_Meet_BE.Security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenProvider tokenProvider;
    /** 마이페이지에서 필요한거 현재 보유 코인, 등록되어있는 포스트잇
     * */


    public ApiResponse mypageHome(HttpServletRequest request) throws IOException{
        //jwt 토큰 유효한지 검사
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        // 마이페이지에 필요한 정보들 db에서 꺼내서 리턴하기
        //마이페이지에 필요한 정보 : 보유 코인, 등록한 포스트잇 갯수
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()){
            MyPageHomeDto myPageHomeDto = MyPageHomeDto.get_info(member.get());
            return ApiResponse.success("마이페이지 정보가져오기 성공", myPageHomeDto);
        }
        else{
            return ApiResponse.error("마이페이지 정보 가져오기 실패");
        }
    }

    public ApiResponse modify(HttpServletRequest request) throws IOException{
        // jwt 토큰 유효한지 검사
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        //member entitiy 정보 다 리턴해주기
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()){
            UserDetailsDto returnInfo = UserDetailsDto.modifyInfo(member.get());
            return ApiResponse.success("개인정보 불러오기 성공",returnInfo);
        }
        else{
            return ApiResponse.error("개인정보 불러오기 실패");
        }
        // db에서 필요한 정보들 담아서 return해주기
    };

    public ApiResponse modify(HttpServletRequest request,UserDetailsDto userDetailsDto) throws IOException{
        //jwt 토큰 유효 검사
        String token = jwtAuthenticationFilter.parseBearerToken(request); // bearer 파싱
        Long memberId = Long.parseLong(tokenProvider.validateTokenAndGetSubject(token).split(":")[0]);
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            member.get().newRegister(userDetailsDto);
            return ApiResponse.success("개인정보 수정 성공");
        }
        else{
            return ApiResponse.error("개인정보 수정 실패");
        }
    }


}
