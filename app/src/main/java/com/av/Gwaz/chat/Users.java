package com.av.Gwaz.chat;

public class Users {
    String profilepic,mail,userName,userId,lastMessage,status;


    public Users() {
        // Required empty constructor for Firebase
    }



    public Users(String userId, String userName, String maill, String profilepic, String status) {
        this.userId = userId;
        this.userName = userName;
        this.mail = maill;
        this.profilepic = profilepic;
        this.status = status;
    }



    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
