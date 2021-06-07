package ru.levelp.hospital.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.levelp.hospital.daoimpl.DoctorDaoImpl;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;
import ru.levelp.hospital.service.DoctorService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Server {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
    private EntityManager manager = factory.createEntityManager();
    DoctorDaoImpl doctorDao = new DoctorDaoImpl(manager);

    public void startServer(String savedDataFileName) {
        doctorDao.loadDatabase(savedDataFileName);
    }

    public void stopServer(String savedDataFileName) {
        doctorDao.saveDatabase(savedDataFileName);
    }

}
