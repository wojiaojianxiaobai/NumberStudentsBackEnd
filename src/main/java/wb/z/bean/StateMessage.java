package wb.z.bean;

import com.alibaba.fastjson.JSONObject;

public class StateMessage {

    public static String loginSuccess(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",true);
        jsonObject.put("message","登录成功");
        System.out.println("登录成功！");
        return jsonObject.toJSONString();
    }

    public static String loginFailUserNameError(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",false);
        jsonObject.put("message","用户名不存在");
        System.out.println("用户名不存在！");
        return jsonObject.toJSONString();
    }
    public static String loginFailPasswordError(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",false);
        jsonObject.put("message","密码错误");
        System.out.println("密码错误！");
        return jsonObject.toJSONString();
    }
    public static String registerSuccess(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",true);
        jsonObject.put("message","注册成功");
        System.out.println("注册成功！");
        return jsonObject.toJSONString();
    }

    public static String registerFailUserNameError(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",false);
        jsonObject.put("message","用户名已存在");
        System.out.println("用户名已存在！");
        return jsonObject.toJSONString();
    }


    public static String unKnowError(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state",false);
        jsonObject.put("message","发生未知错误");
        System.out.println("发生未知错误");
        return jsonObject.toJSONString();
    }

}
