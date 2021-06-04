package ru.levelp.hospital.database;

import lombok.Getter;
import lombok.SneakyThrows;
import ru.levelp.hospital.model.Doctor;

import java.util.HashMap;
import java.util.UUID;


@Getter
public class Database {
    private static Database database;
    private HashMap<String, Doctor> doctors = new HashMap<>(); //для присвоения токена при входе(и удаления (присвоения null) при выходе) и поиска по токену

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


    //я так понимаю, замечание по наличию этого метода касалось ситуации, когда в мапу в базе данных я бы помещал в качестве ключа логин
    //и пока не удалось понять, как проверить базу на отсутствие пользователя без данного метода с putIfAbsent, если в мапе пары "токен-доктор"
    public boolean containsDoctor(String login) {
        for (Doctor doctor : doctors.values()) {
            if (doctor.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
