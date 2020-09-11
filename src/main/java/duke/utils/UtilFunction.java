package duke.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.dateformats.DateFormat;
import duke.exceptions.DateFormatException;

/**
 * @author Eddy
 * @version 1.0.0
 */
public class UtilFunction {
    /**
     * Util function used to match pattern of the string.
     *
     * @param patternStr the pattern you want to check
     * @param string the string to check
     * @return true if the string matches the string pattern
     */
    public static Boolean matchPattern(String patternStr, String string) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    /**
     * Util function used to format available date input to standard date format.
     *
     * @param dateString input string to format
     * @return date in standard format "MMM d yyyy"
     * @throws DateFormatException when input string date formal is invalid
     * @see duke.exceptions.DateFormatException
     */
    public static String formatDateToStandard(String dateString) throws DateFormatException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        LocalDateTime ldt = null;
        for (DateFormat format: Constants.DATE_FORMAT_LIST) {
            if (format.check(dateString)) {
                ldt = format.formatToStandard(dateString);
                String standardDate = formatter.format(ldt);
                return standardDate;
            }
        }

        throw new DateFormatException("The date format is not valid.");
    }

    /**
     * Util function used to print message within {@value duke.utils.Constants#CONSOLEWIDTH}.
     * @param output the string meant to be printed in the console
     */
    public static void printLimit(String output) {
        String[] sentences = output.split("\n");
        for (String sentence: sentences) {
            if (sentence.length() < Constants.CONSOLEWIDTH) {
                System.out.println(sentence);
            } else {
                assert sentence.length() >= Constants.CONSOLEWIDTH : "sentence length: " + sentence.length();
                wrapAndDisplay(sentence);
            }
        }
    }

    private static void wrapAndDisplay(String sentence) {
        String[] words = sentence.split("\\s+");
        int count = 0;
        for (String word: words) {
            count += word.length();
            if (count < Constants.CONSOLEWIDTH) {
                System.out.print(word + " ");
            } else {
                System.out.print('\n' + word + " ");
                count = word.length();
            }
        }
        System.out.print('\n');
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date now = new Date(System.currentTimeMillis());
        return formatter.format(now);
    }


}
