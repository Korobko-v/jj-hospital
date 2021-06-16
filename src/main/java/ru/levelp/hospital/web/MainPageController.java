package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

@Controller
@RequestMapping(path = "/")
public class MainPageController {
    @Autowired
    private DoctorDao doctors;

    @GetMapping
    public String index(Model model, @RequestParam(required = false, defaultValue = "10") int count) {
        List<Doctor> randomDoctors = doctors.findRandomList(count);

        model.addAttribute("randomDoctors", randomDoctors);

        return "index";
    }
}