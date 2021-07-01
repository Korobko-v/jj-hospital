package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.levelp.hospital.daoimpl.PatientDao;
import ru.levelp.hospital.model.Patient;

import java.util.List;

@RestController
public class PatientsController {

    @Autowired
    private PatientDao patients;

    @GetMapping("/api/patients/getByLogin")
    public Patient findByLogin(@RequestParam String login) {
        return patients.findByLogin(login);
    }

    @GetMapping("/api/patients/all")
    public List<Patient> findAll() {
        return patients.findAll();
    }

}
