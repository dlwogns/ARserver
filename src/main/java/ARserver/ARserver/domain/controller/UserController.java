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
            return ResponseEntity.status(202).body("로그인을 하지 않았습니다");
        }
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/join")
    public ResponseEntity join(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ){
        User user = new User(name, email, password);
        if(userService.checkLoginEmailDuplicated(user))
            return ResponseEntity.status(202).body("이미 가입한 이메일이 있습니다");
        userService.join(user);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request
    ){
        User user = userService.login(email, password);
        if(user == null)
            return ResponseEntity.status(202).body("등록되지 않은 이메일이거나 비밀번호가 틀립니다");
        request.getSession().invalidate();
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(1800);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
            return ResponseEntity.ok("로그아웃 성공");
        }
        return ResponseEntity.status(202).body("로그아웃 실패");

    }
}
