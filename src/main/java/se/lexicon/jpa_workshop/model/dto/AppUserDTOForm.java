package se.lexicon.jpa_workshop.model.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

public class AppUserDTOForm implements Serializable {

    private static final String EMAIL_REGEX = "^[0-9a-zA-Z]+([0-9a-zA-Z]*[-._+])*[0-9a-zA-Z]+@[0-9a-zA-Z]+([-.][0-9a-zA-Z]+)*([0-9a-zA-Z]*[.])[a-zA-Z]{2,6}$";

    @NotBlank(message = "This field is required")
    @Size(min = 3, max = 20, message = "Your username is too short or long")
    private String username;
    @NotBlank(message = "This field is required")
    @Pattern(regexp = "^(?=(.*[a-zA-Z].*){2,})(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$" ,message = "Your password is too weak")
    private String password;
    @NotBlank(message = "This field is required")
    @Email(regexp = EMAIL_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE, message = "Your email is invalid")
    private String email;
    @NotBlank(message = "This field is required")
    private String name;
    @NotNull(message = "This field is required")
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
