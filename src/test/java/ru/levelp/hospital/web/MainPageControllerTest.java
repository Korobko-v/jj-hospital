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
import ru.levelp.hospital.dao.DoctorDao;
import ru.levelp.hospital.model.Doctor;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfig.class)
@ComponentScan(basePackages = "ru.levelp.hospital.web")
class MainPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DoctorDao doctors;


    @Test
    void testIndex() throws Exception {
        Doctor doctor1 = new Doctor("1", "1", "1", "1", "1");
        Doctor doctor2 = new Doctor("2", "2", "2", "2", "2");
        Doctor doctor3 = new Doctor("3", "3", "3", "3", "3");

        List<Doctor> expectedDoctors = List.of(doctor1, doctor3, doctor2);

//        when(doctors.findRandomList()).thenReturn(expectedDoctors);
//        when(doctors.findAllSortedBy("login")).thenReturn(List.of(doctor1, doctor2, doctor3));
//        when(doctors.count()).thenReturn(3L);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("doctorSession", new DoctorSession()))
                .andExpect(model().attribute("randomList", expectedDoctors));

    }

    @Test
    public void testIndexLoggedInUser() throws Exception {
        DoctorSession session = new DoctorSession();
        session.setUserId(1);
        session.setLogin("login1");

        mvc.perform(
                get("/").sessionAttr("doctorSession", session)
        )
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("doctorSession", session));
    }

    @Test
    void createDoctorSession() {
    }

    @Test
    void showDoctors() {
    }
}