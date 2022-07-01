package com.example.server.service;

import com.example.server.constants.JsonResponse;
import com.example.server.constants.StatusCode;
import com.example.server.model.dao.user.UserMapper;
import com.example.server.model.dto.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private StatusCode statusCode;


    public String selectPw(String username) {
        return userMapper.findByUsername(Long.parseLong(username)).getPassword();
    }

    public ResponseEntity<StatusCode> updatepw(String username, User user){
        String pwCheck = selectPw(username);
        String nPw = user.getNPassword();
        if (bCryptPasswordEncoder.matches(user.getPassword(), pwCheck)){

            String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
            Matcher matcher = Pattern.compile(pwPattern).matcher(nPw);
            if(!matcher.matches()){
                statusCode = StatusCode.builder().resCode(2)
                        .resMsg("비밀번호는 8~32자이어야 하며, 대/소문자, 숫자, 특수기호를 모두 포함해야 합니다.")
                        .build();
            }
            String eNPw = bCryptPasswordEncoder.encode(nPw);
            System.out.println(eNPw);
            userMapper.updateByUsername(User.builder().username(Long.parseLong(username))
                    .password(eNPw).build());
            statusCode = StatusCode.builder().resCode(0).resMsg("비밀번호 수정 성공").build();
        }
        else{
            statusCode = StatusCode.builder().resCode(2).resMsg("현재 비밀번호가 일치하지 않습니다..").build();
        }
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

    public ResponseEntity<StatusCode> myView(String username){
        System.out.println(username);
        statusCode = StatusCode.builder().resCode(0).resMsg("회원 정보 조회 성공")
                .data(userMapper.myView(Long.parseLong(username))).build();
        System.out.println(statusCode.getData().toString());
        return new JsonResponse().send(HttpStatus.OK, statusCode);
    }

}
