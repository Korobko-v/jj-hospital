package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.daoimpl.PatientDao;
import ru.levelp.hospital.daoimpl.PatientsCustomSort;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("doctorSession")
public class DoctorsController {
    @Autowired
    private DoctorDao doctors;

    @Autowired
    private PatientsCustomSort sort;

    @Autowired
    private PatientDao patients;

    @Autowired
    private PasswordEncoder encoder;

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

        String encryptedPassword =encoder.encode(form.getPassword());

        try {
            doctors.insert(new Doctor(form.getFirstName(), form.getLastName(),
                    form.getLogin(), encryptedPassword, form.getSpeciality()));
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


//    @PostMapping("/login")
//    public String handleLoginForm(
//            @RequestParam String login,
//            @RequestParam String password,
//
//
//            DoctorSession doctorSession
//    ) {
//        Doctor doctor = doctors.findByLoginAndPassword(login, password);
//        if (doctor != null) {
//            doctorSession.setUserId(doctor.getId());
//            doctorSession.setLogin(doctor.getLogin());
//            return "redirect:/";
//        }
//
//        doctorSession.clear();
//        return "redirect:/login";
//    }

//    @GetMapping("/logout")
//    public String handleLogout(DoctorSession doctorSession) {
//        doctorSession.clear();
//        return "redirect:/";
//    }

    @GetMapping("/showPatients")
    public String showPatients(Model model) {

        List<Patient> patientsList = sort.findAllSortedBy("login");
        model.addAttribute("patientsList", patientsList);

        return "patients";
    }

    @ModelAttribute("form")
    public RegistrationForm createDefault() {
        return new RegistrationForm();
    }

}
