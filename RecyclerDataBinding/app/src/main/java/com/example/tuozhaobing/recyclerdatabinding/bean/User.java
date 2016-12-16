package com.example.tuozhaobing.recyclerdatabinding.bean;

/**
 * Created by tuozhaobing on 16/12/16.
 */
public class User {
    public static final String TAG = "User";
    private int userImage;
    private String userName;
    private String userLocation;

    public User(int userImage, String userName, String userLocation) {
        this.userImage = userImage;
        this.userName = userName;
        this.userLocation = userLocation;
    }

    public User() {

    }

    public int getUserImage() {

        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }
}
