package fun;

import java.sql.*;

public class DepositTransaction {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public DepositTransaction(String prn, String name, double depositBal, double totalBal, String currentDate, String details){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);

            // name= "Nitish Kumar"
            // This stringBuilder store Nitish, not include space or Kumar like this
            StringBuilder firstName = new StringBuilder(); // calculating the name before space
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                int ascii = ch;
                if (!(ascii == 32))
                    firstName.append(ch);
                else break;
            }

            //last five digit of prn number
            StringBuilder lastFiveDigitPrn = new StringBuilder();
            for (int i = 7; i < 12; i++) {
                lastFiveDigitPrn.append(prn.charAt(i));
            }
            //Table Name =name and last five digit of prn number
            String tableName = firstName.toString() + lastFiveDigitPrn;//Final Table name Create Update passbook

            String query= "INSERT INTO " + tableName +" (date_time, details, credit, balance) VALUES( ?, ?, ?, ?)";
            PreparedStatement pstmt= connection.prepareStatement(query);
            pstmt.setString(1, currentDate);
            pstmt.setString(2, details);
            pstmt.setDouble(3, depositBal);
            pstmt.setDouble(4, totalBal);

            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
