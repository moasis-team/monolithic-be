package site.moasis.monolithicbe.domain.useraccount.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.configuration.AppProperties;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.ResetPasswordRequestDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static site.moasis.monolithicbe.common.message.UserMessage.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserEmailService {

    private final JavaMailSender javaMailSender;
    private final AppProperties appProperties;
    private final ConcurrentHashMap<String, Integer> codeMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> urlMap = new ConcurrentHashMap<>();
    private final UserAccountReadService userAccountReadService;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;


    public void sendCode(String email) {
        userAccountReadService.checkDuplicatedEmail(email);
        int code = generateCode();

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(new InternetAddress(appProperties.getMail().getUsername(), MAIL_SENDER, "UTF-8"));
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(EMAIL_CERTIFICATION_MAIL_TITLE);
            mimeMessageHelper.setText(EMAIL_CERTIFICATION_MAIL_CONTENT_PREFIX + code + EMAIL_CERTIFICATION_MAIL_CONTENT_POSTFIX, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "user.byEmail", List.of(email));
        }
        log.info("사용자 이메일={}, 발급된 코드={}", email, code);
        codeMap.put(email, code);
    }

    public void sendResetUrl(ResetPasswordRequestDto resetPasswordRequestDto) {
        UserAccount userAccount = userAccountRepository.findByEmail(resetPasswordRequestDto.email()).orElseThrow();
        if (!userAccount.getPhoneNumber().equals(resetPasswordRequestDto.phoneNumber())) {
            throw new BusinessException(ErrorCode.ACCOUNT_MISMATCH);
        }

        UUID uuid = UUID.randomUUID();
        String url = "https://www.moasis.site/users/unauthorized-password" + uuid;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setFrom(new InternetAddress(appProperties.getMail().getUsername(), MAIL_SENDER, "UTF-8"));
            mimeMessageHelper.setTo(resetPasswordRequestDto.email());
            mimeMessageHelper.setSubject(PASSWORD_RESET_MAIL_TITLE);
            mimeMessageHelper.setText(PASSWORD_RESET_MAIL_CONTENT_PREFIX + url + PASSWORD_RESET_MAIL_CONTENT_POSTFIX, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        log.info("사용자 이메일={}, 발급된 url={}", resetPasswordRequestDto.email(), uuid);
        urlMap.put(uuid, resetPasswordRequestDto.email());
    }

    public void certificate(String email, int code) {
        if (!codeMap.get(email).equals(code)) {
            throw new BusinessException(ErrorCode.CODE_MISMATCH, "user.byCode", List.of(email));
        }
        codeMap.remove(email);
    }

    public void certificateResetUrl(UUID url, String email) {
        if (!urlMap.get(url).equals(email)) {
            throw new BusinessException(ErrorCode.ACCOUNT_MISMATCH, "user.byCode", List.of(email));
        }
        urlMap.remove(url);
    }

    public void resetPassword() {

    }

    private int generateCode() {
        return new Random().nextInt(888888) + 111111;
    }
}
