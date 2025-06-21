package bank;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login-request")
public class loginRequest extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        response.setContentType("text/html");
        PrintWriter out= response.getWriter();

        String accNumber= request.getParameter("accNumber").trim();
        String userPass= request.getParameter("pass").trim();

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection= DriverManager.getConnection(url, userName, password);
            String query= "SELECT * FROM accountDetails WHERE pass= ?  AND acc_number= ?";
            PreparedStatement pstm= connection.prepareStatement(query);
            pstm.setString(1, userPass);
            pstm.setString(2, accNumber);
            ResultSet rSet= pstm.executeQuery();

            if(rSet.next()){
                String acName= rSet.getString("name");
                request.setAttribute("accNumber", accNumber);
                request.setAttribute("name", acName);
                RequestDispatcher rd = request.getRequestDispatcher("account-menu.jsp");
                rd.forward(request, response);
            }
            else {
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; margin: 100px auto; max-width:350px; padding:10px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");

                out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red; text-align:center;'>Invalid Ac Number / Password</h4>");
                out.println("<p style='text-align:center;'> <strong>Ac/No: </strong> " + accNumber + "</p>");
                out.println("<p style='text-align:center;'> <strong>Password: </strong> " + userPass + "</p>");

                out.println("<div style='text-align: center; margin-bottom: 10px;'>");
                out.println("</div>");

                out.println("</div>");
                out.println("</body>");
            }

        }catch (ClassNotFoundException e) { // Exception for JDBC Driver
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }
        catch (SQLException e) { // Exception for JDBC Driver
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }
    }

}
