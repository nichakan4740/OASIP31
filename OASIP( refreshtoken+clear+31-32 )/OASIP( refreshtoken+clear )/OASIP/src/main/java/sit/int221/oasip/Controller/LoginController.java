package sit.int221.oasip.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasip.DTO.LoginDTO;
import sit.int221.oasip.Service.JwtUserDetailsService;
import sit.int221.oasip.Service.UserService;
import sit.int221.oasip.config.JwtTokenUtil;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private UserService service;

    @GetMapping("")
    public String home() {
        return "Welcome to Login";
    }

    @PostMapping("")
    public ResponseEntity Login(@Validated @RequestBody LoginDTO userlogin) throws Exception {
        return service.login(userlogin);}

}
