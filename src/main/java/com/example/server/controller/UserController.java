package com.example.server.controller;

import com.example.server.config.auth.PrincipalDetails;
import com.example.server.constants.StatusCode;
import com.example.server.model.dto.user.User;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/myView")
    public ResponseEntity<StatusCode> myView(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return userService.myView(principalDetails.getUsername());
    }

    @PostMapping("/updatePw")
    public ResponseEntity<StatusCode> updatepw(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @RequestBody User user)
    {
        return userService.updatepw(principalDetails.getUsername(), user);
    }

//    @PostMapping("/logoutUser")
//    public ResponseEntity<StatusCode> logout(@AuthenticationPrincipal PrincipalDetails principalDetails){
//        return userService.logout(principalDetails.getUsername());
//    }
}
