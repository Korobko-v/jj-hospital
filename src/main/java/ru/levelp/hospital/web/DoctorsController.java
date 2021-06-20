package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

public class DoctorsController {
    @Autowired
    private DoctorDao doctors;

    @GetMapping("/showDoctors")
    public String showDoctors(Model model) {
        List<Doctor> foundDoctors = doctors.findAllSortedBy("login");
        model.addAttribute("foundDoctors", foundDoctors);
            return "redirect:/";
    }

    @GetMapping("/register")
    private String showRegisterForm() {
        return "register";
    }
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String speciality
    ) {
        doctors.insert(new Doctor(login, password, firstName, lastName, speciality));
        return "redirect:/";
    }

}
