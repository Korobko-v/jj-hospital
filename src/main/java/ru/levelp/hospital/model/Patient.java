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

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    public String diagnosis;

    @ManyToOne()
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
