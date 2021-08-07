package com.shine2share.system.repository;

import com.shine2share.common.Paging;
import com.shine2share.common.entity.system.User;
import com.shine2share.system.dto.SearchUserDto;

public interface UserRepository {
    User getUserByUserId(String userId);
    void createUser(User user);
    Paging<SearchUserDto> searchUser(String userId, int pageNum, int pageSize);
}
