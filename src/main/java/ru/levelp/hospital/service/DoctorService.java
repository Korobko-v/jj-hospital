package ru.levelp.hospital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.dto.request.RegisterDoctorDtoRequest;
import ru.levelp.hospital.dto.response.RegisterDoctorDtoResponse;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;

@Service
@Getter
public class DoctorService extends UserService {

    @Autowired
    static ObjectMapper objectMapper;

    @Autowired
    private DoctorDao doctors;

    @SneakyThrows
    public String registerDoctor(String requestJsonString) {

        RegisterDoctorDtoRequest registerDoctorDtoRequest = objectMapper.readValue(requestJsonString, RegisterDoctorDtoRequest.class);
        Doctor doctor = registerDoctorDtoRequest.validate();

        doctors.insert(doctor);
        RegisterDoctorDtoResponse response = new RegisterDoctorDtoResponse();
        response.getRegisteredDoctorsToken(doctor);

        return response.getToken();
    }


    @SneakyThrows
    @Override
    public String logIn(String requestJsonString) {
        Doctor doctor = objectMapper.readValue(requestJsonString, Doctor.class);
        if (doctors.findByLogin(doctor.getLogin()) == null) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        doctors.loginDoctor(doctor);
        return Database.getDoctorsToken(doctor);
    }

    @SneakyThrows
    @Override
    public void logOut(String requestJsonString) {
        Doctor doctor = objectMapper.readValue(requestJsonString, Doctor.class);
        if (doctors.findByLogin(doctor.getLogin()) == null) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        doctors.logOutDoctor(doctor);
    }
}
