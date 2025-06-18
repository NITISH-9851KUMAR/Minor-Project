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

@WebServlet("/change-mob-number")
public class ChangeMobNumber extends HttpServlet{

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String acNumber = request.getParameter("accNumber").trim();
        String mobNumber = request.getParameter("mobNumber");
        String name= "";
        String reMobNumber= "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, userName, password);

            // Getting the Name
            String query = "SELECT * FROM accountDetails WHERE  acc_number= ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, acNumber);
            ResultSet rSet = pstm.executeQuery();

            if (rSet.next()) { // Getting the name and prn number
                name= (rSet.getString("name")).trim();
                reMobNumber= rSet.getString("mob_number");
            }

            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
            out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
            out.println("<div style='text-align: center;'>");

            // checking if input mobile number is already exist in our bank account
            if (mobNumber.equals(reMobNumber)) {
                out.println("<h4 style='color:red;'>Mobile Number Already Registered </h4>");
                out.println("<p><strong>Mobile Number:</strong> " + mobNumber + "</p>");

                // Go to Account Menu Button with hidden inputs
                out.println("<form action='account-menu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + name + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + acNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                return;
            }

            // check mobile number valid or not
            int mobLength= mobNumber.length(); // checking mobile Number
            if(mobLength!=10){
                out.println("<h4 style='color:red;'>Invalid Mobile Number</h4>");
                out.println("<p><strong>Mobile Number:</strong> " + mobNumber + "</p>");

                // Go to Account Menu Button with hidden inputs
                out.println("<form action='account-menu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + name + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + acNumber + "'>");
//                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                return;
            }

            query= "UPDATE accountDetails SET mob_number=? WHERE acc_number=? ";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, mobNumber);
            pstm.setString(2, acNumber);

            int rowAffect = pstm.executeUpdate();
            if(rowAffect>0){
                out.println("<h4 style='color:green; text-align:center;'>Changed Successfully</h4>");
                out.println("<div style='text-align: center;'>");
                out.println("<p><strong>New Mobile Number:</strong> " + mobNumber + "</p>");

                // Go to Account Menu Button with hidden inputs
                out.println("<form action='account-menu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + name + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + acNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
            }

            pstm.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}