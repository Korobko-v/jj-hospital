package ru.levelp.hospital.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDoctorDtoRequest {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String speciality;
    public List<Patient> patients;


    public RegisterDoctorDtoRequest(String firstName, String lastName, String login, String password, String speciality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.speciality = speciality;
        this.patients = new ArrayList<>();
    }



    @SneakyThrows
    public Doctor validate() {
        ObjectMapper mapper = new ObjectMapper();
        if (this.getLogin() == null || this.getFirstName() == null || this.getLastName() == null
                || this.getSpeciality() == null || this.getPassword() == null || this.getPatients() == null) {
            throw new ServerException(ServerErrorCode.NULL_FIELD);
        }
        if (this.getLogin().isEmpty() || this.getFirstName().isEmpty() || this.getLastName().isEmpty()
                || this.getSpeciality().isEmpty() || this.getPassword().isEmpty()) {
            throw new ServerException(ServerErrorCode.EMPTY_FIELD);
        }
        if (this.getPassword().length() < 4) {
            throw new ServerException(ServerErrorCode.SHORT_PASSWORD);
        }
        if (!this.getPassword().matches(".*[a-z]+.*") ||!this.getPassword().matches(".*[A-Z\\W\\d]+.*")) {
            throw new ServerException(ServerErrorCode.WRONG_PASSWORD);
        }

        return mapper.readValue(mapper.writeValueAsString(this), Doctor.class);
    }
}
