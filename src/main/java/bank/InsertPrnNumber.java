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


@WebServlet("/insert-prn")

public class InsertPrnNumber extends HttpServlet{

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String prnNumber = request.getParameter("prnNumber").trim();
        String pass= request.getParameter("pass").trim();
        String pass1= "NITIYA@9851P";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection;
            String query;
            PreparedStatement pstm;
            ResultSet rSet;


            if(prnNumber.length()!=12) { // Checking prn is invalid
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red; text-align:center;'>Invalid PRN Number</h4>");
                out.println("<p style='text-align: center;'>PRN Number: "+prnNumber+"</p>");
                out.println("<div style='text-align: center;'>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                return;
            }
            if(!pass.equals(pass1)) { // Check For password
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red; text-align:center;'>Invalid PRN Number</h4>");
                out.println("<p style='text-align: center;'>Invalid  Password: "+pass+"</p>");
                out.println("<div style='text-align: center;'>");
                // Go to Account Menu Button with hidden inputs
                out.println("<a href='insert-prn.jsp'><button type='submit' style='padding:5px 20px; background-color:#15dd25; color:white; border:none; border-radius:25px; margin-top:25px; font-weight:bold;'>Insert Prn</button></a>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                return;
            }

            connection = DriverManager.getConnection(url, userName, password);
            query = "SELECT COUNT(sr_no) FROM prn_details";
            pstm = connection.prepareStatement(query);
            rSet= pstm.executeQuery();
            int count= 0;
            if(rSet.next()){
                count= rSet.getInt(1);
            }

            count+= 1;

            query = "INSERT INTO prn_details VALUES(?, ?)";
            pstm = connection.prepareStatement(query);
            pstm.setInt(1, count);
            pstm.setString(2, prnNumber);
            int rowAff=  pstm.executeUpdate();

            if(rowAff>0){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:green; text-align:center;'>PRN Successfully Inserted</h4>");
                out.println("<div style='text-align: center;'>");
                out.println("<p style='text-align: center;'>PRN Number: "+prnNumber+"</p>");
                // Go to Account Menu Button with hidden inputs;
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
            }

            pstm.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
