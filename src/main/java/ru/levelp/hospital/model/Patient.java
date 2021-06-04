package ru.levelp.hospital.model;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import ru.levelp.hospital.database.Database;
import java.util.*;

@Getter
@Setter
public class Patient extends User {
    public String diagnosis;
    public Doctor doctor;
    private String password;
    private Map<String, Integer> medicines;
    private Map<String, TreeSet<Day>> procedures;


    public Patient() {

    }


    public Patient(String firstName, String lastName, String login, String password, Doctor doctor, String diagnosis) {
        super(firstName, lastName, login,password);
        this.diagnosis = diagnosis;
        this.password = password;
        setDoctor(doctor);
        this.medicines = new HashMap<>();
        this.procedures = new HashMap<>();
    }


    @SneakyThrows
    public void logIn(String requestJsonString) {
    }



    public void removePatient() {
        for (Doctor doctor : Database.getDatabase().getDoctors().values()) {
            if (doctor.getPatients().contains(this)) {
                doctor.getPatients().remove(this);
            }
        }
    }

    public void viewMyPrescriptions() {
        System.out.println("Мои лекарства:");
        for (Map.Entry<String, Integer> entry : getMedicines().entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
        System.out.println("==============================");
        System.out.println("Мои процедуры:");
        for (Map.Entry<String, TreeSet<Day>> entry : getProcedures().entrySet()) {
            System.out.println(entry.getKey() + ": ");
            for (Day day: entry.getValue()) {
                System.out.println(day.getDay());
            }
        }
    }
}
