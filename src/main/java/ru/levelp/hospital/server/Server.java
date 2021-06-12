package ru.levelp.hospital.server;

import ru.levelp.hospital.daoimpl.DoctorDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Server {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
    private EntityManager manager = factory.createEntityManager();
    DoctorDaoImpl doctorDao = new DoctorDaoImpl();

    public void startServer(String savedDataFileName) {
        doctorDao.loadDatabase(savedDataFileName);
    }

    public void stopServer(String savedDataFileName) {
        doctorDao.saveDatabase(savedDataFileName);
    }

}
