package com.shine2share.system.service;

import com.shine2share.common.Paging;
import com.shine2share.system.dto.SearchUserDto;
import com.shine2share.system.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Paging<SearchUserDto> searchUser(String userName, int pageNum, int pageSize) {
        return this.userRepository.searchUser(userName, pageNum, pageSize);
    }
}
