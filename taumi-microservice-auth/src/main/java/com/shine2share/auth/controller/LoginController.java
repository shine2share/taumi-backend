package com.shine2share.auth.controller;

import com.shine2share.auth.enums.GrantType;
import com.shine2share.auth.service.LoginService;
import com.shine2share.common.BusinessException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    private final LoginService loginService;
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation("API to create token when login")
    @GetMapping("/generate-token")
    public ResponseEntity<Object> generateToken(
            @RequestHeader(name = "Authorization", required = false) @ApiParam(example = "Basic c2hpbmUyc2hhcmU6SWxvdmV5b3VAMTk5Mg==") String authorization,
            @RequestParam GrantType grantType)
            throws BusinessException {
        return ResponseEntity.ok().body(this.loginService.generateToken(authorization, grantType));
    }
}
