package site.moasis.monolithicbe.domain.useraccount.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.configuration.AppProperties;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static site.moasis.monolithicbe.common.message.UserMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEmailService {

    private final JavaMailSender javaMailSender;
    private final AppProperties appProperties;
    private final ConcurrentHashMap<String, Integer> codeMap = new ConcurrentHashMap<>();

    public void sendCode(String email) {
        int code = generateCode();
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(new InternetAddress(appProperties.getMail().getUsername(), EMAIL_CERTIFICATION_MAIL_SENDER, "UTF-8"));
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(EMAIL_CERTIFICATION_MAIL_TITLE);
            mimeMessageHelper.setText(EMAIL_CERTIFICATION_MAIL_CONTENT_PREFIX + code + EMAIL_CERTIFICATION_MAIL_CONTENT_POSTFIX, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("사용자 이메일={}, 발급된 코드={}", email, code);
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
}
