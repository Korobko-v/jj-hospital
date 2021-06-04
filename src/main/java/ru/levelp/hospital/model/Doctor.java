package ru.levelp.hospital.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;


@Getter
@Setter
public class Doctor extends User {
        public String speciality;
        public List<Patient> patients;


        public Doctor() {}

        public Doctor(String firstName, String lastName, String login, String password, String speciality) {
            super(firstName, lastName, login, password);
            this.speciality = speciality;
            this.patients = new ArrayList<>();
            //Database.getDatabase().getDoctors().put(this, UUID.randomUUID().toString());
        }

        @SneakyThrows
        public void viewMyPatients() {
            try {
                System.out.println("Мои пациенты: ");
                patients.stream()
                        .forEach(patient -> System.out.println(patient.getLogin() + "|"
                                + patient.getFirstName() + " " + patient.getLastName()
                                + "|" + patient.getDiagnosis()));
            } catch (Exception e) {
                System.out.println("Нет пациентов");
            }

        }

    public void viewMyPatients(String diagnosis) {
        patients.
                stream()
                .filter(patient -> patient.getDiagnosis().equals(diagnosis))
                .forEach(patient -> System.out.println(patient.getLogin() + "|"
                        + patient.getFirstName() + " " + patient.getLastName()));
    }

    public void viewMyPatientsByPrescription(String prescription) {
        System.out.println("Мои пациенты с назначением: " + prescription);
        patients.
                stream()
                .filter(patient -> patient.getProcedures().containsKey(prescription)|| patient.getMedicines().containsKey(prescription))
                .forEach(patient -> System.out.println(patient.getLogin() + "|"
                        + patient.getFirstName() + " " + patient.getLastName()));
    }

        @SneakyThrows
        public void addPrescription() {
        }

        @SneakyThrows
        public void registerPatient(String requestJsonString) {
        }

        public void removeDoctor() {
        }

        @SneakyThrows
        public void cancelPrescription() {
        }

    @Override
    public String toString() {
        return "Doctor{" +
                "speciality='" + speciality + '\'' +
                ", patients=" + patients +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "} " + super.toString();
    }
}
