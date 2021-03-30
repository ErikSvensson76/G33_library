package se.lexicon.jpa_workshop.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class AppUserDTO implements Serializable {
    private int appUserId;
    private LocalDate regDate;
    private DetailsDTO userDetails;

    public AppUserDTO(int appUserId, LocalDate regDate, DetailsDTO userDetails) {
        this.appUserId = appUserId;
        this.regDate = regDate;
        this.userDetails = userDetails;
    }

    public AppUserDTO() {
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public DetailsDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(DetailsDTO userDetails) {
        this.userDetails = userDetails;
    }
}
