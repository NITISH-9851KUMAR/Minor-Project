package fun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTransaction {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public CreateTransaction(String prn, String name, double bal, String date, String details) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();

//            create table of passbook Details
//            Table Name =name and last five digit of prn number

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

            String query;
            //Query for create table
            query = String.format("CREATE TABLE %s(" +
                    "date_time VARCHAR(20)," +
                    "details VARCHAR(30)," +
                    "debit FLOAT(10) DEFAULT 0.0," +
                    "credit FLOAT(10) DEFAULT 0.0," +
                    "balance FLOAT(10))", tableName);
            statement.executeUpdate(query);

            query = String.format("INSERT INTO %s(date_time,details,balance) VALUES('%s','%s',%f)", tableName, date, details, bal);
            statement.executeUpdate(query);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CreateTransaction("220205131004", "Nitish Kumar", 10, "19-05-25 10:11", "BANK");

    }
}
