package kr.or.tashow;

import java.io.Serializable;

public class User implements Serializable {
    private String userPhoneNum;
    private String userName;
    private String userPwd;

    public User() {
    }

    public User(String userPhoneNum, String userName, String userPwd) {
        this.userPhoneNum = userPhoneNum;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public String toString() {
        return "[" +
                "휴대폰번호(ID): " + userPhoneNum +
                " | 이름: " + userName +
                " | 비밀번호: " + userPwd +
                "]";
    }
}
