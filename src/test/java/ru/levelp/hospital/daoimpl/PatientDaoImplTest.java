package ru.levelp.hospital.daoimpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.levelp.hospital.TestConfig;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PatientDaoImplTest {
    @Autowired
    private EntityManagerFactory factory;

    @Autowired
    private EntityManager manager;

    @Autowired
    private PatientDaoImpl patients;

    @Test
    void create() {
        Patient createdPatient = patients.create(new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd"));
        assertNotNull(createdPatient);

        assertEquals(createdPatient, manager.find(Patient.class, createdPatient.getId()));
        assertEquals(createdPatient, patients.findById(createdPatient.getId()));
        assertNull(patients.findById(55594));
    }


    @Test
    void findByLoginExisting() {
        Patient createdPatient = patients.create(new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd"));
        assertEquals(createdPatient, patients.findByLogin(createdPatient.getLogin()));
    }

    @Test
    void findByLoginNotExisting() {
        assertNull(patients.findByLogin("this login doesn't exist"));
    }

    @Test
    void findByLoginAndPassword() {
        Patient createdPatient = patients.create(new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd"));
        assertEquals(createdPatient, patients.findByLoginAndPassword(createdPatient.getLogin(), createdPatient.getPassword()));
        assertNull(patients.findByLoginAndPassword("kkkkkkkkk", "kkkkkkkkkkkk"));
    }

    @Test
    void findByDoctorsLogin() {
        Patient patient = new Patient("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "dgsgsd");
        Doctor doctor = new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon");

        manager.getTransaction().begin();
        manager.persist(doctor);
        manager.persist(patient);
        patient.setDoctor(doctor);
        manager.getTransaction().commit();

        assertEquals(Collections.singletonList(patient), patients.findByDoctorsLogin(doctor.getLogin()));
        assertEquals(Collections.emptyList(), patients.findByDoctorsLogin("unknown"));
    }

    @Test
    void findAllSortedBy() {
        Patient first = patients.create(new Patient("aaa", "aaa", "aaa", "bbbbAA2", "aaa"));
        Patient second = patients.create(new Patient("bbb", "bbb", "bbb", "abbbVA2", "bbvxbx"));

        assertEquals(Arrays.asList(first,second), patients.findAllSortedBy("login"));
        assertEquals(Arrays.asList(second, first), patients.findAllSortedBy("password"));

        try {
            patients.findAllSortedBy("---fd-d-afaf");
            fail("Sorting by non-existing column");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSortedByWrongColumn() {
        assertThrows(IllegalArgumentException.class,()-> patients.findAllSortedBy("-- wrong column name"));
    }

    @Test
    void testCount() {
        assertEquals(0, patients.count());

        patients.create(new Patient("aaa", "aaa", "aaa", "aaaAA2", "aaa"));
        assertEquals(1, patients.count());
    }

    @Test
    void testDelete() {
        Patient patient = patients.create(new Patient("aaa", "aaa", "aaa", "aaaAA2", "aaa"));
        assertNotNull(patients.findByLogin(patient.getLogin()));
        patients.delete(patient);
        assertNull(patients.findByLogin(patient.getLogin()));
    }
}