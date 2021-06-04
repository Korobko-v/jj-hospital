import org.junit.Test;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestUsers {

    @Test
    public void testCreateDoctorAndPatient() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        EntityManager entityManager = factory.createEntityManager();

        Doctor doctor = new Doctor("Ryan", "Gosling", "rg999", "1234", "dentist");
        Patient patient = new Patient("Alexander", "Nevsky", "mister_universe", "4321", "absolutely healthy");
        entityManager.getTransaction().begin();
        entityManager.persist(doctor);
        entityManager.persist(patient);
        entityManager.getTransaction().commit();

        entityManager.close();
        factory.close();
    }

}
