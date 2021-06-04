package ru.levelp.hospital.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.levelp.hospital.daoimpl.DoctorDaoImpl;
import ru.levelp.hospital.dto.request.RegisterDoctorDtoRequest;
import ru.levelp.hospital.dto.response.RegisterDoctorDtoResponse;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;


public class DoctorService extends UserService {
    static ObjectMapper objectMapper = new ObjectMapper();
    DoctorDaoImpl doctorDao = new DoctorDaoImpl();

    @SneakyThrows
    public String registerDoctor(String requestJsonString) {

        RegisterDoctorDtoRequest registerDoctorDtoRequest = objectMapper.readValue(requestJsonString, RegisterDoctorDtoRequest.class);
        Doctor doctor = registerDoctorDtoRequest.validate();

        doctorDao.insert(doctor);

        RegisterDoctorDtoResponse response = new RegisterDoctorDtoResponse();
        response.getRegisteredDoctorsToken(doctor);

        return response.getToken();
    }




    @SneakyThrows
    @Override
    public String logIn(String requestJsonString) {
        Doctor doctor = objectMapper.readValue(requestJsonString, Doctor.class);
        if (DoctorDaoImpl.getDoctorByLogin(doctor.getLogin()) == null) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        doctorDao.loginDoctor(doctor);
        return DoctorDaoImpl.getDoctorsToken(doctor);
    }

    @SneakyThrows
    @Override
    public void logOut(String requestJsonString) {
        Doctor doctor = objectMapper.readValue(requestJsonString, Doctor.class);
        if (DoctorDaoImpl.getDoctorByLogin(doctor.getLogin()) == null) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        doctorDao.logOutDoctor(doctor);
    }
}
