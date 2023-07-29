package SSU.SSU_Meet_BE.Controller.Sticky;

import SSU.SSU_Meet_BE.Common.ApiResponse;
import SSU.SSU_Meet_BE.Dto.Sticky.StickyRegisterDto;
import SSU.SSU_Meet_BE.Service.Sticky.StickyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "포스트잇 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/sticky")
public class StickyController {

    private final StickyService stickyService;

    @Operation(summary = "포스트잇 등록")
    @PostMapping("/new")
    public ApiResponse newRegister(HttpServletRequest request, @RequestBody StickyRegisterDto stickyRegisterDto) {
        return stickyService.newRegister(request, stickyRegisterDto);
    }

}
