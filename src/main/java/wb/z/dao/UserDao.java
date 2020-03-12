package wb.z.dao;

import wb.z.bean.User;

import java.util.List;

public interface UserDao {

    int add(User user);

    int login(User user);

    int update(User user);

    int delete(Long id);

    User findUser(Long id);

    List<User> findUserList();
}