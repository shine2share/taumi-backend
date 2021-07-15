package com.shine2share.system.repository;

import com.shine2share.common.entity.system.User;

public interface UserRepository {
    User getUserByUserId(String userId);
    void createUser(User user);
}
