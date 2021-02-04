package sey.a.rasp3.shell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xmls {
    public static StringBuffer stringToXml(String fieldName, String text) {
        return stringToXml(fieldName, new StringBuffer(text));
    }

    public static StringBuffer stringToXml(String fieldName, StringBuffer text) {
        StringBuffer result = new StringBuffer();
        result.append('<').append(fieldName).append('>').append(text).append("</").append(fieldName).append('>');
        return result;
    }

    public static StringBuffer dateToXml(String fieldName, Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String result = sdf.format(calendar.getTime());
        return stringToXml(fieldName, result);
    }

    public static String extractString(String fieldName, String text) {
        String result = null;
        String openingTag = "<" + fieldName + ">";
        String closingTag = "</" + fieldName + ">";
        Pattern pattern = Pattern.compile(openingTag + ".*?" + closingTag);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            result = matcher.group().trim().replace(openingTag, "").replace(closingTag, "");
        }
        return result;
    }

    public static List<String> extractStringList(String fieldName, String text) {
        List<String> result = new LinkedList<>();
        String openingTag = "<" + fieldName + ">";
        String closingTag = "</" + fieldName + ">";
        Pattern pattern = Pattern.compile(openingTag + ".*?" + closingTag);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            result.add(matcher.group().trim().replace(openingTag, "").replace(closingTag, ""));
        }
        return result;
    }

    public static Calendar extractDate(String fieldName, String text){
        String string = extractString(fieldName, text);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date date = sdf.parse(string);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            return new GregorianCalendar(1970, 1, 1);
        }
    }

    public static Integer extractInteger(String fieldName, String text){
        int result;
        try {
            result = Integer.parseInt(extractString(fieldName, text));
        }catch (NumberFormatException e){
            result = 0;
        }
        return result;
    }

    public static Long extractLong(String fieldName, String text){
        return Long.parseLong(extractString(fieldName, text));
    }
}
