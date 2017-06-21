package com.app.easyspeak.model;

import java.io.Serializable;

/**
 * Created by user1 on 6/15/2017.
 */

public class User implements Serializable {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String secondName;
    private String dateofBirth;
    private String mobileNumber;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public User(String userName) {
        this.userName = userName;
    }

    public User() {
        super();
    }

    public User(String id, String userName, String password, String email, String firstName, String secondName, String dateofBirth, String mobileNumber) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateofBirth = dateofBirth;
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(id).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", secondName='").append(secondName).append('\'');
        sb.append(", dateofBirth='").append(dateofBirth).append('\'');
        sb.append(", mobileNumber='").append(mobileNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
