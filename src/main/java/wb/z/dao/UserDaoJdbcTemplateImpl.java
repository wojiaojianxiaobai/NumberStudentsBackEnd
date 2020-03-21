package wb.z.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        int curTime = (int) System.currentTimeMillis()/100000;
        String sql = "insert into t_author(user_name,user_password,user_message,user_nick_name,study_password,user_personalized_signature) " +
                "values(:userName,:userPassword,:userMessage,:userNickName,:studyPassword,:userPersonalizedSignature )";
        Map<String, Object> param = new HashMap<>();
        param.put("userName", user.getUserName());
        param.put("userPassword", user.getUserPassword());
        param.put("userMessage", "null");
        param.put("studyPassword","null");
        param.put("userNickName","学霸" + curTime);
        param.put("userPersonalizedSignature","这个人很懒，什么也没留下");
        return (int) namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public User login(User user) {
        String userName = user.getUserName();
        String sql = "SELECT * FROM springboot_db.t_author " +
                "where user_name = " + userName;
        System.out.println("wb.z : userName " + userName);
        BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        List<User> userList = new ArrayList<>();
        userList = namedParameterJdbcTemplate.query(sql, new HashMap<>(), new BeanPropertyRowMapper<User>(User.class));
        User curUser = new User();
        if (userList.size() < 1) {
            System.out.println("wb.z : 登录失败，用户名不存在");
            curUser.setUserState(StateConfig.LOGIN_FAIL_USERNAME_ERROR);

        } else if (userList.get(0).getUserPassword().equals(user.getUserPassword())) {
            System.out.println("wb.z : 登录成功");
            curUser = userList.get(0);
            curUser.setUserState(StateConfig.LOGIN_SUCCESS);
            curUser.setUserMessage(userList.get(0).getUserMessage());
        } else {
            curUser = userList.get(0);
            System.out.println("wb.z : 登录失败" + user.getUserPassword());
            curUser.setUserState(StateConfig.LOGIN_FAIL_PASSWORD_ERROR);
        }
        return curUser;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int updateUserMessage(User user) {
        String sql = "UPDATE springboot_db.t_author SET "
                + "user_message = " + "'" + user.getUserMessage() + "'"
                + ", user_personalized_signature = " + "'" + user.getUserPersonalizedSignature() + "'"
                + ", user_nick_name = " + "'" + user.getUserNickName() + "'"
                + " WHERE user_name = " + user.getUserName();
        System.out.print("wb.z :" + user.getUserPersonalizedSignature());
        Map<String, Object> param = new HashMap<>();
        param.put("userMessage",user.getUserMessage());
        param.put("userPersonalizedSignature",user.getUserPersonalizedSignature());
        param.put("userNickName",user.getUserNickName());
        namedParameterJdbcTemplate.update(sql,param);
        return StateConfig.OPERATION_SUCCESS;
    }

    @Override
    public int changeUserPassword(User user) {

        String sql = "UPDATE springboot_db.t_author SET user_password = " + user.getUserPassword()
                + " WHERE user_name = " + user.getUserName();
        Map<String, Object> param = new HashMap<>();
        param.put("userPassword",user.getUserPassword());
        namedParameterJdbcTemplate.update(sql,param);
        return 1;
    }

    @Override
    public int changeStudyPassword(User user,String newStudyPassword) {
        String userName = user.getUserName();
        String sql = "SELECT * FROM springboot_db.t_author " +
                "where user_name = " + userName;
        System.out.println("wb.z : userName " + userName);
        List<User> userList = new ArrayList<>();
        userList = namedParameterJdbcTemplate.query(sql, new HashMap<>(), new BeanPropertyRowMapper<User>(User.class));
        if (userList.size() < 1) {
            System.out.println("wb.z : 验证失败，用户名不存在");
            return StateConfig.OPERATION_FAIL;
        } else if (userList.get(0).getStudyPassword().equals(user.getStudyPassword())) {
            System.out.println("wb.z : 验证通过");
            String changeStudyPasswordSql = "UPDATE springboot_db.t_author SET study_password = " + newStudyPassword
                    + " WHERE user_name = " + user.getUserName();
            Map<String, Object> param = new HashMap<>();
            param.put("userPassword",user.getUserPassword());
            namedParameterJdbcTemplate.update(changeStudyPasswordSql,param);
            return StateConfig.OPERATION_SUCCESS;
        } else {
            System.out.println("wb.z : 验证失败" + user.getStudyPassword());
            return StateConfig.OPERATION_FAIL;
        }
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