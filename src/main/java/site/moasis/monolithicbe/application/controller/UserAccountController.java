package site.moasis.monolithicbe.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountJoinRequestDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.service.UserAccountService;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping
    public UserAccount join(@RequestBody UserAccountJoinRequestDto userAccountJoinRequestDto) {
        return userAccountService.create(userAccountJoinRequestDto);
    }
}
