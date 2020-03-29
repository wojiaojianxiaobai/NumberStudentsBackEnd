package wb.z.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wb.z.Config.StateConfig;
import wb.z.bean.MomentsItem;
import wb.z.bean.StateMessage;
import wb.z.bean.User;
import wb.z.dao.MomentsDao;
import wb.z.dao.UserDao;

import java.io.File;
import java.util.HashMap;

/*@ResponseBody   //返回return内容
@Controller*/
@RestController
public class HelloController {

    private static final boolean IS_DEBUG_IN_LOCAL = false;
    @RequestMapping(value = "/login")
    public String Login(String username, String password) {

        User user = login(username, password);
        int status = user.getUserState();
        switch (status) {
            case StateConfig.LOGIN_SUCCESS:{

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("state",true);
                jsonObject.put("message","登录成功");
                System.out.println("userMessage: " + user.getUserMessage());
                jsonObject.put("userMessage",user.getUserMessage());
                jsonObject.put("userNickName",user.getUserNickName());
                jsonObject.put("userPersonalizedSignature",user.getUserPersonalizedSignature());
                jsonObject.put("studyPassword",user.getStudyPassword());
                System.out.println("登录成功！");
                return jsonObject.toString();
            }

            case StateConfig.LOGIN_FAIL_PASSWORD_ERROR:{
                System.out.println("密码：" + user.getUserPassword());
                return StateMessage.loginFailPasswordError();
            }

            case StateConfig.LOGIN_FAIL_USERNAME_ERROR:{
                System.out.println("账号：" + user.getUserName());
                return StateMessage.loginFailUserNameError();
            }
            default:
                return StateMessage.unKnowError();
        }

    }

    @RequestMapping(value = "/register")
    public String Register(String username, String password) {
        try {
            register(username, password);
            return StateMessage.registerSuccess();
        } catch (DuplicateKeyException e) {
            System.out.print(e.toString());
            return StateMessage.registerFailUserNameError();
        } catch (Exception e) {
            System.out.print(e.toString());
            return StateMessage.unKnowError();
        } finally {
            System.out.println("插入完成！");
        }

    }

    @RequestMapping(value = "/updateUserMessage")
    public String updateUserMessage(String username,String userNickName,String userMessage,String userPersonalizedSignature) {
        System.out.print("userName :" +username + "  nickName:" + userNickName + " userMessage:" + userMessage + " userPersonalizedSignature" + userPersonalizedSignature );
        try {
            int state;
            User user = new User();
            user.setUserName(username);
            user.setUserNickName(userNickName);
            user.setUserMessage(userMessage);
            user.setUserPersonalizedSignature(userPersonalizedSignature);
            state =  userDao.updateUserMessage(user);
            if (state == StateConfig.OPERATION_SUCCESS){
                return "修改成功";
            }else {
                return "修改失败";
            }

        } catch (DuplicateKeyException e) {
            return StateMessage.registerFailUserNameError();
        } catch (Exception e) {
            System.out.print(e.toString());
            return StateMessage.unKnowError();
        } finally {
            System.out.println("插入完成！");
        }

    }

    @RequestMapping(value = "/changeUserPassword")
    public String changeUserPassword(String username,String oldUserPassword,String newUserPassword) {
        try {
            User user = new User();
            user.setUserName(username);
            user.setUserPassword(oldUserPassword);
            int state = userDao.login(user).getUserState();
            if (state == StateConfig.LOGIN_SUCCESS){        //用登录的方法验证账号密码是否正确
                user.setUserPassword(newUserPassword);
                userDao.changeUserPassword(user);
                return "修改成功";
            }else{
                return "原密码不正确";
            }

        } catch (DuplicateKeyException e) {
            return StateMessage.registerFailUserNameError();
        } catch (Exception e) {
            System.out.print(e.toString());
            return StateMessage.unKnowError();
        } finally {
            System.out.println("插入完成！");
        }

    }

    @RequestMapping(value = "/changeStudyPassword")
    public String changeStudyPassword(String username,String oldStudyPassword,String newStudyPassword) {
        try {
            User user = new User();
            user.setUserName(username);
            user.setStudyPassword(oldStudyPassword);
            int state = userDao.changeStudyPassword(user,newStudyPassword);
            if (state == StateConfig.OPERATION_SUCCESS){
                return "修改成功";
            }else{
                return "原学霸密码不正确";
            }

        } catch (DuplicateKeyException e) {
            return StateMessage.registerFailUserNameError();
        } catch (Exception e) {
            System.out.print(e.toString());
            return StateMessage.unKnowError();
        } finally {
            System.out.println("修改完成！");
        }

    }

    @RequestMapping(value = "/addMoment")
    public String addMoment(String momentsId,
                            MultipartFile momentPicture,
                             String userName,
                            String userNickName,
                            String addMomentTime,
                            String momentTitle,
                            String momentContent) {

        MomentsItem momentsItem = new MomentsItem();
        momentsItem.setMomentId(momentsId);
        momentsItem.setUserName(userName);
        momentsItem.setUserNickName(userNickName);
        momentsItem.setMomentTime(addMomentTime);
        momentsItem.setMomentTitle(momentTitle);
        momentsItem.setMomentContent(momentContent);

        if (!momentPicture.isEmpty()) {
            String path;
            if (IS_DEBUG_IN_LOCAL){
                path = "D:/111";
            }else {
                path = "/home/NumerousStudentsMomentsPicture";
            }
            String fileName = momentPicture.getOriginalFilename();
//            momentsItem.setMomentPicturePath(path+ "/" + fileName);
            momentsItem.setMomentPicturePath(fileName);

            File dest = new File(new File(path).getAbsolutePath()+ "/" + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                momentPicture.transferTo(dest); // 保存文件
            } catch (Exception e) {
                e.printStackTrace();
                return "false";
            }
        }else {
            momentsItem.setMomentPicturePath("null");
        }

        System.out.print(" momentsId :" + momentsId + " momentPicture:" + momentPicture.getOriginalFilename());
        System.out.print(" userName :" + userName + " userNickName:" + userNickName);
        System.out.print(" addMomentTime :" + addMomentTime + " momentTitle:" + momentTitle);
        System.out.print(" momentContent :" + momentContent);

        int state = mMomentsDao.addMomentsItem(momentsItem);
        return "true";

    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private MomentsDao mMomentsDao;

    public void register(String userId, String userPassword) {
        User user = new User();
        user.setUserName(userId);
        user.setUserPassword(userPassword);
        user.setStudyPassword("null");
        userDao.add(user);
    }

    public User login(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(password);
        return userDao.login(user);
    }
}
