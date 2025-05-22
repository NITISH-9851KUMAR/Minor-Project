package bank;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import static fun.AccountNumber.accNumber;
import static fun.DateNow.dateFun;

@WebServlet(urlPatterns = "/OpenAccountServlets")
public class OpenAccount extends HttpServlet {

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
    final static String password = "Nitish@04";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out= response.getWriter();

        String name= request.getParameter("full_name").trim();
        String bal= request.getParameter("balance").trim();
        String mobNumber = request.getParameter("mob-num").trim();
        String prn= request.getParameter("prn").trim();
        String pass= request.getParameter("password-value").trim();
        String currentDate= dateFun().trim();
        String accNum= accNumber(prn).trim();
        // Bank name is Default "NITIYA BANK"
        // IFSC code is Default
        // Freeze details is Default

        // first check mobile number is invalid or not
        // for mobile number checking it should be 10 digit

        // check if balance is negative number
        // also check whether balance is less than 500

        double checkBal= Double.parseDouble(bal);
        System.out.println((int)checkBal); //200
        if(checkBal< 500){
            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin:100px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");
            out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");

            if(checkBal<=0){
                out.println("<h4 style='color:red;'>Invalid Bank Balance</h4>");
                out.println("<p><strong>Balance :</strong> " + bal + "</p>");
            }
            else{
                out.println("<h4 style='color:red;'>Minimum Balance Should be ₹500</h4>");
                out.println("<p><strong>Balance :</strong> " + bal + "</p>");
            }

            out.println("<a href='openAccount.html' style='display:inline-block; padding:10px 20px; background-color:red; color:white; text-decoration:none; border-radius:25px; font-weight:bold; margin-top:15px;'>Register Again</a>");
            out.println("</div>");
            out.println("</body>");
            return;
        }

        int mobLength= mobNumber.length();
        if(mobLength!=10){
            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin:100px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

            out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");
            out.println("<h4 style='color:red;'>Invalid Mobile Number</h4>");
            out.println("<p><strong>Mobile Number:</strong> " + mobNumber + "</p>");


            out.println("<a href='openAccount.html' style='display:inline-block; padding:10px 20px; background-color:red; color:white; text-decoration:none; border-radius:25px; font-weight:bold; margin-top:15px;'>Register Again</a>");

            out.println("</div>");
            out.println("</body>");
            return;
        }


        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection= DriverManager.getConnection(url, userName, password);

            // checking prn details valid or not through prnDetails database

            String query = "SELECT * FROM prn_details WHERE prn = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, prn); // if prn is an integer
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin:100px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red;'>Invalid PRN Number</h4>");
                out.println("<p><strong>Entered PRN:</strong> " + prn + "</p>");


                out.println("<a href='openAccount.html' style='display:inline-block; padding:10px 20px; background-color:red; color:white; text-decoration:none; border-radius:25px; font-weight:bold; margin-top:15px;'>Register Again</a>");

                out.println("</div>");
                out.println("</body>");
                return;
            }

            //Query for check prn number is already exist in accountDetails database
            query = "SELECT * FROM accountDetails WHERE prn_number = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, prn);
            rs = pstmt.executeQuery();
            if(rs.next()){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red;'>Account Already Exists</h4>");

                out.println("<p>The PRN number <strong>" + prn + "</strong> is already registered with our bank.</p>");
                out.println("<p>If you already have an account, please</p>");
                out.println("<a href='User_Interface.html' style='display:inline-block; padding:10px 20px; background-color:red; color:white; text-decoration:none; border-radius:25px; font-weight:bold;'>Log In</a>");

                out.println("<p style='margin-top:15px;'>If you believe this is an error, contact our support team.</p>");
                out.println("<p>Contact Us: <strong>7250063756</strong></p>");

                out.println("<a href='openAccount.html' style='display:inline-block; padding:10px 20px; background-color:#28a745; color:white; text-decoration:none; border-radius:25px; font-weight:bold; margin-top:10px;'>Try Again with Another PRN</a>");

                out.println("</div>");
                out.println("</body>");
                return;
            }

            // Password Checking 69 to 101
            int passLength= pass.length();
            if(passLength < 6){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin:100px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red;'>Invalid Password</h4>");
                out.println("<p>Password must be at least 6 characters</p>");
                out.println("<p><strong>Entered Password:</strong> " + pass + "</p>");


                out.println("<a href='openAccount.html' style='display:inline-block; padding:10px 20px; background-color:#dc3545; color:white; text-decoration:none; border-radius:25px; font-weight:bold; margin-top:15px;'>Register Again</a>");

                out.println("</div>");
                out.println("</body>");
                return;
            }

            //flagChar for checking capital alphabet present in password
            boolean flagChar= false; // means capital letter is not present

            //flagChar for checking capital alphabet present in password
            boolean flagSymbol= false; // means capital letter is not present

            //flagChar for checking capital alphabet present in password
            boolean flagNum= false; // means capital letter is not present

            char ch;
            for(int i= 0; i<passLength; i++){ // check special Symbol
                ch= pass.charAt(i);
                int ascii= ch;
                if((ascii>34 && ascii< 48) || (ascii>59 && ascii< 65) || (ascii>124 && ascii< 127)){
                    flagSymbol= true;
                    break;
                }
            }
            for(int i= 0; i<passLength; i++){ // check capital alphabet present or not
                ch= pass.charAt(i);
                if(Character.isUpperCase(ch)){
                    flagChar= true;
                    break;
                }
            }
            for(int i= 0; i<passLength; i++){ // check digit is present or not
                ch= pass.charAt(i);
                if(Character.isDigit(ch)){
                    flagNum= true;
                    break;
                }
            }

            if(!(flagNum && flagChar && flagSymbol)){
                out.println("<body style='background-color: #2d2d30;'>");
                out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin:50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

                out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");
                out.println("<h4 style='color:red;'>Invalid Password</h4>");

                out.println("<p><strong>Entered Password:</strong> " + pass + "</p>");
                out.println("<p>Your password must contain at least:</p>");
                out.println("<p>• One digit<br>• One special symbol<br>• One uppercase letter</p>");

                out.println("<a href='openAccount.html' style='display:inline-block; padding:10px 20px; background-color:#dc3545; color:white; text-decoration:none; border-radius:25px; font-weight:bold; margin-top:15px;'>Register Again</a>");

                out.println("</div>");
                out.println("</body>");
                return;

            }

            // If above all condition is satisfied then it will execute successfully

            query= "INSERT INTO accountDetails(name, mob_number, prn_number, pass, acc_number, acc_balance, date_time)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, mobNumber);
            pstmt.setString(3, prn);
            pstmt.setString(4, pass);
            pstmt.setString(5, accNum);
            pstmt.setString(6, bal);
            pstmt.setString(7, currentDate);

            pstmt.executeUpdate();

            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:400px; margin:auto; padding:10px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1); text-align:center;'>");

            out.println("<h2 style='text-align:center;'>Nitiya Bank</h2>");
            out.println("<h4 style='color:green;'>Account Opened Successfully</h4>");

            out.println("<p><strong>Account Holder Name:</strong> " + name + "</p>");
            out.println("<p><strong>Mobile Number:</strong> " + mobNumber + "</p>");
            out.println("<p><strong>PRN Number:</strong> " + prn + "</p>");
            out.println("<p><strong>Account Password:</strong> " + pass + "</p>");
            out.println("<p><strong>Account Number:</strong> " + accNum + "</p>");
            out.println("<p><strong>Account Balance:</strong> " + bal + "</p>");
            out.println("<p><strong>Bank Name:</strong> Nitiya Bank</p>");
            out.println("<p><strong>IFSC Code:</strong> PRSH0009851</p>");
            out.println("<p><strong>Account Open Date:</strong> " + currentDate + "</p>");

            out.println("<div style='margin-top:20px; margin-bottom: 20px;'>");
            out.println("<a href='User_Interface.html' style='padding:10px 20px; background-color:red; color:white; text-decoration:none; border-radius:25px; font-weight:bold;'>Log In to Your Account</a>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");

            // Create The Transaction Details of respective prn number
            float balVal= Float.parseFloat(bal);
            new fun.CreateTransaction(prn, name, balVal, currentDate, "Account Open");


        }catch (SQLException e) { // Exception for JDBC Driver
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
