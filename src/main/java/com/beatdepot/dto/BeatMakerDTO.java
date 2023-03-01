package com.beatdepot.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

public class BeatMakerDTO extends RepresentationModel<BeatMakerDTO>  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String userName;
    private String description;
    private List<BeatDTO> beats;

    public BeatMakerDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<BeatDTO> getBeats() {
        return beats;
    }

    public void setBeats(List<BeatDTO> beats) {
        this.beats = beats;
    }
}

