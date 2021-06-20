package ru.levelp.hospital.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.levelp.hospital.TestConfig;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@ComponentScan(basePackages = "ru.levelp.hospital.web")
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DoctorDao doctors;

    @Test
    public void testLoginForm() throws Exception {
        Doctor doctor = new Doctor("1", "1", "1", "123", "1");
        doctor.setId(1);

        when(doctors.getDoctorByLoginAndPassword("1", "123"))
                .thenReturn(doctor);

        mvc.perform(post("/login")
                .param("login", "1")
                .param("password", "123")
        ).andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute(
                        "doctorSession",
                        new DoctorSession(1, "1")
                ));
    }

    @Test
    public void testLoginFormInvalidUser() throws Exception {
        mvc.perform(post("/login")
                .param("login", "doctor")
                .param("password", "123")
        ).andExpect(status().is3xxRedirection())
                .andExpect(request()
                        .sessionAttribute("doctorSession", new DoctorSession()));
    }

}
