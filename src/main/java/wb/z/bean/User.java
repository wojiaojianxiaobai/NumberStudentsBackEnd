package wb.z.bean;

public class User {

    private Long id;
    private String userName;
    private String userPassword;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    private String userNickName;

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