package Server;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import static java.lang.Math.toIntExact;
/**
 * DataManager that reads JSON and store into MYSQL DB
 */
public class DataManager {

    private static final Logger log = LogManager.getLogger();
    // JDBC driver name and databse URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://frank.czb2g0pebcye.ap-southeast-2.rds.amazonaws.com:3306/medsec?useSSL=false";

    // Databse credentials
    private static final String USER = "frank";
    private static final String PASSWORD = "^xt*E8kE78vrZJPW";

    private static DataManager instance = null;
    private static Connection connection;
    private static Statement stm;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void ConnectionDB() {
        try {
            Class.forName(JDBC_DRIVER);
            log.info("coonecting mysql database");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processPatient(JSONObject patient) {
        try {
            int patientId = Integer.parseInt((String) patient.get("Id"));
            // search the patient ID firstly to check if it exists in DB
            stm = connection.createStatement();
            String query = "SELECT Id FROM Patient WHERE id = " + patientId;
            ResultSet resultSet = stm.executeQuery(query);
            if (resultSet.next()) {
                do {
                    log.info("find the patient" + patientId + ". Begin to update!");
                    updatePatient(patient);
                } while (resultSet.next());
            } else {
                log.info("Cannot find it, it should be a new one. Begin to insert!");
                try {
                    insertPatient(patient);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Update existing patient
    Assume they only change their address or email
    */
    public void updatePatient(JSONObject patient) {
        int patientId = Integer.parseInt((String) patient.get("Id"));
        String email = (String) patient.get("EmailAddress");
        String street = (String) patient.get("AddressLine1");
        String suburb = (String) patient.get("Suburb");
        String state = (String) patient.get("State");
        String query = "UPDATE Patient SET email='" + email + "', street='"
                + street +"', suburb='" + suburb + "', state='" + state + "'" +
                "WHERE id= " + patientId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Insert new patient
     */
    public void insertPatient(JSONObject patient) throws ParseException{
        int patientId = Integer.parseInt((String) patient.get("Id"));
        String surname = (String) patient.get("Surname");
        String firstName = (String) patient.get("FirstName");
        //String middleName = (String) patient.get("MiddleName");
        String dob = (String) patient.get("DOB");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dataStr = format.parse(dob);
        java.sql.Date dateDB = new java.sql.Date(dataStr.getTime());
        String email = (String) patient.get("EmailAddress");
        String street = (String) patient.get("AddressLine1");
        String suburb = (String) patient.get("Suburb");
        String state = (String) patient.get("State");
        String query = "INSERT INTO Patient (id, surname, firstname, dob, email, street, suburb, state) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,patientId);
            preparedStatement.setString(2,surname);
            preparedStatement.setString(3,firstName);
            preparedStatement.setDate(4,dateDB);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,street);
            preparedStatement.setString(7,suburb);
            preparedStatement.setString(8,state);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {

        return stm;
    }

    public void processAppointment(JSONObject appointment) {
        try {
            int apptId = Integer.parseInt((String) appointment.get("Id"));
            // search the patient ID firstly to check if it exists in DB
            stm = connection.createStatement();
            String query = "SELECT Id FROM Appointment WHERE id = " + apptId;
            ResultSet resultSet = stm.executeQuery(query);
            if (resultSet.next()) {
                do {
                    log.info("find the appointment" + apptId + ". Begin to update!");
                    try {
                        updateAppt(appointment);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (resultSet.next());
            } else {
                log.info("Cannot find it, it should be a new one. Begin to insert!");
                try {
                    insertAppt(appointment);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAppt(JSONObject appointment) throws ParseException{
        int apptId = Integer.parseInt((String) appointment.get("Id"));
        String title = (String) appointment.get("Name");
        //String dateChange = (String) appointment.get("LastUpdated");
        String date = (String) appointment.get("StartDate");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dataStr;
        dataStr = format.parse(date);
        java.sql.Date dateAppt = new java.sql.Date(dataStr.getTime());
        String note = (String) appointment.get("Note");
        int status = Integer.parseInt((String) appointment.get("Status"));
        int duration = Integer.parseInt((String) appointment.get("ApptDuration"));
        Boolean isCancelled = Boolean.valueOf((String) appointment.get("Cancelled")) ;
        int patientId = Integer.parseInt((String) appointment.get("PT_Id_Fk"));
        String query = "UPDATE Appointment SET title='" + title + "', date='" + dateAppt + "', note='" + note +
                "', status='" + status + "', duration='" + duration +
                "', is_cancelled='" + isCancelled + "', pid='" + patientId +  "'" +
                "WHERE id= " + apptId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAppt(JSONObject appointment) throws ParseException{
        int apptId = Integer.parseInt((String) appointment.get("Id"));
        String title = (String) appointment.get("Name");
        String dataCreate = (String) appointment.get("CreationDate");
        //String dateChange = (String) appointment.get("LastUpdated");
        String date = (String) appointment.get("StartDate");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dataStr;
        dataStr = format.parse(dataCreate);
        java.sql.Date dateCreated = new java.sql.Date(dataStr.getTime());
        dataStr = format.parse(date);
        java.sql.Date dateAppt = new java.sql.Date(dataStr.getTime());
        String detail = (String) appointment.get("Comment");
        String note = (String) appointment.get("Note");
        int status = Integer.parseInt((String) appointment.get("Status"));
        int duration = Integer.parseInt((String) appointment.get("ApptDuration"));
        Boolean isCancelled = Boolean.valueOf((String) appointment.get("Cancelled")) ;
        int patientId = Integer.parseInt((String) appointment.get("PT_Id_Fk"));
        String query = "INSERT INTO Appointment (id, title, date_create, date, detail, note, " +
                "status, duration, is_cancelled, pid) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,apptId);
            preparedStatement.setString(2,title);
            preparedStatement.setDate(3,dateCreated);
            preparedStatement.setDate(4,dateAppt);
            preparedStatement.setString(5,detail);
            preparedStatement.setString(6,note);
            preparedStatement.setInt(7,status);
            preparedStatement.setInt(8,duration);
            preparedStatement.setBoolean(9,isCancelled);
            preparedStatement.setInt(10,patientId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
