package ru.levelp.hospital.daoimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.levelp.hospital.dao.DoctorDao;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;


@Repository
@NoArgsConstructor
public class DoctorDaoImpl implements DoctorDao {


    @Autowired
    static ObjectMapper mapper;

    @Autowired
    private EntityManager manager;

    @Override
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

    @Override
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




    @Override
    @SneakyThrows
    public void loadDatabase(String savedDataFileName) {
        BufferedReader bufferedFileReader = new BufferedReader(new FileReader(savedDataFileName));
        while (bufferedFileReader.ready()) {
            String s = bufferedFileReader.readLine();
            Database.setDatabase(mapper.readValue(s, Database.class));
        }
        bufferedFileReader.close();
    }

    @Override
    @SneakyThrows
    public void saveDatabase(String savedDataFileName) {
        BufferedWriter bufferedFileWriter = new BufferedWriter(new FileWriter(savedDataFileName));
        for (Doctor doctor : Database.getDatabase().getDoctors().values()) {
            Database.getDatabase().getDoctors().put("empty token", doctor);
        }
        bufferedFileWriter.write(mapper.writeValueAsString(Database.getDatabase()));
        bufferedFileWriter.close();
    }

    public static String getDoctorsToken(Doctor doctor) {
        for (Map.Entry<String, Doctor> entry : Database.getDatabase().getDoctors().entrySet()) {
            String key = entry.getKey();
            Doctor value = entry.getValue();
            if (value.getLogin().equals(doctor.getLogin())) {
                return key;
            }
        }
        return null;
    }

}
