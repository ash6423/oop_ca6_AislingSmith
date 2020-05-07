/** Feb 2019
 *
 * Data Access Object (DAO) for User table with MySQL-specific code
 * This 'concrete' class implements the 'UserDaoInterface'.
 *
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it
 * will be independent of the database specifics.
 *
 */
package oop_ca6_aislingsmith.DAOs;

import oop_ca6_aislingsmith.DTOs.TollEvent;
import oop_ca6_aislingsmith.Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MySqlTollEventDao extends MySqlDao implements TollEventDaoInterface {

    public List<TollEvent> findAllTollEvents() throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM TOLL_EVENTS";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) {

                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                long timestamp = rs.getLong("TIMESTAMP");

                TollEvent t = new TollEvent(registration, imageId, timestamp);
                events.add(t);
            }
        } catch (SQLException e) {
            throw new DaoException("findAllTollEvents() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("findAllTollEvents() " + e.getMessage());
            }
        }
        return events;     // may be empty
    }

    @Override
    public TollEvent findTollEventByRegistration(String reg) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TollEvent t = null;
        try {
            con = this.getConnection();

            String query = "SELECT * FROM TOLL_EVENTS WHERE REGISTRATION = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, reg);

            rs = ps.executeQuery();
            if (rs.next()) {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                long timestamp = rs.getLong("TIMESTAMP");

                t = new TollEvent(registration, imageId, timestamp);
            }
        } catch (SQLException e) {
            throw new DaoException("findTollEventByRegistration " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("findTollEventByRegistration " + e.getMessage());
            }
        }
        return t;     // u may be null 
    }

    @Override
    public List<TollEvent> findTollEventBySpecificDate(String dateTime) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TollEvent t = null;
       
        //code required to convert dateTime into timestamp

        Instant instant = Instant.parse(dateTime);
        long timestamp = instant.toEpochMilli();
        ArrayList<TollEvent> list = new ArrayList<>();
        try {
            con = this.getConnection();

            String query = "SELECT * FROM TOLL_EVENTS WHERE TIMESTAMP >= ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, timestamp);

            rs = ps.executeQuery();
            if (rs.next()) {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                timestamp = rs.getLong("TIMESTAMP");

                t = new TollEvent(registration, imageId, timestamp);
                list.add(t);
            }
        } catch (SQLException e) {
            throw new DaoException("findTollEventBySpecificDate() " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                throw new DaoException("findTollEventBySpecificDate() " + e.getMessage());
            }
        }
        return list;
    }
//
    //QUESTION 4 PART B, Tried to add a start and end date query. It is broken as I am unsure how i could pass in two different dates using the time stamp and retrieve data back from the database.
//     @Override
//    public List<TollEvent> findTollEventByStartAndEndDate(String dateTime1, String dateTime2) throws DaoException {
//        Connection con = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        TollEvent t = null;
//       
//
//        Instant instant = Instant.parse(dateTime1);
//        Instant instant = Instant.parse(dateTime2);
//        long timestamp = instant.toEpochMilli();
//        ArrayList<TollEvent> list = new ArrayList<>();
//        try {
//            con = this.getConnection();
//
//            String query = "SELECT * FROM TOLL_EVENTS WHERE TIMESTAMP = "2020-02-17T13:20:01.123Z" AND TIMESTAMP = " 2020-02-17T23:25:10.654Z"";
//            ps.setString(1, dateTime1);
//            ps.setString(2, dateTime2);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                String registration = rs.getString("REGISTRATION");
//                long imageId = rs.getLong("IMAGEID");
//                timestamp = rs.getLong("TIMESTAMP");
//
//                t = new TollEvent(registration, imageId, timestamp);
//                list.add(t);
//            }
//        } catch (SQLException e) {
//            throw new DaoException("findTollEventByStartAndEndDate() " + e.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//                if (con != null) {
//                    freeConnection(con);
//                }
//            } catch (SQLException e) {
//                throw new DaoException("findTollEventByStartAndEndDate() " + e.getMessage());
//            }
//        }
//        return list;
//    }
}
