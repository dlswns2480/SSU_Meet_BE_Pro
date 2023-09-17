package SSU.SSU_Meet_BE.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckController {

    @GetMapping("/")
    public ResponseEntity<Void> main(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}