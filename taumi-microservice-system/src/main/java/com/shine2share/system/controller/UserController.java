package com.shine2share.system.controller;

import com.shine2share.core.base.ResponseData;
import com.shine2share.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @ApiOperation("API tìm kiếm thông tin người dùng")
    @GetMapping("sys/search-user")
    @PreAuthorize("#oauth2.hasScope('admin')")
    public ResponseEntity<ResponseData<Object>> searchUser(
            @RequestHeader(name = "clientMessageId") @ApiParam(value = "Unique ID for each request.", required = true) String clientMessageId,
            @RequestParam(required = false) @ApiParam("Mã người dùng") String userId,
            @RequestParam(required = false, defaultValue = "1") @ApiParam("Trang thứ ...") int pageNum,
            @RequestParam(required = false, defaultValue = "5") @ApiParam("Số bản ghi trên trang") int pageSize) {
        return ResponseEntity.ok().body(new ResponseData<>().success(this.userService.searchUser(userId, pageNum, pageSize)));
    }
}
