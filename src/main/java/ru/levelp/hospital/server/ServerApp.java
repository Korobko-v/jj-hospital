package ru.levelp.hospital.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.dto.request.RegisterDoctorDtoRequest;
import ru.levelp.hospital.model.Doctor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServerApp {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        File databaseFile = new File("database.txt");

        if (!databaseFile.exists()) {
            databaseFile.createNewFile();
        }


        Server server = new Server();
        ObjectMapper mapper = new ObjectMapper();
        //server.startServer(databaseFile.getName());
        Doctor doctor = new Doctor("vvk", "avc", "vbc", "vbc", "bvc");
        RegisterDoctorDtoRequest dtoRequest = new RegisterDoctorDtoRequest("avk", "bvc", "vcc", "cbc", "qvc");
        //DoctorService.registerDoctor(new ObjectMapper().writeValueAsString(doctor));
        BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile.getName(), true));
        //writer.write(mapper.writeValueAsString(doctor));
        writer.write(mapper.writeValueAsString(dtoRequest));
        writer.close();
  //      new DoctorService().logOut(new ObjectMapper().writeValueAsString(doctor));
        System.out.println("Доктор: " + Database.getDatabase().getDoctors().get(doctor));
        //server.stopServer(databaseFile.getName());

    }
}
