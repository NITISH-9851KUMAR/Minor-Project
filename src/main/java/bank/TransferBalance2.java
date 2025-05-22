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

@WebServlet("/TransferBalance2Servlets")

public class TransferBalance2 extends HttpServlet{
    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{

        response.setContentType("text/html");
        PrintWriter out= response.getWriter();

        double transferBal= Double.parseDouble(request.getParameter("bal").trim());

        String senderAcNumber= request.getParameter("senAcNumber").trim();
        String senderPrn= "";
        String senderName="";
        double senderBal= 0.0;

        String receiverAcNumber= request.getParameter("recAcNumber").trim();
        String receiverPrn= "";
        String receiverName="";
        double receiverBal= 0.0;

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection= DriverManager.getConnection(url, userName, password);

            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
            out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
            out.println("<div style='text-align: center;'>");

            // Checking Receiver Account number is valid or not
            // If valid get the Account Balance and Acc Holder Name
            String query= "SELECT * FROM accountDetails WHERE  acc_number= ?";
            PreparedStatement pstm= connection.prepareStatement(query);
            pstm.setString(1, receiverAcNumber);
            ResultSet rSet= pstm.executeQuery();
            if(rSet.next()){
                receiverBal= rSet.getDouble("acc_balance");
                receiverName= rSet.getString("name");
                receiverPrn= rSet.getString("prn_number");
            }else{
                out.println("<p style='color:red;'>Invalid Receiver Account Details</p>");
                out.println("<p style='color:black;'>Receiver Account Number: "+receiverAcNumber+"</p>");
                out.println("<form action='AccountMenu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + senderName + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");
                return;
            }

            // Get the sender Bank account Details
            // Checking sender Account number is valid or not
            // If valid get the Account Balance and Acc Holder Name
            query= "SELECT * FROM accountDetails WHERE  acc_number= ?";
            pstm= connection.prepareStatement(query);
            pstm.setString(1, senderAcNumber);
            rSet= pstm.executeQuery();
            if(rSet.next()){
                senderBal= rSet.getDouble("acc_balance");
                senderName= rSet.getString("name");
                senderPrn= rSet.getString("prn_number");
            }

            // check if Transfer Balance in negative
            if(transferBal<=0){
                out.println("<p style='color:red;'>Invalid Transfer Balance</p>");
                out.println("<p style='color:black;'>Entered Transfer Balance: "+transferBal+"</p>");

                out.println("<form action='AccountMenu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + senderName + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");
                return;
            }


            // check if available balance of sender account is not less the transfer amount
            if(senderBal<transferBal){
                out.println("<p style='color:red;'>Insufficient Balance</p>");
                out.println("<p style='color:black;'>The money in your account is not enough for this payment. Check account balance and try again</p>");
                // Check Balance
                out.println("<form action='CheckBalanceServlets' method='post'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Check Balance</button>");
                out.println("</form>");
                //             Go to Account Menu Button with hidden inputs
                out.println("<form action='AccountMenu.jsp' method='post'>");
                out.println("<input type='hidden' name='name' value='" + senderName + "'>");
                out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
                out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
                out.println("</form>");
                return;
            }

            String currentDate= DateNow.dateFun();

            // Paid the Transfer Money into Receiver Bank Account
            double totalTranBalance= receiverBal+transferBal;
            query= "UPDATE accountDetails SET acc_balance= ? WHERE acc_number= ?";
            pstm= connection.prepareStatement(query);
            pstm.setDouble(1, totalTranBalance);
            pstm.setString(2, receiverAcNumber);
            pstm.executeUpdate();
            // Update Transaction Details
//            DepositTransaction(String prn, String name, double depositBal, double totalBal, String currentDate, String Details)
            new fun.DepositTransaction(receiverPrn, receiverName, transferBal, totalTranBalance , currentDate ,senderName);


            // also update the sender account
            double remainBal= senderBal-transferBal;
            query= "UPDATE accountDetails SET acc_balance= ? WHERE acc_number= ?";
            pstm= connection.prepareStatement(query);
            pstm.setDouble(1, remainBal);
            pstm.setString(2, senderAcNumber);
            pstm.executeUpdate();
            new fun.WithdrawTransaction(senderPrn, senderName, transferBal, remainBal, currentDate, receiverName);

            out.println("<p style='color:green;'>Transfer Balance Successfully</p>");
            out.println("<p style='color:black ;'>Ac Holder Name: "+receiverName+"</p>");
            out.println("<p style='color:black;'>Account Number: "+receiverAcNumber+"</p>");
            out.println("<p style='color:black;'>Transfer Amount: "+transferBal+"</p>");


//             Go to Account Menu Button with hidden inputs
            out.println("<form action='AccountMenu.jsp' method='post'>");
            out.println("<input type='hidden' name='name' value='" + senderName + "'>");
            out.println("<input type='hidden' name='accNumber' value='" + senderAcNumber + "'>");
            out.println("<button type='submit' style='padding:10px 20px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Go to Account Menu</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</div>");
            out.println("</body>");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
