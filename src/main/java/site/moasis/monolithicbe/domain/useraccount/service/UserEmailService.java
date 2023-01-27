package site.moasis.monolithicbe.domain.useraccount.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class UserEmailService {

    private static final String subject = "[Moasis]회원 가입 인증 이메일 입니다.";
    private final JavaMailSender javaMailSender;
    private final ConcurrentHashMap<String, Integer> codeMap = new ConcurrentHashMap<>();

    @Value("${spring.mail.username}")
    private String origin;

    public void sendCode(String email) {
        int code = generateCode();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(origin);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(generateText(code), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        codeMap.put(email, code);
    }

    public void certificate(String email, int code) {
        if (!codeMap.get(email).equals(code)) {
            throw new BusinessException(ErrorCode.CODE_MISMATCH);
        }
    }

    private int generateCode() {
        return new Random().nextInt(888888) + 111111;
    }

    private String generateText(int code) {
        return "<h2>홈페이지를 방문해주셔서 감사합니다</h2>" +
                "<br><br>" +
                "인증 번호는 [" +
                code +
                "] 입니다." +
                "<br>" +
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
    }
}
