package ru.levelp.hospital.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;

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

        @Column
        @ColumnDefault(value = "false")
        private boolean isAdmin;

        @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
        public List<Patient> patients;

        @ManyToOne
        private Department department;

        public Doctor() {}

    public Doctor(String firstName, String lastName, String login, String password, String speciality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.speciality = speciality;
        this.patients = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", speciality='" + speciality + '\'' +
                ", patients=" + patients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id && firstName.equals(doctor.firstName) && lastName.equals(doctor.lastName) && login.equals(doctor.login) && password.equals(doctor.password) && speciality.equals(doctor.speciality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, login, password, speciality);
    }
}
