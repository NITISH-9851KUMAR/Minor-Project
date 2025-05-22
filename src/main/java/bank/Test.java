package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Test {
    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String user = "root";
    final static String password = "Nitish@04";
    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(url, user, password);

            String query= "INSERT INTO prn_details VALUES(?, ?)";
            PreparedStatement pstm= con.prepareStatement(query);
            String prn= "220205131100";
            pstm.setInt(1, 100);
            pstm.setString(2, prn);

            pstm.executeUpdate();



            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
