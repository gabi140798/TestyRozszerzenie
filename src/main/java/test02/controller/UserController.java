package test02.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test02.dto.UserDto;
import test02.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/with-transfers")
    public ResponseEntity<Page<UserDto>> getUsersWithTransfers(Pageable pageable) {
        Page<UserDto> users = userService.getUsersWithTransferCount(pageable);
        return ResponseEntity.ok(users);
    }
}
