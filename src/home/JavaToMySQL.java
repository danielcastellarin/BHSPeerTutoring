package home;

import java.sql.*;

public class JavaToMySQL {

    private static final String url = "jdbc:mysql://localhost:3306/testdb";
    private static final String user = "devman";
    private static final String pass = "PeerTutoringRocks45";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static String query;

    public JavaToMySQL(String q){
        query = q;

    }

    public void doQuery(){
        try {
            con = DriverManager.getConnection(url, user, pass);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

}
