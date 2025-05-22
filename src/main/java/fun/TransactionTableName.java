package fun;

public class TransactionTableName {
    public static String  TransactionTableName(String name, String prn){

        String tranTableName="";
        // Extract first part of name (before space)
        StringBuilder firstName = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (ch != ' ') {
                firstName.append(ch);
            } else {
                break;
            }
        }

        // Extract last five digits of PRN (safe indexing check)
        StringBuilder lastFiveDigitPrn = new StringBuilder();
        if (prn.length() >= 12) {
            for (int i = 7; i < 12; i++) {
                lastFiveDigitPrn.append(prn.charAt(i));
            }
        }

        tranTableName= (firstName.toString()+lastFiveDigitPrn).trim();
        return tranTableName;

    }
}
