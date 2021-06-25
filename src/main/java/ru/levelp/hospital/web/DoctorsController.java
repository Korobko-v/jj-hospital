package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("doctorSession")
public class DoctorsController {
    @Autowired
    private DoctorDao doctors;


    @GetMapping("/register")
    private String showRegisterForm(
            @ModelAttribute("form")
            RegistrationForm form) {
        return "register";
    }
    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("form")
                    @Valid
            RegistrationForm form,
            BindingResult result
    ) {
        if (!form.getPassword().equals(form.getPasswordConfirmation())) {
            result.addError(new FieldError("form", "passwordConfirmation",
                    "Password and confirmation should match"));
        }

        if (result.hasErrors()) {
            return "/register";
        }
        try {
            doctors.insert(new Doctor(form.getFirstName(), form.getLastName(),
                    form.getLogin(), form.getPassword(), form.getSpeciality()));
        }
        catch (Exception cause) {
            result.addError(new FieldError(
                    "form",
                    "login",
                    "Пользователь с таким логином уже существует"
            ));
        }

        if (result.hasErrors()) {
            return "register";
        }

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

        doctorSession.clear();
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String handleLogout(DoctorSession doctorSession) {
        doctorSession.clear();
        return "redirect:/";
    }

    @ModelAttribute("form")
    public RegistrationForm createDefault() {
        return new RegistrationForm();
    }

}
