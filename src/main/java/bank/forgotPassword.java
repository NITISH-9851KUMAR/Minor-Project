package bank;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/password-recover")
public class forgotPassword extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String prn = request.getParameter("prn-num");
        String mobNum = request.getParameter("mob-num");
        String accNum = request.getParameter("acc-num");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, userName, password);
            String query = "SELECT * FROM accountDetails WHERE prn_number= ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, prn);
            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) {
                String mobNumber = rSet.getString("mob_number");
                String prnNumber = rSet.getString("prn_number");
                String acNumber = rSet.getString("acc_number");

                String pass = rSet.getString("pass");

                if ((accNum.equals(acNumber)) && (mobNum.equals(mobNumber))) {

                    out.println("<body style='background-color: #2d2d30;'>");

                    out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                    out.println("<h2 style='color:#333;'>Nitiya Bank</h2>");
                    out.println("<h4 style='color:green;'>Password Recovered</h4>");

                    out.println("<p><strong>Account Number:</strong> " + accNum + "</p>");
                    out.println("<p><strong>Account Password:</strong> " + pass + "</p>");
                    out.println("<p><strong>PRN Number:</strong> " + prn + "</p>");
                    out.println("<p><strong>Mobile Number:</strong> " + mobNumber + "</p>");

                    out.println("<div style='margin-top:20px;'>");
                    out.println("<a href='index.jsp' style='padding:10px 20px; background-color:red; color:white; text-decoration:none; border-radius:25px; font-weight:bold;'>Log In to Your Account</a>");
                    out.println("</div>");

                    out.println("</div>");
                    out.println("</body>");

                } else {
                    out.println("<body style='background-color: #2d2d30;'>");
                    out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin:50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                    out.println("<h2 style='color:#333;'>Nitiya Bank</h2>");
                    out.println("<h4 style='color:red;'>Invalid Details</h4>");
                    out.println("<p style='color:#555;'>Please check your credentials and try again.</p>");

                    out.println("<div style='margin-top:20px;'>");
//                    out.println("<a href='forgot-password.jsp' style='padding:10px 20px; background-color:green; color:white; text-decoration:none; border-radius:25px; font-weight:bold;'>Recover Password</a>");
                    out.println("</div>");

                    out.println("</div>");
                    out.println("</body>");
                }
            } else {
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin:50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                out.println("<h2 style='color:#333;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red;'>Invalid Details</h4>");
                out.println("<p style='color:#555;'>Please check your credentials and try again.</p>");

                out.println("<div style='margin-top:20px;'>");
                out.println("</div>");

                out.println("</div>");
                out.println("</body>");

            }

        } catch (Exception e) { // Exception for JDBC Driver
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }
    }
}
