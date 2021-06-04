package ru.levelp.hospital.daoimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.levelp.hospital.dao.DoctorDao;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

import static ru.levelp.hospital.database.Database.getDatabase;

public class DoctorDaoImpl implements DoctorDao {
    static ObjectMapper mapper = new ObjectMapper();
    @Override
    @SneakyThrows
    public void insert(Doctor doctor) {
        if (Database.getDatabase().containsDoctor(doctor.getLogin())) {
            throw new ServerException(ServerErrorCode.USER_EXISTS);
        }
        Database.getDatabase().insert(doctor);
    }
    public void loginDoctor(Doctor doctor) {
        Database.getDatabase().insert(doctor);
    }

    @SneakyThrows
    public void logOutDoctor(Doctor doctor) {
        if (!Database.getDatabase().containsDoctor(doctor.getLogin())) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        Database.getDatabase().logOutDoctor(doctor);
    }

    @Override
    public void update(Doctor doctor, String password) {

        doctor.setPassword(password);
    }

    @Override
    @SneakyThrows
    public void delete(Doctor doctor) {
        if (!Database.getDatabase().containsDoctor(doctor.getLogin())) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        String token = getDoctorsToken(doctor);
        Database.getDatabase().getDoctors().remove(token);
    }

    @Override
    @SneakyThrows
    public void loadDatabase(String savedDataFileName) {
        BufferedReader bufferedFileReader = new BufferedReader(new FileReader(savedDataFileName));
        while (bufferedFileReader.ready()) {
            String s = bufferedFileReader.readLine();
            Database.setDatabase(mapper.readValue(s, Database.class));
        }
        bufferedFileReader.close();
    }

    @Override
    @SneakyThrows
    public void saveDatabase(String savedDataFileName) {
        BufferedWriter bufferedFileWriter = new BufferedWriter(new FileWriter(savedDataFileName));
        for (Doctor doctor : Database.getDatabase().getDoctors().values()) {
            Database.getDatabase().getDoctors().put("empty token", doctor);
        }
        bufferedFileWriter.write(mapper.writeValueAsString(Database.getDatabase()));
        bufferedFileWriter.close();
    }

    public static String getDoctorsToken(Doctor doctor) {
        for (Map.Entry<String, Doctor> entry : Database.getDatabase().getDoctors().entrySet()) {
            String key = entry.getKey();
            Doctor value = entry.getValue();
            if (value.getLogin().equals(doctor.getLogin())) {
                return key;
            }
        }
        return null;
    }
    public static Doctor getDoctorByLogin(String login) {
        for (Doctor doctor : getDatabase().getDoctors().values()) {
            if (doctor.getLogin().equals(login)) {
                return doctor;
            }
        }
        return null;
    }
}
