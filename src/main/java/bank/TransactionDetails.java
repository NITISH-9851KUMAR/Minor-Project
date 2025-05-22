package bank;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/TransactionDetailsServlets")
public class TransactionDetails extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{

        response.setContentType("text/html");
        PrintWriter out= response.getWriter();

        String acNumber= request.getParameter("accNumber").trim();

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection= DriverManager.getConnection(url, userName, password);
            String tranTableName= "";

            String query= "SELECT * FROM accountDetails WHERE  acc_number= ?";
            PreparedStatement pstm= connection.prepareStatement(query);
            pstm.setString(1, acNumber);
            ResultSet rSet= pstm.executeQuery();

            while (rSet.next()) { // Getting the name and prn number
                String name = rSet.getString("name");
                String prn = rSet.getString("prn_number");

                // Extract first part of name (before space)
                StringBuilder firstName = new StringBuilder();
                for (int i = 0; i < name.length(); i++) {
                    char ch = name.charAt(i);
                    if (ch != ' ') {
                        firstName.append(ch);
                    } else {
                        break;
                    }
                }

                // Extract last five digits of PRN (safe indexing check)
                StringBuilder lastFiveDigitPrn = new StringBuilder();
                if (prn.length() >= 12) {
                    for (int i = 7; i < 12; i++) {
                        lastFiveDigitPrn.append(prn.charAt(i));
                    }
                }
                tranTableName= (firstName.toString()+lastFiveDigitPrn).trim();
            }

            // Getting the transaction details
            query= "SELECT * FROM "+tranTableName;
            pstm= connection.prepareStatement(query);
            rSet= pstm.executeQuery();
            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; margin: 50px auto; max-width: 700px; padding: 20px; border: 1px solid #ccc; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>");

            out.println("<h2 style='text-align: center;'>NITIYA BANK</h2>");
            out.println("<h4 style='color: green; text-align: center;'>Transaction History</h4>");

            out.println("<table style='width:100%; border-collapse: collapse; text-align: center;'>");
            out.println("<thead>");
            out.println("<tr style='background-color: #007bff; color: white;'>");
            out.println("<th style='padding: 8px; border: 1px solid #ddd;'>Date & Time</th>");
            out.println("<th style='padding: 8px; border: 1px solid #ddd;'>Details</th>");
            out.println("<th style='padding: 8px; border: 1px solid #ddd;'>Debit</th>");
            out.println("<th style='padding: 8px; border: 1px solid #ddd;'>Credit</th>");
            out.println("<th style='padding: 8px; border: 1px solid #ddd;'>Balance</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            while (rSet.next()) {
                String date = rSet.getString("date_time");
                String details = rSet.getString("details");
                double debit = rSet.getDouble("debit");
                double credit = rSet.getDouble("credit");
                double bal = rSet.getDouble("balance");

                out.println("<tr>");
                out.println("<td style='padding: 8px; border: 1px solid #ddd;'>" + date + "</td>");
                out.println("<td style='padding: 8px; border: 1px solid #ddd;'>" + details + "</td>");
                out.println("<td style='padding: 8px; border: 1px solid #ddd;'>" + debit + "</td>");
                out.println("<td style='padding: 8px; border: 1px solid #ddd;'>" + credit + "</td>");
                out.println("<td style='padding: 8px; border: 1px solid #ddd;'>" + bal + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");


            pstm.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

