package com.tt.simpledagger2demo;

/**
 * Created by tuozhaobing on 16-4-13.
 * Add Some Description There
 */
public class UserModel {
    private String userName = "123";
    private String userPass = "456";
    private String address;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserModel() {
    }

    public UserModel(String userName, String userPass, String address) {
        this.userName = userName;
        this.userPass = userPass;
        this.address = address;
    }
}
