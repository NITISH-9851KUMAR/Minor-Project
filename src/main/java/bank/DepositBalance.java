package bank;

import fun.DateNow;

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


@WebServlet("/DepositBalanceServlets")
public class DepositBalance extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String acNumber = request.getParameter("accNumber").trim();
        String val= request.getParameter("dep-bal");
        float depositBal= Float.parseFloat(val);
        String currentDate= DateNow.dateFun();
        String prn= "";
        String name= "";

        // check if user deposit balance in negative number
//        depositBal = -96
        if(depositBal<=0){
            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
            out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
            out.println("<h4 style='color:red; text-align:center;'>Balance Deposit Failed</h4>");
            out.println("<div style='text-align: center;'>");
            out.println("<p><strong>Entered Deposited Balance:</strong> " + depositBal + "</p>");

            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            return;
        }

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, userName, password);

            String query = "SELECT * FROM accountDetails WHERE  acc_number= ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, acNumber);
            ResultSet rSet = pstm.executeQuery();


            double acBal= 0;
            if (rSet.next()) { // Getting the name and prn number
                acBal= rSet.getDouble("acc_balance");
                prn= (rSet.getString("prn_number")).trim();
                name= (rSet.getString("name")).trim();
            }

            double totalBal= depositBal+acBal;
            //Query for deposit the balance in the respective bank account
            query= "UPDATE accountDetails SET acc_balance=? WHERE acc_number=? ";
            pstm = connection.prepareStatement(query);
            pstm.setDouble(1, totalBal);
            pstm.setString(2, acNumber);
            int rowAffect = pstm.executeUpdate();

            if (rowAffect > 0) {

                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
                out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:green; text-align:center;'>Balance Deposited Successfully</h4>");
                out.println("<div style='text-align: center;'>");
                out.println("<p><strong>Deposit Balance:</strong> " + depositBal + "</p>");

                // Go to Account Menu Button with hidden inputs
                out.println("<form action= 'AccountMenu.jsp' method='post'>");
                out.println("<input type= 'hidden' name='name' value='" + name + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + acNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");

                out.println("</div>");
                out.println("</div>");
                out.println("</body>");


                //DepositTransaction class which add deposit Balance Transaction Table
                new fun.DepositTransaction(prn, name, depositBal, totalBal, currentDate, "Banks");
                connection.close();
            }

            pstm.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
