package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.config.jwt.JwtProperties;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.token.Token;
import com.example.server.model.dto.user.User;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @GetMapping("/myView")
    public ResponseEntity<StatusCode> myView(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return userService.myView(principalDetails.getUsername());
    }

    @PostMapping("/updatePw")
    public ResponseEntity<StatusCode> updatepw(HttpServletRequest request,
                                               @RequestBody User user)
    {
        return userService.updatepw(request, user);
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<StatusCode> logout(HttpServletRequest request){
        return userService.logout(request);
    }
}
