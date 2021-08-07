package com.shine2share.system.repository.impl;
import com.shine2share.common.CommonUtil;
import com.shine2share.common.Paging;
import com.shine2share.common.entity.system.User;
import com.shine2share.system.dto.SearchUserDto;
import com.shine2share.system.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public UserRepositoryImpl(EntityManager entityManager, NamedParameterJdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
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

    @Override
    public Paging<SearchUserDto> searchUser(String userId, int pageNum, int pageSize) {
        String sql = "SELECT T2.* FROM (SELECT ROW_NUMBER() OVER (ORDER BY T1.USERID) ROW_NUM, COUNT(*) OVER() TOTAL_COUNT, T1.USERID, T1.USERNAME, T1.EMAIL, T1.CREATEDBY, T1.CREATEDTIME, T1.UPDATEDBY, T1.UPDATEDTIME, T1.ISACTIVE, T1.USERTYPE FROM T_SYS_USER T1) T2  WHERE 1 = 1";
        if (!CommonUtil.isNullOrEmpty(userId)) {
            sql += (" AND USERID = :userId");
        }
        sql += " AND T2.ROW_NUM > (:pageNum - 1) * :pageSize AND T2.ROW_NUM < :pageNum  * (:pageSize + 1)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("pageNum", pageNum)
                .addValue("pageSize", pageSize);
        AtomicLong totalItem = new AtomicLong(0);
        List<SearchUserDto> result = jdbcTemplate.query(sql, params, (rs, i) -> {
            totalItem.set(rs.getLong("TOTAL_COUNT"));
            return toUserDto(rs);
        });
        Paging<SearchUserDto> paging = new Paging<>();
        paging.setItems(result);
        paging.setTotalItem(totalItem.get());
        paging.setPageNum(pageNum);
        paging.setPageSize(pageSize);
        return paging;
    }

    private SearchUserDto toUserDto(ResultSet rs) throws SQLException {
        SearchUserDto dto = new SearchUserDto();
        dto.setUserId(rs.getString("USERID"));
        dto.setUserName(rs.getString("USERNAME"));
        dto.setEmail(rs.getString("EMAIL"));
        dto.setCreatedBy(rs.getString("CREATEDBY"));
        dto.setCreatedTime(rs.getString("CREATEDTIME"));
        dto.setUpdatedBy(rs.getString("UPDATEDBY"));
        dto.setUpdatedTime(rs.getString("UPDATEDTIME"));
        dto.setIsActive(rs.getString("ISACTIVE"));
        dto.setUserType(rs.getString("USERTYPE"));
        return dto;
    }
}
