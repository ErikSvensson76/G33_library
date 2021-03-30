package se.lexicon.jpa_workshop.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailId;
    @Column(unique = true)
    private String email;
    private String name;
    private LocalDate birthDate;

    public Details(int detailId, String email, String name, LocalDate birthDate) {
        this.detailId = detailId;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Details() {
    }

    public int getDetailId() {
        return detailId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Details details = (Details) o;
        return detailId == details.detailId && Objects.equals(email, details.email) && Objects.equals(name, details.name) && Objects.equals(birthDate, details.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detailId, email, name, birthDate);
    }

    @Override
    public String toString() {
        return "Details{" +
                "detailId=" + detailId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
