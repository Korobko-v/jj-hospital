package ru.levelp.hospital.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.levelp.hospital.daoimpl.DoctorDaoImpl;
import ru.levelp.hospital.model.Doctor;

@Getter
@Setter
public class RegisterDoctorDtoResponse {
    private String token;

    @SneakyThrows
    public void getRegisteredDoctorsToken(Doctor doctor) {
        this.token = DoctorDaoImpl.getDoctorsToken(doctor);
    }
}
