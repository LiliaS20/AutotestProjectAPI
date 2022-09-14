package tripDemo.model;

import java.sql.*;

public class test {
    static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String  USER = "postgres";
    static final String PASSWORD = "148192";
    static final String QUERY = "select * from trip where id = ?";
    static final String UPDATE_QUERY = "update trip set plane=? where id=?";

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //Statement stmt = conn.createStatement();
            PreparedStatement ptmt = conn.prepareStatement(UPDATE_QUERY);
            PreparedStatement qptmt = conn.prepareStatement(QUERY);
        ) {
            // Let us check if it returns a true Result Set or not.
          /*  boolean ret = stmt.execute(QUERY);
            System.out.println("Return value is : " + ret );*/

            ptmt.setString(1, "test14");
            ptmt.setInt(2, 15);
            qptmt.setInt(1, 15);

            int rows = ptmt.executeUpdate();
            System.out.println("Rows impacted : " + rows );

            // Let us select all the records and display them.
            ResultSet rs = qptmt.executeQuery();
           // ResultSet rs = stmt.executeQuery(QUERY);

            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", Компания: " + rs.getInt("company_id"));
                System.out.print(", Самолет: " + rs.getString("plane"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
