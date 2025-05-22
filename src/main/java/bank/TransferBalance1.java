package bank;

import fun.DateNow;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/TransferBalance1Servlets")

public class TransferBalance1 extends HttpServlet{
    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{

        response.setContentType("text/html");
        PrintWriter out= response.getWriter();

        String senderAcNumber= request.getParameter("senAcNumber").trim();
        String senderName="";

        String receiverAcNumber= request.getParameter("recAcNumber").trim();
        String receiverName="";


        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection= DriverManager.getConnection(url, userName, password);

            // Fetch the Sender Name
            // Checking Receiver Account number is valid or not
            // If valid get the Account Balance and Acc Holder Name
            String query= "SELECT * FROM accountDetails WHERE  acc_number= ?";
            PreparedStatement pstm= connection.prepareStatement(query);
            pstm.setString(1, senderAcNumber);
            ResultSet rSet= pstm.executeQuery();
            if(rSet.next()){
                senderName= rSet.getString("name");
            }

            // Check whether sender is itself Receiver
            if(senderAcNumber.equals(receiverAcNumber)){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>NITIYA BANK</h2>");
                out.println("<div style='text-align: center;'>");
                out.println("<p style='color:red;'>You Itself Receiver Details</p>");
                out.println("<p style='color:black;'>Use Another Account Number <br> Entered Ac/No: "+receiverAcNumber+"</p>");
                out.println("<form action='AccountMenu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + senderName + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");
                return;
            }

            // Checking Receiver Account number is valid or not
            // If valid get the Account Balance and Acc Holder Name
            query= "SELECT * FROM accountDetails WHERE  acc_number= ?";
            pstm= connection.prepareStatement(query);
            pstm.setString(1, receiverAcNumber);
            rSet= pstm.executeQuery();
            if(rSet.next()){
                receiverName= rSet.getString("name");
            }else{
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>NITIYA BANK</h2>");
                out.println("<div style='text-align: center;'>");
                out.println("<p style='color:red;'>Invalid Receiver Account Details</p>");
                out.println("<p style='color:black;'>Receiver Account Number: "+receiverAcNumber+"</p>");
                out.println("<form action='AccountMenu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + senderName + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                return;
            }
            // Check IFSC Code is valid or not
            String ifsc= request.getParameter("ifsc").trim();
            if(!ifsc.equals("PRSH0009851")){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>NITIYA BANK</h2>");
                out.println("<div style='text-align: center;'>");
                out.println("<p style='color:red;'>Invalid IFSC CODE</p>");
                out.println("<p style='color:black;'>IFSC CODE: "+ifsc+"</p>");
                out.println("<form action='AccountMenu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + senderName + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                return;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        RequestDispatcher rd= request.getRequestDispatcher("TransferBalance2.jsp");
        request.setAttribute("recName", receiverName);
        request.setAttribute("recAccNumber", receiverAcNumber);
        request.setAttribute("senAcNumber", senderAcNumber);
        rd.include(request, response);

    }

}
