package duke.utils;

import duke.exceptions.DateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilFunction {
    /**
     * util function used to match pattern of the string
     * @param patternStr the pattern you want to check
     * @param string the string to check
     * @return true if the string matches the string pattern
     */
    public static Boolean matchPattern(String patternStr, String string){
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    public static String formatDateToStandard(String dateString) throws DateFormatException{
        String standardDateFormat = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
            LocalDate date = LocalDate.parse(dateString);
            standardDateFormat = formatter.format(date);
        } catch (DateTimeParseException e) {
            throw new DateFormatException("The date format is not valid.");
        }
        return standardDateFormat;
    }

    public static void printLimit(String output) {
        int width = Constants.consoleWidth;
        int count = 0;
        String[] words = output.split("\\s+");
        for(String word: words) {
            count += word.length();
            if(Objects.equals(word, '\n')) {
                System.out.print('\n');
            } else if (count < width) {
                System.out.print(word + " ");
            } else {
                System.out.print('\n' + word);
                count = word.length();
            }
        }
        System.out.print('\n');
    }
}