package lk.ijse.rental.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static boolean idValidation(String id) {
        Pattern pattern = Pattern.compile("^(S00-)[0-9]{1,3}$");
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }
}