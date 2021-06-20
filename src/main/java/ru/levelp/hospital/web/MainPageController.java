package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

@Controller
@RequestMapping(path = "/")
@SessionAttributes("doctorSession")
public class MainPageController {
    @Autowired
    private DoctorDao doctors;

    @GetMapping
    public String index(Model model) {
        List<Doctor> randomDoctors = doctors.findRandomList();

        model.addAttribute("randomDoctors", randomDoctors);

        return "index";
    }


    @ModelAttribute("doctorSession")
    public DoctorSession createDoctorSession() {
        return new DoctorSession();
    }

    @GetMapping("/showDoctors")
    public String showDoctors(Model model) {
        List<Doctor> doctorsList = doctors.findAllSortedBy("login");

        model.addAttribute("doctorsList", doctorsList);

        return "doctors";
    }
}