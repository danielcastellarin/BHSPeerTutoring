package home;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JavaToMySQL {

    private static final String url = "jdbc:mysql://localhost:3306/testdb?allowMultiQueries=true";
    private static final String user = "devman";
    private static final String pass = "PeerTutoringRocks45";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static String query;
    private static ArrayList<String> matches;

    public JavaToMySQL(String q){
        query = q;
        matches = new ArrayList<>();
    }

    public void doQuery(){
        try {
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void doUpdate(){
        try {
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> readTutorProfiles(){
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        try {
            while(rs.next()){
                ArrayList<String> item = new ArrayList<>();
                item.add(rs.getInt(1) + "@bps121.org");
                item.add(rs.getString(2));
                item.add(rs.getString(3));
                item.add(rs.getString(4));
                list.add(item);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> readTutorNames(){
        ArrayList<String> list = new ArrayList<>();
        String name;
        try {
            while(rs.next()){
                name = rs.getString(1) + " " + rs.getString(2);
                list.add(name);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return list;
    }

    public ArrayList<TimeSlot> readTimeSlots(){
        ArrayList<TimeSlot> list = new ArrayList<>();
        try {
            while(rs.next()){
                TimeSlot timeSlot = new TimeSlot(
                        rs.getString(1), rs.getInt(2), rs.getInt(3));
                list.add(timeSlot);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return list;
    }

    public int readLasid() {
        try {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return 0;
    }

    public ArrayList getMatches() throws SQLException{
        while(rs.next()){
            matches.add(rs.getString(1));
        }
        return matches;
    }

    public void printMatches(){
        for(int i = 0; i < matches.size(); i++){
            System.out.println(matches.get(i));
        }
    }

}
