package se.lexicon.jpa_workshop.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class AppUserDTOForm implements Serializable {
    private String username;
    private String password;
    private String email;
    private String name;
    private LocalDate birthDate;

    public AppUserDTOForm(String username, String password, String email, String name, LocalDate birthDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
    }

    public AppUserDTOForm() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
