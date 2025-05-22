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

@WebServlet("/AccountDetailsServlets")
public class AccountDetails extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{

        response.setContentType("text/html");
        PrintWriter out= response.getWriter();

        String acNumber= request.getParameter("accNumber");

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection= DriverManager.getConnection(url, userName, password);

            String query= "SELECT * FROM accountDetails WHERE  acc_number= ?";
            PreparedStatement pstm= connection.prepareStatement(query);
            pstm.setString(1, acNumber);
            ResultSet rSet= pstm.executeQuery();

            if(rSet.next()){
                String name= rSet.getString("name");
                String mobNumber= rSet.getString("mob_number");
                String prn= rSet.getString("prn_number");
                String pass= rSet.getString("pass");
                String accNum= rSet.getString("acc_number");
                String bal= rSet.getString("acc_balance");
                String opDate= rSet.getString("date_time");

                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:450px; margin: 20px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:green; text-align:center;'>Account Holder Details</h4>");

                out.println("<div style='text-align: center;'>");

                out.println("<p><strong>Account Holder Name: </strong> " + name + "</p>");
                out.println("<p><strong>Mobile Number: </strong> " + mobNumber + "</p>");
                out.println("<p><strong>PRN Number: </strong> " + prn + "</p>");
                out.println("<p><strong>Account Password: </strong> " + pass + "</p>");
                out.println("<p><strong>Account Number: </strong> " + accNum + "</p>");
                out.println("<p><strong>Account Balance: </strong> " + bal + "</p>");
                out.println("<p><strong>Bank Name: </strong> NITIYA BANK</p>");
                out.println("<p><strong>IFSC Code: </strong> PRSH0009851</p>");
                out.println("<p><strong>Account Open Date: </strong> " + opDate + "</p>");

                out.println("</div>");

                out.println("</div>");
                out.println("</body>");

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
