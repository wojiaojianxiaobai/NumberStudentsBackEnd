package wb.z.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import wb.z.Config.StateConfig;
import wb.z.bean.User;


@Repository
public class UserDaoJdbcTemplateImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int add(User user) {
        String sql = "insert into t_author(user_name,user_password) " +
                "values(:userName,:userPassword)";
        Map<String, Object> param = new HashMap<>();
        param.put("userName", user.getUserName());
        param.put("userPassword", user.getUserPassword());
        return (int) namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public int login(User user) {
        Map<String, Object> param = new HashMap<>();
        String userName = user.getUserName();
        String sql = "SELECT * FROM springboot_db.t_author " +
                "where user_name = " + userName;
        System.out.println("wb.z : userName " + userName);
        BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        List<User> userList = new ArrayList<>();
        userList = namedParameterJdbcTemplate.query(sql, new HashMap<>(), new BeanPropertyRowMapper<User>(User.class));
        if (userList.size() < 1) {
            System.out.println("wb.z : 登录失败，用户名不存在");
            return StateConfig.LOGIN_FAIL_USERNAME_ERROR;

        } else if (userList.get(0).getUserPassword().equals(user.getUserPassword())) {
            System.out.println("wb.z : 登录成功");
            return StateConfig.LOGIN_SUCCESS;
        } else {
            System.out.println("wb.z : 登录失败");
            return StateConfig.LOGIN_FAIL_PASSWORD_ERROR;
        }
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public User findUser(Long id) {

        return null;
    }

    @Override
    public List<User> findUserList() {
        return null;
    }
}