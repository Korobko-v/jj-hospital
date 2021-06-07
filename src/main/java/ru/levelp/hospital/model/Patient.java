package ru.levelp.hospital.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "Patients")
public class Patient {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, length = 100)
    public String firstName;

    @Column(nullable = false, length = 100)
    public String lastName;

    @Column(unique = true, nullable = false, length = 100)
    private String login;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 1000)
    public String diagnosis;

    @ManyToOne
    public Doctor doctor;

    public Patient() {
    }


    public Patient(String firstName, String lastName, String login, String password, String diagnosis) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.diagnosis = diagnosis;
        this.password = password;
    }

}
