package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.levelp.hospital.dao.DepartmentDao;
import ru.levelp.hospital.dao.DoctorDao;
import ru.levelp.hospital.dao.DoctorsCustomSort;
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
    public String index(Model model,
    @RequestParam(required = false, defaultValue = "10") int count,
                        Authentication authentication
    ) {
        List<Doctor> randomDoctors = doctors.findAll();

        boolean loggedIn = authentication != null && authentication.isAuthenticated();

        String userName = "";
        boolean isAdmin;

        if (loggedIn) {
            userName = authentication.getName();
            isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        model.addAttribute("login", userName);
        model.addAttribute("loggedIn", loggedIn);
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