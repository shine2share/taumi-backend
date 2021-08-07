package com.shine2share.system.controller;

import com.shine2share.base.ResponseData;
import com.shine2share.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation("API tìm kiếm thông tin người dùng")
    @GetMapping("sys/search-user")
    public ResponseEntity<ResponseData<Object>> searchUser(
            @RequestParam(required = false) @ApiParam("Mã người dùng") String userId,
            @RequestParam(required = false, defaultValue = "1") @ApiParam("Trang thứ ...") int pageNum,
            @RequestParam(required = false, defaultValue = "5") @ApiParam("Số bản ghi trên trang") int pageSize) {
        return ResponseEntity.ok().body(new ResponseData<>().success(this.userService.searchUser(userId, pageNum, pageSize)));
    }
}
