package lk.ijse.rental.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {


    public static String dateNow() {
        return null;
    }
    public static String timenow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd                  HH:mm:ss:aa");
        return simpleDateFormat.format(new Date());
    }
}
