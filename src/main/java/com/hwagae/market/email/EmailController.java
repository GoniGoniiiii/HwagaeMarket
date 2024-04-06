package com.hwagae.market.email;

import com.hwagae.market.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor

public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    private String storedRandomString; // 이메일로 보낸 randomString을 저장하는 변수
    private String emailAuthResult; // 이메일 인증 결과를 저장할 변수



    // 랜덤 문자열 생성 메서드는 EmailService 클래스에 구현하거나 별도의 Util 클래스에 구현할 수 있음
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);
        System.out.println("randomString = " + randomString);
        SecureRandom secureRandom = new SecureRandom();
        System.out.println("secureRandom = " + secureRandom);

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody String user_email) {
        // user_email 값에서 'user_email='을 삭제하고, '%40'을 '@'로 변경하여 올바른 이메일 주소로 변환
        String userEmail = user_email.replace("user_email=", "").replace("%40", "@");

        // userEmail에는 사용자가 입력한 올바른 이메일 주소가 전달됨
        System.out.println("111111111111111111111111111 " + userEmail);

        String randomString = generateRandomString(8); // 8자리의 랜덤 문자열 생성
        System.out.println("222222222222222222222222222 " + userEmail);

        storedRandomString = randomString; // 보낸 randomString 저장
        System.out.println("333333333333333333333333333 " + randomString);

        String subject = "[화개장터] 인증확인 메일입니다.";
        System.out.println("4444444444444444444444444444 " + userEmail);

        String text = "인증번호 : " + randomString;
        System.out.println("5555555555555555555555555555 " + userEmail);


        String checkResult = userService.emailCheck(userEmail);
        if(checkResult != null){
            emailService.sendEmail(userEmail, subject, text);
            System.out.println("6666666666666666666666666666 " + userEmail);
            return ResponseEntity.ok("success");
        }else {
            System.out.println(" 이미 사용중인 이메일");
            return ResponseEntity.ok("duplicate");
        }

    }

    // 이메일 인증 확인 요청 처리
    @PostMapping("/authKey-check")
    public String checkAuthKey(@RequestBody String emailAuthKey) {
        System.out.println("뭘로받아오지? = " + emailAuthKey);
        String authKey = emailAuthKey.replace("emailAuthKey=", "");

        if (storedRandomString.equals(authKey)) {
            // 저장된 randomString과 입력한 emailAuthKey가 일치할 경우
            System.out.println("인증 성공"+authKey);
            emailAuthResult = "ok"; // 인증 결과를 저장
            return "ok";
        } else {
            // 불일치할 경우
            System.out.println("인증 실패"+authKey);
            emailAuthResult = "fail"; // 인증 결과를 저장
            return "fail";
        }
    }

    // 이메일 인증 결과를 반환하는 메서드
    public String getEmailAuthResult() {
        return emailAuthResult;
    }


}