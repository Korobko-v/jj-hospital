package ru.levelp.hospital.web;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationForm {

    private String firstName;

    private String lastName;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "[a-zA-Z0-9_\\.-]*", message = "Login should consist of letters, digits, underscore, dot or hyphen")
    private String login;

    @Size(min = 4, max = 10)
    private String password;

    private String speciality;

    private String passwordConfirmation;
}
