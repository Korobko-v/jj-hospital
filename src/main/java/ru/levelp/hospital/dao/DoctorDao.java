package ru.levelp.hospital.dao;

import ru.levelp.hospital.model.Doctor;

public interface DoctorDao {
    void insert(Doctor doctor);
    void update(Doctor doctor, String password);
    void delete(Doctor doctor);
    void loadDatabase(String savedDataFileName);
    void saveDatabase(String savedDataFileName);
}
