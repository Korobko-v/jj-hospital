package ru.levelp.hospital.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.levelp.hospital.dao.PatientsDao;
import ru.levelp.hospital.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository()
public class PatientDaoImpl implements PatientsDao {
    @Autowired
    private EntityManager manager;

    @Override
    public Patient create(Patient patient) {
        manager.getTransaction().begin();

        try {
            manager.persist(patient);
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        return patient;
    }

    @Override
    public Patient findById(int id) {
        return manager.find(Patient.class, id);
    }

    @Override
    public Patient findByLogin(String login) {
        try {
            return manager.createQuery("select p from Patient p where p.login =:login_to_search", Patient.class)
                    .setParameter("login_to_search", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    @Override
    public Patient findByLoginAndPassword(String login, String password) {
        try {
            return manager.createQuery("select p from Patient p where p.login =:login_to_search " +
                    "and p.password =:pass", Patient.class)
                    .setParameter("pass", password)
                    .setParameter("login_to_search", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    @Override
    public List<Patient> findByDoctorsLogin(String doctorsLogin) {
        return manager.createQuery("select p from Patient p where p.doctor.login =:login", Patient.class)
                .setParameter("login", doctorsLogin)
                .getResultList();
    }

    @Override
    public List<Patient> findAllSortedBy(String columnName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Patient> query = builder.createQuery(Patient.class);
        Root<Patient> root = query.from(Patient.class);

        query.orderBy(builder.asc(root.get(columnName)));

        return manager.createQuery(query).getResultList();
    }

    @Override
    public int count() {
        return manager.createQuery("select count (p) from Patient p", Number.class)
                .getSingleResult().intValue();
    }

    @Override
    public void delete(Patient patient) {
        manager.getTransaction().begin();

        try {
            manager.remove(patient);
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
}
