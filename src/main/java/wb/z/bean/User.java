package wb.z.bean;

public class User {

    private Long id;
    private String userName;
    private String userPassword;
    private String studyPassword;
    private String userMessage;
    private String userNickName;
    private String userPersonalizedSignature;
    private int userState;


    public User(){

    }

    public User(String userName,String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }


    public String getUserPersonalizedSignature() {
        return userPersonalizedSignature;
    }

    public void setUserPersonalizedSignature(String userPersonalizedSignature) {
        this.userPersonalizedSignature = userPersonalizedSignature;
    }

    public String getStudyPassword() {
        return studyPassword;
    }

    public void setStudyPassword(String studyPassword) {
        this.studyPassword = studyPassword;
    }


    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        //由于id在数据库自增，这里暂时用不到
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user_name='" + userName + '\'' +
                ", user_password='" + userPassword + '\'' +
                '}';
    }
}