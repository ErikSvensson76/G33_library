package se.lexicon.jpa_workshop.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class DetailsDTO implements Serializable {
    private int detailId;
    private String name;
    private LocalDate birthDate;

    public DetailsDTO(int detailId, String name, LocalDate birthDate) {
        this.detailId = detailId;
        this.name = name;
        this.birthDate = birthDate;
    }

    public DetailsDTO() {
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
