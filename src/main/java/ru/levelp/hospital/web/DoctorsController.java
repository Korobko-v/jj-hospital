package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

@Controller
@SessionAttributes("doctorSession")
public class DoctorsController {
    @Autowired
    private DoctorDao doctors;


    @GetMapping("/register")
    private String showRegisterForm() {
        return "register";
    }
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String speciality
    ) {
        doctors.insert(new Doctor(login, password, firstName, lastName, speciality));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLoginForm(
            @RequestParam String login,
            @RequestParam String password,

            DoctorSession doctorSession
    ) {
        Doctor doctor = doctors.getDoctorByLoginAndPassword(login, password);
        if (doctor != null) {
            doctorSession.setUserId(doctor.getId());
            doctorSession.setLogin(doctor.getLogin());
            return "redirect:/";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String handleLogout(DoctorSession doctorSession) {
        doctorSession.clear();
        return "redirect:/";
    }

}
