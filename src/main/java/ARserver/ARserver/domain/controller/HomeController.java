package ARserver.ARserver.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {
    @CrossOrigin
    @GetMapping("/payment")
    public Mono<Rendering> payment() {
        return Mono.just(Rendering.view("payment").build());
    }
}
