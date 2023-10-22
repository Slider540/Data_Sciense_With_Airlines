package datasciencewithairlines;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that is used to verify the contents of strings and database fields
 *
 */
public class Verifier {

    /**
     * Method checks the contents of the string for the possibility of
     * converting to a number
     *
     * @param str The string to be parsed
     * @return Value or 0 on error
     */
    public static int verifyInt(String str) {
        try {
            Integer.parseInt(str);
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * Method checks the value of the day of the month for a possible range
     *
     * @param number The value of field DayOfMonth in database
     * @return Value of field or 0 if value is incorrect
     */
    public static int checkDayOfMonth(int number) {
        if (number < 1 || number > 31) {
            return 0;
        } else {
            return number;
        }
    }

    /**
     * Method checks the value of the day of the week for a possible range
     *
     * @param number The value of field DayOfWeek in database
     * @return Value of field or 0 if value is incorrect
     */
    public static int checkDayOfWeek(int number) {
        if (number < 1 || number > 7) {
            return 0;
        } else {
            return number;
        }
    }

    /**
     * Method checks the value of the time for a possible range
     *
     * @param number The value of field DepTime or ArrTime or WheelsOff or
     * WheelsOn in database
     * @return Value of field or 0 if value is incorrect
     */
    public static int checkTime(int number) {
        if (number < 1 || number > 2400) {
            return 0;
        } else {
            return number;
        }
    }

    /**
     * Method checks the value of the attribute Cancelled for a possible range
     *
     * @param number The value of field Cancelled in database
     * @return Value of field or 0 if value is incorrect
     */
    public static int checkCancelled(int number) {
        if (number < 0 || number > 1) {
            return 0;
        } else {
            return number;
        }
    }

    /**
     * Method checks for the presence of an int datatype field in the database string
     * 
     * @param index The index of the element in string parsed to array
     * @param line The flights data parsed to string array
     * @param inarg The type of field (Delay or Others)
     * @return 0 for type Others or 1000000 for type Delay if the field in database is empty
     */
    public static int checkForInt(int index, String[] line, String inarg) {
        int outarg = 0;
        switch (inarg) {
            case "others":
                outarg = 0;
                break;
            case "delay":
                outarg = 1000000;
                break;
        }

        return line.length >= index + 1 ? verifyInt(line[index]) : outarg;
    }

    /**
     * Method checks for the presence of an String datatype field in the database string
     * 
     * @param index The index of the element in string parsed to array
     * @param line The flights data parsed to string array
     * @return "Not filled" if the field is empty
     */
    public static String checkForString(int index, String[] line) {
        String str = line.length >= index + 1 ? line[index] : "Not filled";

        return str.length() == 0 ? "Not filled" : str;
    }

    /**
     * Method checks for a date in the FlightDate field and formats the date
     * value using a single pattern
     * 
     * @param index The index of the element in string parsed to array
     * @param line The flights data parsed to string array
     * @return Date in LocalData format or Local Date "01-01-3999" if the field is empty
     */
    public static LocalDate checkForDate(int index, String[] line) {
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MM-dd-yy");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM/dd/yy");
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String str = line.length >= index + 1 ? line[index] : "Not filled";
        LocalDate date;

        if (!str.equals("Not filled")) {
            if (str.contains("-") && str.length() == 8) {
                date = LocalDate.parse(str, dtf1);
            } else if (str.contains("/") && str.length() == 8) {
                date = LocalDate.parse(str, dtf2);
            } else {
                date = LocalDate.parse("01-01-3999", dtf3);
            }
        } else {
            date = LocalDate.parse("01-01-3999", dtf3);
        }

        return date;
    }
}
