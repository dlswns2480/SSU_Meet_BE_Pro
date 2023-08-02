package SSU.SSU_Meet_BE.Controller.Main;


import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.StickyDetailDto;
import SSU.SSU_Meet_BE.Dto.Members.UserDetailsDto;
import SSU.SSU_Meet_BE.Entity.StickyNote;
import SSU.SSU_Meet_BE.Service.MainPage.MainPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "메인화면 API")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MainController {

    private final MainPageService mainPageService;

        /** jwt 정보를 지워야함  리턴 로그아웃 성공 받으면 로그인창으로 */


    @Operation(summary = "포스트잇 등록")
    @PostMapping("/sticky/new")
    public ApiResponse registerNote(HttpServletRequest request, @RequestBody StickyDetailDto stickyDetailDto) throws Exception{
        return mainPageService.registerNote(request,stickyDetailDto);
    }
}
