package com.beatdepot.dto;

import java.io.Serializable;

public class BeatMakerInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String userName;
    private String description;

    public BeatMakerInputDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
