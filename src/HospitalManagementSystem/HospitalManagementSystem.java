package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;

public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospital_management_system";

    private static final String username= "root";

    private static final String password = "1234";

    public static void main(String[] args){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, sc);
            Doctor doctor = new Doctor(connection);

            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice :");
                int choice = sc.nextInt();

                switch(choice){

                    case 1:
                        //Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // view patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // view doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // book appointment
                        bookApppointment(patient, doctor, connection, sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thank You For Using Our Services.");
                        return;
                    default:
                       System.out.println("Enter a valid Choice!");
                       break;
                }
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookApppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc ){
        System.out.print("Enter Patient Id: ");
        int patientId= sc.nextInt();

        System.out.print("Enter Doctor Id: ");
        int doctorId = sc.nextInt();

        System.out.print("Enter Appointment date(YYYY-MM-DD):");
        String appointmentDate= sc.next();

        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvaialability(doctorId, appointmentDate, connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?,?,?)";

                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int affectedRows = preparedStatement.executeUpdate();

                    if(affectedRows>0){
                        System.out.println("Appointment Booked!");
                    }
                    else{
                        System.out.println("Failed to book appointment!");
                    }

                }
                catch(SQLException e){
                    e.printStackTrace();
                }

            }
            else{
                System.out.println("Doctor Not Available on This date.");
            }
        } else{
            System.out.println("Either  doctor or patient  doesn't exist");

        }

    }

    public static  boolean checkDoctorAvaialability(int doctorId, String appointmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date= ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }
                else{
                    return false;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        return false;
        }
    }

