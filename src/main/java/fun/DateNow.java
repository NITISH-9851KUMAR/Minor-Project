package fun;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateNow {

    public static String dateFun(){
        LocalDateTime local= LocalDateTime.now();
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yy hh:mm");
        String val= ""+local.format(formatter);
        return val;
    }
}
