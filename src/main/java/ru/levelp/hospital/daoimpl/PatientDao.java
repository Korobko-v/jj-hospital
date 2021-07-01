package ru.levelp.hospital.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.hospital.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public interface PatientDao extends JpaRepository<Patient, Integer> {

    @Transactional
    default Patient create(Patient patient) {
        save(patient);
        return patient;
    }

    Patient findById(int id);

    Patient findByLogin(String login);

    Patient findByLoginAndPassword(String login, String password);

    @Query("select p from Patient p where p.doctor.login =:login")
    List<Patient> findByDoctorsLogin(@Param("login") String doctorsLogin);



    default void delete(Patient patient) {
        deleteById(patient.getId());
    }
}
