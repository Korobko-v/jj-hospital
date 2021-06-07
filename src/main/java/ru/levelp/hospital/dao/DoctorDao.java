package ru.levelp.hospital.dao;

import ru.levelp.hospital.model.Doctor;

public interface DoctorDao {
    Doctor insert(Doctor doctor);
    Doctor updatePassword(String login, String newPass);
    void delete(Doctor doctor);
    void loadDatabase(String savedDataFileName);
    void saveDatabase(String savedDataFileName);
}
