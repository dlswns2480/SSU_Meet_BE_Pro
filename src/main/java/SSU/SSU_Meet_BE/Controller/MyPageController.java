package SSU.SSU_Meet_BE.Controller;


import SSU.SSU_Meet_BE.Service.StickyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class MyPageController {
    private final StickyService stickyService;
}
