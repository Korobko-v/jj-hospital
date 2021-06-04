package ru.levelp.hospital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.levelp.hospital.model.User;

import java.util.UUID;

public class UserService {
    ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    String logIn(String requestJsonString) {
        User user = mapper.readValue(requestJsonString, User.class);
        return requestJsonString + "\\|" + UUID.randomUUID();
    }

    void logOut(String requestJsonString) {

    }
}
