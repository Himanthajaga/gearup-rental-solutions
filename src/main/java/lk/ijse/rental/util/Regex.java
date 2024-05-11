package lk.ijse.rental.util;

import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";

        switch (textField){
            case ID:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case NAME:
                filed = "^[A-z|\\\\s]{3,}$";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case PHONE:
                filed = "^([0-9]{3})[-]([0-9]{7})$";
                break;
            case ADDRESS:
                filed = "^[A-z|0-9|\\\\s|,]{3,}$";
                break;
            case PASSWORD:
                filed = "^[A-z|0-9|\\\\s]{3,}$";
                break;
            case OTP:
                filed = "^([0-9]{6})$";
                break;

        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location,TextField textField){

        if (Regex.isTextFieldValid(location, String.valueOf(textField.getClass()))){
            textField.compareTo(textField.valueOf("Green"));
            textField.compareTo(textField.valueOf("Green"));

            return true;
        }else {
            textField.compareTo(textField.valueOf("Red"));
            textField.compareTo(textField.valueOf("Red"));

            return false;
        }
    }
}
