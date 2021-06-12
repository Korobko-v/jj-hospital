package ru.levelp.hospital.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import ru.levelp.hospital.model.Doctor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Getter
public class Database {
    private static Database database;
    private HashMap<String, Doctor> doctors = new HashMap<>(); //для присвоения токена при входе(и удаления (присвоения null) при выходе) и поиска по токену
    public static ObjectMapper mapper;
    public Database() {
    }

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    @SneakyThrows
    public void insert(Doctor doctor) {
        String token = UUID.randomUUID().toString();
        getDoctors().put(token, doctor);
    }

    public void logOutDoctor(Doctor doctor) {
        getDoctors().put(null, doctor);
    }

    public static void setDatabase(Database newDatabase) {
        database = newDatabase;
    }

    public void setDoctors(HashMap<String, Doctor> newDoctors) {
            doctors = newDoctors;
    }


    public boolean containsDoctor(String login) {
        for (Doctor doctor : doctors.values()) {
            if (doctor.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public void loadDatabase(String savedDataFileName) {
        BufferedReader bufferedFileReader = new BufferedReader(new FileReader(savedDataFileName));
        while (bufferedFileReader.ready()) {
            String s = bufferedFileReader.readLine();
            Database.setDatabase(mapper.readValue(s, Database.class));
        }
        bufferedFileReader.close();
    }

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
}
