package ru.levelp.hospital.daoimpl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class DoctorDao {

    @Autowired
    private EntityManager manager;


    @SneakyThrows
    public Doctor insert(Doctor doctor) {
        manager.getTransaction().begin();

        try {
            manager.persist(doctor);
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        return doctor;
    }


    public Doctor getDoctorById(int id) {
        return manager.find(Doctor.class, id);
    }


    public Doctor getDoctorByLogin(String login) {
        try {
            return manager.createQuery("select d from Doctor d where d.login =:login_to_search", Doctor.class)
                    .setParameter("login_to_search", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public Doctor getDoctorByLoginAndPassword(String login, String password) {
        try {
            return manager.createQuery("select d from Doctor d where d.login =:login_to_search " +
                    "and d.password =:pass", Doctor.class)
                    .setParameter("pass", password)
                    .setParameter("login_to_search", login)
                    .getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        }
    }

    public Doctor updatePassword(String login, String newPass) {

        try {
//           String hql = "update Doctor d set d.password=:password where d.login =:login";
//            Query query = manager.createQuery(hql).setParameter("password", newPass)
//                    .setParameter("login", login);
//            query.executeUpdate();
//            return getDoctorByLogin(login);

            Doctor toUpdate = manager.createQuery("select d from Doctor d where d.login =:login", Doctor.class)
                    .setParameter("login", login)
                    .getSingleResult();
            delete(toUpdate);
            toUpdate.setPassword(newPass);
            insert(toUpdate);
            return toUpdate;
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<Doctor> findAllSortedBy(String columnName) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Doctor> query = builder.createQuery(Doctor.class);
        Root<Doctor> root = query.from(Doctor.class);

        query.orderBy(builder.asc(root.get(columnName)));

        return manager.createQuery(query).getResultList();

    }

    public int count() {
        return manager.createQuery("select count (d) from Doctor d", Number.class)
                .getSingleResult().intValue();
    }


    @SneakyThrows
    public void delete(Doctor doctor) {
        manager.getTransaction().begin();

        try {
            manager.remove(doctor);
            manager.getTransaction().commit();
        }
        catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }

    public List<Doctor> findRandomList() {
        return manager.createQuery(
                "from Doctor",
                Doctor.class
        ).getResultList();
    }






    public void loginDoctor(Doctor doctor) {
        Database.getDatabase().insert(doctor);
    }

    @SneakyThrows
    public void logOutDoctor(Doctor doctor) {
        if (!Database.getDatabase().containsDoctor(doctor.getLogin())) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        Database.getDatabase().logOutDoctor(doctor);
    }






}
