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

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
//        System.out.print("userName :" +username + "  nickName:" + userNickName + " userMessage:" + userMessage + " userPersonalizedSignature" + userPersonalizedSignature );

        ChangeUserMessage changeUserMessage = new ChangeUserMessage();
        changeUserMessage.updateUserMessage();
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
            System.out.print("\n---" + e.toString() + "---\n");
            return e.toString();
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
            String thumbnailPath;
            String virtualPath;
            if (IS_DEBUG_IN_LOCAL){
                path = "D:/111";
                virtualPath = "http://localhost:8080/image/";
            }else {
                path = "/home/NumerousStudentsMomentsPicture";
                thumbnailPath = "/home/thumbnail";
                virtualPath = "http://175.24.23.24:8080/image/";
            }
            String fileName = momentPicture.getOriginalFilename();
//            momentsItem.setMomentPicturePath(path+ "/" + fileName);
            momentsItem.setMomentPicturePath(virtualPath + fileName);

            File dest = new File(new File(path).getAbsolutePath()+ "/" + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                momentPicture.transferTo(dest); // 保存文件
                imgConvert(dest.getAbsolutePath(),thumbnailPath + "/" + fileName);       //生成缩略图
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

    @RequestMapping(value = "/addNoPictureMoment")
    public String addMoment(String momentsId,
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
        momentsItem.setMomentPicturePath("null");
        int state = mMomentsDao.addMomentsItem(momentsItem);
        return "true";
    }

    @RequestMapping(value = "/readMoment")
    public String readMoment(){
        return mMomentsDao.readMoments();
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


    public void imgConvert(String oldImgPath, String newImgPath) {
        try {
            File fi = new File(oldImgPath); //大图文件
            File fo = new File(newImgPath); //将要转换出的小图文件
            if (!fo.exists()) {
                fo.getParentFile().mkdirs();
                //创建文件
                fo.createNewFile();
            }
            int nw = 200;
			/*
			AffineTransform 类表示 2D 仿射变换，它执行从 2D 坐标到其他 2D
			坐标的线性映射，保留了线的“直线性”和“平行性”。可以使用一系
			列平移、缩放、翻转、旋转和剪切来构造仿射变换。
			*/
            AffineTransform transform = new AffineTransform();
            BufferedImage bis = ImageIO.read(fi); //读取图片
            int w = bis.getWidth();
            int h = bis.getHeight();
            int nh = (nw * h) / w;
            double sx = (double) nw / w;
            double sy = (double) nh / h;
            transform.setToScale(sx, sy); //setToScale(double sx, double sy) 将此变换设置为缩放变换。
			/*
			 * AffineTransformOp类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或
			 *  Raster 中 2D 坐标的线性映射。所使用的插值类型由构造方法通过
			 *  一个 RenderingHints 对象或通过此类中定义的整数插值类型之一来指定。
			如果在构造方法中指定了 RenderingHints 对象，则使用插值提示和呈现
			的质量提示为此操作设置插值类型。要求进行颜色转换时，可以使用颜色
			呈现提示和抖动提示。 注意，务必要满足以下约束：源图像与目标图像
			必须不同。 对于 Raster 对象，源图像中的 band 数必须等于目标图像中
			的 band 数。
			*/
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            /*
             * TYPE_3BYTE_BGR 表示一个具有 8 位 RGB 颜色分量的图像，
             * 对应于 Windows 风格的 BGR 颜色模型，具有用 3 字节存
             * 储的 Blue、Green 和 Red 三种颜色。
             */
            ato.filter(bis, bid);
            ImageIO.write(bid, "jpeg", fo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
