package wb.z.bean;

import com.alibaba.fastjson.JSONObject;

public class MomentsItem {
    String mMomentId;
    String mMomentPicturePath;
    String mUserName;
    String mUserNickName;
    String mMomentTime;
    String mMomentTitle;
    String mMomentContent;

    public String getMomentId() {
        return mMomentId;
    }

    public void setMomentId(String mMomentId) {
        this.mMomentId = mMomentId;
    }

    public String getMomentPicturePath() {
        return mMomentPicturePath;
    }

    public void setMomentPicturePath(String mMomentPicturePath) {
        this.mMomentPicturePath = mMomentPicturePath;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserNickName() {
        return mUserNickName;
    }

    public void setUserNickName(String mUserNickName) {
        this.mUserNickName = mUserNickName;
    }

    public String getMomentTime() {
        return mMomentTime;
    }

    public void setMomentTime(String mMomentTime) {
        this.mMomentTime = mMomentTime;
    }

    public String getMomentTitle() {
        return mMomentTitle;
    }

    public void setMomentTitle(String mMomentTitle) {
        this.mMomentTitle = mMomentTitle;
    }

    public String getMomentContent() {
        return mMomentContent;
    }

    public void setMomentContent(String mMomentContent) {
        this.mMomentContent = mMomentContent;
    }

    @Override
    public String toString() {
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("mMomentId",mMomentId);
        jsonObject.put("mMomentPicturePath",mMomentPicturePath);
        jsonObject.put("mUserName",mUserName);
        jsonObject.put("mUserNickName",mUserNickName);
        jsonObject.put("mMomentTime",mMomentTime);
        jsonObject.put("mMomentTitle",mMomentTitle);
        jsonObject.put("mMomentContent",mMomentContent);

        return jsonObject.toString();
    }
}
