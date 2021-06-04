package ru.levelp.hospital.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.levelp.hospital.daoimpl.DoctorDaoImpl;
import ru.levelp.hospital.database.Database;
import ru.levelp.hospital.model.Doctor;
import ru.levelp.hospital.model.Patient;
import ru.levelp.hospital.service.DoctorService;

import java.io.*;

public class Server {
//    public static List<Doctor> doctors = new ArrayList<>();
//    public static List<Patient> patients = new ArrayList<>();
    public ObjectMapper mapper = new ObjectMapper();
    public DoctorService service = new DoctorService();
    DoctorDaoImpl doctorDao = new DoctorDaoImpl();

    public static boolean containsLogin(String login) {
        for (Doctor doctor : Database.getDatabase().getDoctors().values()) {
            try {
                if (doctor.getLogin().equals(login)) {
                    return true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean containsPatientsLogin(String login) {
        for (Doctor doctor : Database.getDatabase().getDoctors().values()) {
            for (Patient patient : doctor.getPatients()) {
                try {
                    if (patient.getLogin().equals(login)) {
                        return true;
                    }
                } catch (NullPointerException e) {
                    return false;
                }
            }
        }
        return false;
    }



    public static Patient getPatientByLogin(String login) {
        for (Doctor doctor : Database.getDatabase().getDoctors().values()) {
            for (Patient patient : doctor.getPatients()) {
                if (patient.getLogin().equals(login)) {
                    return patient;
                }
            }
        }
        return null;
    }

//    public static Doctor getDoctorByToken(String token) {
//        for (Doctor doctor : doctors) {
//            if (doctor.getToken().equals(token)) {
//                return doctor;
//            }
//        }
//        return null;
//    }

    public void startServer(String savedDataFileName) {
        doctorDao.loadDatabase(savedDataFileName);
    }

    public void stopServer(String savedDataFileName) {
        doctorDao.saveDatabase(savedDataFileName);
    }

//    @SneakyThrows
//    public void loadPatients() {
//        BufferedReader bufferedFileReader = new BufferedReader(new FileReader("patients.txt"));
//        while (bufferedFileReader.ready()) {
//            String[] arr = bufferedFileReader.readLine().split("\\|");
//            if (arr.length >= 5) {
//                Patient currentPatient = new Patient(arr[0], arr[1],
//                        arr[2], arr[3], getDoctorByToken(arr[6]), arr[4]);
//                currentPatient.setDoctor(getDoctorByToken(arr[6]));
//                patients.add(currentPatient);
//
//                for (Doctor doctor: doctors) {
//                    if (arr[6].equals(doctor.getToken())) {
//                        doctor.patients.add(currentPatient);
//                    }
//                }
//            }
//            for (Doctor doctor: doctors) {
//                for (Patient patient: doctor.patients) {
//                    patients.add(patient);
//                }
//            }
//        }


//    @SneakyThrows
//    public static void savePatient() {
//        BufferedWriter bufferedFileWriter = new BufferedWriter(new FileWriter("patients.txt", true));
//        Patient currentPatient  = patients.get(patients.size()-1);
//            bufferedFileWriter.write(String.join("|", currentPatient.getFirstName(),
//                    currentPatient.getLastName(), currentPatient.getLogin(), currentPatient.getPassword(),
//                    currentPatient.getDiagnosis(), currentDoctor.getFirstName() + " " + currentDoctor.getLastName(),
//                    currentDoctor.getToken() +"\n"));
//
//        bufferedFileWriter.close();
//    }
//
//    @SneakyThrows
//    public static void updatePatients() {
//        BufferedWriter bufferedFileWriter = new BufferedWriter(new FileWriter("patients.txt"));
//        for (Patient patient : patients) {
//            bufferedFileWriter.write(String.join("|", patient.getFirstName(),
//                    patient.getLastName(), patient.getLogin(), patient.getPassword(),
//                    patient.getDiagnosis(), patient.getDoctor().getFirstName() + " " + patient.getDoctor().getLastName(),
//                    patient.getDoctor().getToken() + "\n"));
//        }
//        bufferedFileWriter.close();
//    }

//    @SneakyThrows
//    public static void loadProcedures() {
//        BufferedReader proceduresReader = new BufferedReader(new FileReader("procedures.txt"));
//        while (proceduresReader.ready()) {
//            String s = proceduresReader.readLine();
//            String[] patientAndProcedure = s.split("\\|");
//            if (patientAndProcedure.length > 1) {
//                String login = patientAndProcedure[0];
//                Patient patient = getPatientByLogin(login);
//
//                String[] procedureAndDays = patientAndProcedure[1].split("\\-");
//                if (procedureAndDays.length > 1) {
//                    String procedure = procedureAndDays[0];
//                    String[] days = procedureAndDays[1].split(",");
//
//                    TreeSet<Day> daySet = new TreeSet<>((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
//                    for (String sDay : days) {
//                        for (Day day : Day.values()) {
//                            if (day.getDay().equals(sDay)) {
//                                daySet.add(day);
//                                break;
//                            }
//                        }
//                    }
//                    patient.getProcedures().put(procedure, daySet);
//                }
//            }
//        }
//        proceduresReader.close();
//    }
//
//    @SneakyThrows
//    public void loadMedicines() {
//        BufferedReader medicinesReader = new BufferedReader(new FileReader("medicines.txt"));
//        while (medicinesReader.ready()) {
//            String s = medicinesReader.readLine();
//            String[] patientAndMedicines = s.split("\\|");
//            String login = patientAndMedicines[0];
//            Patient patient = getPatientByLogin(login);
//            String[] meds = new String[patientAndMedicines.length - 1];
//            System.arraycopy(patientAndMedicines, 1, meds, 0, patientAndMedicines.length - 1);
//            for (String med : meds) {
//                String[] medsAndSeq = med.split("-");
//
//                if (medsAndSeq.length > 1) {
//                    String key = medsAndSeq[0];
//                    Integer value = Integer.parseInt(medsAndSeq[1]);
//                    patient.getMedicines().put(key,value);
//                }
//            }
//        }
//    }
}
