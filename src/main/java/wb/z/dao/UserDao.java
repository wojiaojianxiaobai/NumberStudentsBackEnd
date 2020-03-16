package wb.z.dao;

import wb.z.bean.User;

import java.util.List;

public interface UserDao {

    int add(User user);

    User login(User user);

    int update(User user);

    int updateUserMessage(User user);

    int changeUserPassword(User user);

    int changeStudyPassword(User user,String newStudyPassword);

    int delete(Long id);

    User findUser(Long id);

    List<User> findUserList();
}