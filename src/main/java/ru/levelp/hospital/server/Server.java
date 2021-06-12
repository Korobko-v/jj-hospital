package ru.levelp.hospital.server;


import ru.levelp.hospital.database.Database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Server {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
    private EntityManager manager = factory.createEntityManager();

    public void startServer(String savedDataFileName) {
        Database.getDatabase().loadDatabase(savedDataFileName);
    }

    public void stopServer(String savedDataFileName) {

        Database.getDatabase().saveDatabase(savedDataFileName);
    }

}
