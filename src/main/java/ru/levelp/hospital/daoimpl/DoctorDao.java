package ru.levelp.hospital.daoimpl;

import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.exception.ServerErrorCode;
import ru.levelp.hospital.exception.ServerException;
import ru.levelp.hospital.model.Doctor;
import java.util.List;


@Repository
@RepositoryRestResource(
        collectionResourceRel = "doctors",
        itemResourceRel = "doctor"
)
public interface DoctorDao extends JpaRepository<Doctor, Integer> {


    @SneakyThrows
    @Transactional
    default Doctor insert(Doctor doctor) {
            save(doctor);
        return doctor;
    }


    Doctor findByLogin(String login);

    Doctor findByLoginAndPassword(String login, String password);


    @Query("update Doctor d set d.password =: password where d.login=: login")
    Doctor updatePassword(@Param("login") String login, @Param("password") String password);

    @SneakyThrows
    default void delete(Doctor doctor) {
        deleteById(doctor.getId());
    }


    List<Doctor> findAll();


    default void loginDoctor(Doctor doctor) {
        Database.getDatabase().insert(doctor);
    }

    @SneakyThrows
    default void logOutDoctor(Doctor doctor) {
        if (!Database.getDatabase().containsDoctor(doctor.getLogin())) {
            throw new ServerException(ServerErrorCode.USER_DOESNT_EXIST);
        }
        Database.getDatabase().logOutDoctor(doctor);
    }

}
