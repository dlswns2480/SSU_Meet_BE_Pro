package SSU.SSU_Meet_BE.Controller.Members;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Members.StickyDetailsDto;
import SSU.SSU_Meet_BE.Service.Members.MemberService;
import SSU.SSU_Meet_BE.Service.Members.StickyNoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "포스트잇 구매")
    @GetMapping("/sticky/buy")
    public ApiResponse buyStickyNote(HttpServletRequest request, @RequestParam("stickyId") String stickyId){
        Long stickyIdLong = Long.parseLong(stickyId);
        return stickyNoteService.buyStickyNote(request, stickyIdLong);
    }
}
