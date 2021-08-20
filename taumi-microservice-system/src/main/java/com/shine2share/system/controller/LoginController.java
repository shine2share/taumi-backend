package com.shine2share.system.controller;

import com.shine2share.core.base.ResponseData;
import com.shine2share.common.BusinessException;
import com.shine2share.system.enums.GrantType;
import com.shine2share.system.service.LoginService;
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
    public ResponseEntity<ResponseData<Object>> generateToken(
            @RequestHeader(name = "Authorization", required = false) @ApiParam(example = "Basic c2hpbmUyc2hhcmU6SWxvdmV5b3VAMTk5Mg==") String authorization,
            @RequestParam GrantType grantType, @RequestParam(required = false) String token)
            throws BusinessException {
        return ResponseEntity.ok().body(new ResponseData<>().success(this.loginService.generateToken(authorization, grantType, token)));
    }

    @ApiOperation("API revoke token when logout")
    @DeleteMapping("/revoke-token")
    public ResponseEntity<Object> revoke(@RequestParam String token,
                                                @RequestParam(required = false) String clientId,
                                                @RequestParam(required = false) String clientSecret) {
        this.loginService.delete(token);
        return ResponseEntity.ok().build();
    }
}
