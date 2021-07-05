package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.daoimpl.DepartmentDao;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.daoimpl.DoctorsCustomSort;
import ru.levelp.hospital.model.Department;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

@Controller
@RequestMapping(path = "/")
@SessionAttributes("doctorSession")
public class MainPageController {
    @Autowired
    private DoctorDao doctors;

    @Autowired
    private DepartmentDao departments;

    @Autowired
    private DoctorsCustomSort sort;

    @GetMapping
    public String index(Model model) {
        List<Doctor> randomDoctors = doctors.findAll();

        model.addAttribute("randomDoctors", randomDoctors);
        return "index";
    }


    @ModelAttribute("doctorSession")
    public DoctorSession createDoctorSession() {
        return new DoctorSession();
    }

    @GetMapping("/showDoctors")
    public String showDoctors(Model model) {
        List<Doctor> doctorsList = sort.findAllSortedBy("login");
        model.addAttribute("doctorsList", doctorsList);

        return "doctors";
    }

    @GetMapping("/api/departments/all")
    public String showDepartments(Model model) {
        List<Department> departmentsList = departments.findAll();
        model.addAttribute("departmentsList", departmentsList);
        return "departments";
    }

}