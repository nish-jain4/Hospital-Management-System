package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;


    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.sc= sc;
    }

    public void addPatient(){
        System.out.print("enter Patient Name: ");
        String name = sc.next();
        System.out.print("Enter Patient Age:");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender:");
        String Gender= sc.next();


        try{
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows>0){
                System.out.println("Patient Added Successfully!!");

            }
            else{
                System.out.println("Failed to add Patient!!");
            }


        }
         catch(SqlException e){
            e.printStackTrace();
         }

    }

    public void viewPatient(){
        String query = "SELECT * from patients";
        try{
            preparedStatement preparedStatement = connection.preparedStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");

            while(resultSet.next()){
                int id= resultSet.getInt("id");
                String name= resultSet.getString("name");
                int age= resultSet.getString("gender");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-20s|%-10s|%-12s|");
                System.out.println("+------------+--------------------+----------+------------+");

            }
        }
        catch(SQLExcpetion e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query= "SELECT * FROM patients WhERE id=?";

        try{
            PreparedStatement preparedStatement = conenction.p
        }
    }

}
