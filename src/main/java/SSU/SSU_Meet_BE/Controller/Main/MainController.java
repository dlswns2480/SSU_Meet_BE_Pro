//package SSU.SSU_Meet_BE.Controller.Main;
//
//
//import SSU.SSU_Meet_BE.Common.ApiResponse;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "메인화면 API")
//@RestController
//@RequestMapping("/v1")
//@RequiredArgsConstructor
//public class MainController {
//    @Operation(summary = "메인화면")
//    @GetMapping("/main")
//    public ApiResponse logout(HttpServletRequest request) throws Exception{
//        return mainService.mainpage(request);
//    }
//}
