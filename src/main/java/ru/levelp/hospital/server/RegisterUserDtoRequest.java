package ru.levelp.hospital.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterUserDtoRequest {
    private String firstName;
    private String lastName;
    private String speciality;
    private String login;
    private String password;

}
