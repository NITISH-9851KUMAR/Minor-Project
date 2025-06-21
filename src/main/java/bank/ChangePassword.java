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

@WebServlet("/change-password")
public class ChangePassword extends HttpServlet{

    final static String url = "jdbc:mysql://localhost:3306/NitiyaBank";
    final static String userName = "root";
final static String password = "Nitish@04";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acNumber = request.getParameter("accNumber").trim();
        String pass = request.getParameter("newPassword").trim();

        String name= "";
        String regPass= "";

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
                regPass= rSet.getString("pass");
            }


            out.println("<body style='background-color: #2d2d30;'>");
            out.println("<div style='background-color: #ffffff; font-family:Arial, sans-serif; max-width:350px; margin: 50px auto; padding:20px; border:1px solid #ccc; box-shadow:0 0 10px rgba(0,0,0,0.1);'>");
            out.println("<h2 style='text-align: center;'>Nitiya Bank</h2>");
            out.println("<div style='text-align: center;'>");

            // checking if pass is already exist in our bank account
            if (pass.equals(regPass)) {
                out.println("<p style='color:red;'>Password Already Exist</p>");
                out.println("<p style='color: #03a9f4;'>Use New Password</p>");
                out.println("<p><strong>Password:</strong> " + pass + "</p>");
                return;
            }

            // if password length is less then 6
            if(pass.length()<6){
                out.println("<p style='color:red;'>Password must be at least 6 characters</p>");
                out.println("<p style='color: #03a9f4;'>Use Another Password</p>");
                out.println("<p><strong>Password:</strong> " + pass + "</p>");
                return;
            }

            // check password content digit, special symbol, and Uppercase or not

            //flagChar for checking capital alphabet present in password
            boolean flagChar= false; // means capital letter is not present

            //flagChar for checking capital alphabet present in password
            boolean flagSymbol= false; // means capital letter is not present

            //flagChar for checking capital alphabet present in password
            boolean flagNum= false; // means capital letter is not present

            char ch;
            for(int i= 0; i<pass.length(); i++){ // check special Symbol
                ch= pass.charAt(i);
                int ascii= ch;
                if((ascii>34 && ascii< 48) || (ascii>59 && ascii< 65) || (ascii>124 && ascii< 127)){
                    flagSymbol= true;
                    break;
                }
            }
            for(int i= 0; i<pass.length(); i++){ // check capital alphabet present or not
                ch= pass.charAt(i);
                if(Character.isUpperCase(ch)){
                    flagChar= true;
                    break;
                }
            }
            for(int i= 0; i<pass.length(); i++){ // check digit is present or not
                ch= pass.charAt(i);
                if(Character.isDigit(ch)){
                    flagNum= true;
                    break;
                }
            }

            if(!(flagNum && flagChar && flagSymbol)){
                out.println("<p>Your password must contain at least </p>");
                out.println("<p> One digit<br>One special symbol<br> One uppercase letter</p>");
                out.println("<p style='color: #03a9f4;'>Use Another Password</p>");
                out.println("<p><strong>Password:</strong> " + pass + "</p>");
                return;
            }

            query= "UPDATE accountDetails set pass= ? WHERE acc_number=? ";
            pstm = connection.prepareStatement(query);
            pstm.setString(1, pass);
            pstm.setString(2, acNumber);

            int rowAffect= pstm.executeUpdate();
            if(rowAffect>0){
                out.println("<p style='color: green;'>Changed Successfully</p>");
                out.println("<p><strong>New Password:</strong> " + pass + "</p>");
            }

            out.println("<form action='index.jsp' method='post'>");

            out.println("<button type='submit' style='padding:5px 30px; background-color:#4CAF50; color:white; border:none; border-radius:25px; margin-top:15px; font-weight:bold;'>Sign in</button>");
            out.println("</form>");

            out.println("</div>");
            out.println("</div>");
            out.println("</body>");

            pstm.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
