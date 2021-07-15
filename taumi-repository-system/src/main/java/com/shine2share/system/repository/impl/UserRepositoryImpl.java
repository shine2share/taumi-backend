package com.shine2share.system.repository.impl;
import com.shine2share.common.entity.system.User;
import com.shine2share.system.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUserByUserId(String userId) {
        String sql = "SELECT u FROM User u WHERE u.userId = :userId";
        List<User> list = (List<User>) entityManager.createQuery(sql).setParameter("userId", userId).getResultList();
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }
}
