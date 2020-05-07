/** OOP Feb 2019
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here we use one DAO per database table.
 *
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */
/**
 * Loads TollEvents from TollEvents.csv directly into the DB
 *
 * DB Format:
 * REGISTRATION String
 * IMAGEID long
 * TIMESTAMP BIGINT
 *
 */
package oop_ca6_aislingsmith.BusinessObjects;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import oop_ca6_aislingsmith.DAOs.MySqlTollEventDao;
import oop_ca6_aislingsmith.DAOs.TollEventDaoInterface;
import oop_ca6_aislingsmith.DTOs.TollEvent;
import oop_ca6_aislingsmith.Exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // connect to datbase table ......., use try catch....  String url = "jdbc:mariadb://localhost/";
        String url = "jdbc:mariadb://localhost/tollevent";
        String dbName = "test?useFractionalSeconds=true";  // NB: note the fractional seconds flag
        String driver = "org.mariadb.jdbc.Driver";
        String userName = "root";
        String password = null;  //  or password = null; if there is no password on your database
        try {
            Class.forName(driver);            // load JDBC driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement stmt = conn.createStatement();) {
            System.out.println("Connected to the database, INSERTing into Events table...");

            try (Scanner sc = new Scanner(new File("TollEvents.csv"))) {

                sc.useDelimiter(";\n");

                while (sc.hasNext()) {
                    String registration = sc.next();

                    long imageId = sc.nextLong();

                    String strTimestamp = sc.next();  // read in as String , then conver to long
                    Instant instant = Instant.parse(strTimestamp);
                    long timestamp = instant.toEpochMilli();

                    // Insert a row in the database
                    String sql = "INSERT INTO EVENTS (REGISTRATION, IMAGEID, TIMESTAMP) VALUES( ?, ?, ? );";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                    preparedStatement.setString(1, registration);
                    preparedStatement.setLong(2, imageId);
                    preparedStatement.setLong(3, timestamp);  // I hope this will write to BIGINT field (it didnt:()

                    preparedStatement.executeUpdate();

                }

            } catch (IOException e) {
                System.out.println("Exception thrown : " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        

//            UNIT TESTING DAO'S
//            try {
//            List<TollEvent> events = ITollEventDao.findAllTollEvents();
//
//            if (events.isEmpty()) {
//                System.out.println("There are no Toll Events");
//            }
//
//            for (TollEvent event : events) {
//                System.out.println(event.toString());
//            }
//
//            // test dao - registration
//           TollEvent event =  ITollEventDao.findTollEventByRegistration("191LH1111");
//            if (event != null) {
//                System.out.println("Registration found: " + event);
//            } else {
//                System.out.println("Registration not found");
//            }
//            
//            //pass in date time as a string. Dao will convert to miliseconds(timestamp) 
//            // test dao - Specific date
//             events =  ITollEventDao.findTollEventBySpecificDate("2020-02-14T10:15:30.000Z");
//            if (!events.isEmpty()) {
//                System.out.println("Specific date found: " + events);
//            } else {
//                System.out.println("Specific date not found");
//            }
//            
//            // test dao - Start and finish date
////             event =  ITollEventDao.findTollEventByStartAndFinishDate(2020-02-17T13:20:01.123Z, 2020-02-17T23:25:10.654Z);
////            if (!events.isEmpty()) {
////                System.out.println("Start and End date found: " + events);
////            } else {
////                System.out.println("Start and End date not found" + events);
////            }
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
    }
}
