package ru.levelp.hospital.daoimpl;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.levelp.hospital.model.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

class DoctorDaoImplTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private DoctorDaoImpl doctors;

    @BeforeEach
    void setUp() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        doctors = new DoctorDaoImpl(manager);
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
    void insert() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertNotNull(createdDoctor);

        assertEquals(createdDoctor, manager.find(Doctor.class, createdDoctor.getId()));
        assertEquals(createdDoctor, doctors.getDoctorByLogin(createdDoctor.getLogin()));
        assertNull(doctors.getDoctorById(55594));
    }


    @Test
    void getDoctorByLoginExisting() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertEquals(createdDoctor, doctors.getDoctorByLogin("iviv"));
    }

    @Test
    void getDoctorByLoginNotExisting() {
        assertNull(doctors.getDoctorByLogin("ivivv"));
    }

    @Test
    public void getDoctorByLoginAndPassword() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertEquals(createdDoctor, doctors.getDoctorByLoginAndPassword("iviv", "fdsfGSDG12"));
        assertNull(doctors.getDoctorByLoginAndPassword("login8", "pass3"));
    }

    @Test
    public void updatePassword() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        doctors.updatePassword(createdDoctor.getLogin(), "ghgKL12");
        assertEquals("ghgKL12", createdDoctor.getPassword());
    }


    @Test
    public void testSortedBy() {
        Doctor first = doctors.insert(new Doctor("aaa", "aaa", "aaa", "bbbbAA2", "aaa"));
        Doctor second = doctors.insert(new Doctor("bbb", "bbb", "bbb", "abbbVA2", "bbvxbx"));

        assertEquals(Arrays.asList(first,second), doctors.findAllSortedBy("login"));
        assertEquals(Arrays.asList(second, first), doctors.findAllSortedBy("password"));

        try {
            doctors.findAllSortedBy("---fd-d-afaf");
            fail("Sorting by non-existing column");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSortedByWrongColumn() {
        assertThrows(IllegalArgumentException.class,()-> doctors.findAllSortedBy("-- wrong column name"));
    }

    @Test
    public void testCount() {
        assertEquals(0, doctors.count());

        doctors.insert(new Doctor("aaa", "aaa", "aaa", "aaaAA2", "aaa"));
        assertEquals(1, doctors.count());
    }



    @Test
    void delete() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertNotNull(createdDoctor);

        doctors.delete(createdDoctor);
        assertNull(doctors.getDoctorByLogin(createdDoctor.getLogin()));
    }
}