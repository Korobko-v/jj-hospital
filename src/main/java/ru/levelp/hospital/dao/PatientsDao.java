package ru.levelp.hospital.dao;

import ru.levelp.hospital.model.Patient;

import java.util.List;

public interface PatientsDao {
    Patient create(Patient patient);
    Patient findById(int id);
    Patient findByLogin(String login);
    Patient findByLoginAndPassword(String login, String password);
    List<Patient> findByDoctorsLogin(String doctorsLogin);
    List<Patient> findAllSortedBy(String columnName);
    int count();
    void delete(Patient patient);
}
