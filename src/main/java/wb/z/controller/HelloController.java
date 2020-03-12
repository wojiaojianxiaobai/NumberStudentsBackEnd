package wb.z.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wb.z.Config.StateConfig;
import wb.z.bean.StateMessage;
import wb.z.bean.User;
import wb.z.dao.UserDao;

/*@ResponseBody   //返回return内容
@Controller*/
@RestController
public class HelloController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(String username, String password) {

        int status = login(username, password);

        switch (status) {
            case StateConfig.LOGIN_SUCCESS:{
                return StateMessage.loginSuccess();
            }

            case StateConfig.LOGIN_FAIL_PASSWORD_ERROR:{
                return StateMessage.loginFailPasswordError();
            }

            case StateConfig.LOGIN_FAIL_USERNAME_ERROR:{
                return StateMessage.loginFailUserNameError();
            }
            default:
                return StateMessage.unKnowError();
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)   //接受浏览器的hello
    public String Register(String username, String password) {
        try {
            register(username, password);
            return StateMessage.registerSuccess();
        } catch (DuplicateKeyException e) {
            return StateMessage.registerFailUserNameError();
        } catch (Exception e) {
            return StateMessage.unKnowError();
        } finally {
            System.out.println("插入完成！");
        }

    }

    @Autowired
    private UserDao userDao;

    public void register(String userId, String userPassword) {
        User user = new User();
        user.setUserName(userId);
        user.setUserPassword(userPassword);
        userDao.add(user);
    }

    public int login(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(password);
        return userDao.login(user);
    }
}
