package ru.levelp.hospital.daoimpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.levelp.hospital.TestConfig;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PatientDaoTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private PatientDao patients;

    @Autowired
    private DoctorDao doctors;

    @Autowired
    private PatientsCustomSort sort;

    @Test
    public void create() {
        Patient createdPatient = patients.create(new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd"));
        assertNotNull(createdPatient);

        assertEquals(createdPatient, manager.find(Patient.class, createdPatient.getId()));
        assertEquals(createdPatient, patients.findById(createdPatient.getId()));
        assertNull(patients.findById(55594));
    }



    @Test
    public void findByLoginExisting() {
        Patient createdPatient = patients.create(new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd"));
        assertEquals(createdPatient, patients.findByLogin(createdPatient.getLogin()));
    }

    @Test
    public void findByLoginNotExisting() {
        assertNull(patients.findByLogin("this login doesn't exist"));
    }

    @Test
    public void findByLoginAndPassword() {
        Patient createdPatient = patients.create(new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd"));
        assertEquals(createdPatient, patients.findByLoginAndPassword(createdPatient.getLogin(), createdPatient.getPassword()));
        assertNull(patients.findByLoginAndPassword("kkkkkkkkk", "kkkkkkkkkkkk"));
    }

    @Test
    public void findByDoctorsLogin() {
        Patient patient = new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd");
        Doctor doctor = new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon");
        patient.setDoctor(doctor);
//        Mockito.when(patients.findByDoctorsLogin(doctor.getLogin())).thenReturn(Collections.singletonList(patient));

        doctors.insert(doctor);


        assertEquals(Collections.singletonList(patient), patients.findByDoctorsLogin(doctor.getLogin()));
        assertEquals(Collections.emptyList(), patients.findByDoctorsLogin("unknown"));
    }

    @Test
    public void findAllSortedBy() {
        Patient first = patients.create(new Patient("aaa", "aaa", "aaa", "bbbbAA2", "aaa"));
        Patient second = patients.create(new Patient("bbb", "bbb", "bbb", "abbbVA2", "bbvxbx"));

        assertEquals(Arrays.asList(first,second), sort.findAllSortedBy("login"));
        assertEquals(Arrays.asList(second, first), sort.findAllSortedBy("password"));

        try {
            sort.findAllSortedBy("---fd-d-afaf");
            fail("Sorting by non-existing column");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSortedByWrongColumn() {
        assertThrows(IllegalArgumentException.class,()-> sort.findAllSortedBy("-- wrong column name"));
    }


    @Test
    public void testDelete() {
        Patient patient = patients.create(new Patient("aaa", "aaa", "aaa", "aaaAA2", "aaa"));
        assertNotNull(patients.findByLogin(patient.getLogin()));
        patients.delete(patient);
        assertNull(patients.findByLogin(patient.getLogin()));
    }
}