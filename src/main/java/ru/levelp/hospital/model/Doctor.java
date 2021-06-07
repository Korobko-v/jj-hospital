package ru.levelp.hospital.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.persistence.*;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "Doctors")
public class Doctor  {

        @Id
        @GeneratedValue
        @Column
        private int id;

        @Column(nullable = false, length = 100)
        public String firstName;

        @Column(nullable = false, length = 100)
        public String lastName;

        @Column(unique = true, nullable = false, length = 100)
        private String login;

        @Column(nullable = false, length = 100)
        private String password;

        @Column(nullable = false, length = 100)
        public String speciality;

        @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
        public List<Patient> patients;


        public Doctor() {}

    public Doctor(String firstName, String lastName, String login, String password, String speciality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.speciality = speciality;
        this.patients = new ArrayList<>();
    }



}
