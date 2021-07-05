package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.daoimpl.DepartmentDao;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Department;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

@RestController
public class DepartmentsController {

    @Autowired
    private DepartmentDao departments;

    @Autowired
    private DoctorDao doctors;

    @GetMapping("/api/departments/findByName")
    public Department findByName (@RequestParam String departmentName) {
        return departments.findByName(departmentName);
    }

    @GetMapping("/api/departments/findAll")
    public List<Department> findAll () {
        return departments.findAll();
    }

    @PostMapping("/api/departments/create")
    public Department create(@RequestParam String name) {
        return departments.create(name);
    }

    @PostMapping("/api/department/{id}/add-doctor/{login}")
    public Department addDoctor(@PathVariable int id, @PathVariable String login) {
        Department department = departments.findById(id).orElseThrow();
        Doctor doctor = doctors.findByLogin(login);
        department.getDoctors().add(doctor);
        doctor.setDepartment(department);
        return department;
    }
}
