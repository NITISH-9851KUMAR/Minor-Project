package fun;

import java.util.concurrent.ThreadLocalRandom;
public class AccountNumber {

    public static String accNumber(String prn) {
        String bankNumber = "447318210011";
        StringBuilder str= new StringBuilder();
        for(int i= 9; i<prn.length(); i++){
            str.append(prn.charAt(i));
        }
        // bankNumber and prn last three digit number
        String accNumber= bankNumber+str;
        return accNumber;
    }
}
