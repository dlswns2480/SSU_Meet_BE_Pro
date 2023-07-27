package SSU.SSU_Meet_BE.Controller.MyPage;


import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Service.MyPage.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "마이 페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이 페이지 홈")
    @GetMapping("/mypage")
    public ApiResponse mypage(HttpServletRequest request) throws Exception{
        return myPageService.mypageHome(request);
    }

    @GetMapping("/modify")
    public ApiResponse modify(HttpServletRequest request) throws IOException{
        // 포스트잇 정보들 db에서 찾아서 리턴하기
        return myPageService.modify(request);
    }

    @PostMapping("/modify")
    public ApiResponse modify_(HttpServletRequest request, @RequestBody UserDetailsDto userDetailsDto) throws IOException{
        return myPageService.modify(request,userDetailsDto);
    }


}
