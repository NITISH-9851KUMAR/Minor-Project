package fun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountNumber {
    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public static String accNumber(String prn) {

        int count = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);

            String query = "SELECT COUNT(name) FROM accountDetails";
            PreparedStatement pstm = connection.prepareStatement(query);
            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                count = rSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // bank number is 357712364051+ number of customer +1

        String bankNumber1 = "357712364051";
        String bankNumber2 = "35771236405100";
        String bankNumber3 = "3577123640510";

        if (count == 0) return "357712364051001";
        else if(count==9) return bankNumber3 + (count + 1);
        else if (count > 0 && count < 10) return bankNumber2 + (count + 1);
        else if (count >= 10 && count < 100) return bankNumber3 + (count + 1);
        else return bankNumber1 + (count + 1);
    }

}
