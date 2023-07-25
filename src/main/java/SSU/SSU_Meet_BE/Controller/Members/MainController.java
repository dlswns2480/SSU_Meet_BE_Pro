package SSU.SSU_Meet_BE.Controller.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.StickyDetailsDto;
import SSU.SSU_Meet_BE.Service.Members.MemberService;
import SSU.SSU_Meet_BE.Service.Members.StickyNoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MainController {
    private final MemberService memberService;
    private final StickyNoteService stickyNoteService;


    @Operation(summary = "포스트잇 등록")
    @PostMapping("/sticky/new")
    public ApiResponse newResisterSticky(HttpServletRequest request, @RequestBody StickyDetailsDto stickyDetailsDto){
        return stickyNoteService.resisterStickyNote(request, stickyDetailsDto);
    }
}
