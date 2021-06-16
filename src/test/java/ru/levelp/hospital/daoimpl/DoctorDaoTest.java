package ru.levelp.hospital.daoimpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import ru.levelp.hospital.TestConfig;
import ru.levelp.hospital.model.Doctor;

import javax.persistence.EntityManager;

import java.util.Arrays;

import static org.junit.Assert.*;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DoctorDaoTest {


    @Autowired
    public EntityManager manager;
    @Autowired
    public DoctorDao doctors;


    @Test
    public void insert() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertNotNull(createdDoctor);

        assertEquals(createdDoctor, manager.find(Doctor.class, createdDoctor.getId()));
        assertEquals(createdDoctor, doctors.getDoctorByLogin(createdDoctor.getLogin()));
        assertNull(doctors.getDoctorById(55594));
    }


    @Test
    public void getDoctorByLoginExisting() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertEquals(createdDoctor, doctors.getDoctorByLogin("iviv"));
    }

    @Test
    public void getDoctorByLoginNotExisting() {
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
    public void delete() {
        Doctor createdDoctor = doctors.insert(new Doctor("Ivan" , "Ivanov", "iviv", "fdsfGSDG12", "Surgeon"));
        assertNotNull(createdDoctor);

        doctors.delete(createdDoctor);
        assertNull(doctors.getDoctorByLogin(createdDoctor.getLogin()));
    }
}