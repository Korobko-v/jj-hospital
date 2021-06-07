package ru.levelp.hospital.daoimpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class PatientDaoImplTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private PatientDaoImpl patients;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        patients = new PatientDaoImpl(manager);
    }

    @AfterEach
    void tearDown() {
        if (manager != null) {
            manager.close();
        }

        if (factory != null) {
            factory.close();
        }
    }

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