package ARserver.ARserver.domain.controller;

import ARserver.ARserver.domain.service.UserService;
import ARserver.ARserver.domain.user.User;
import ARserver.ARserver.domain.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity home(
            @SessionAttribute(name="userId", required = false) Long userId
    ){
        if(userId == null){
            return ResponseEntity.status(401).body("아이디를 찾을 수 없습니다");
        }
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/join")
    public ResponseEntity join(
            @RequestParam String email,
            @RequestParam String password
    ){
        User user = new User(email, password);
        userService.join(user);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request
    ){
        Long userId = userService.login(email, password);
        if(userId == null)
            return ResponseEntity.status(404).body("이메일을 찾을 수 없습니다");
        request.getSession().invalidate();
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(1800);

        return ResponseEntity.ok(userId);


    }
}
