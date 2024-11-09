package fr.coding.zip;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *the class {@code fr.coding.zip.Utils} mainly provides methods for processing character strings such as: <br>
 *Extract initials from a string {@link Utils#extractInitials} <br>
 *Capitalize the first letter of a string {@link Utils#capitalizeFirstLetter(String)} <br>
 *Separate each letter of a string with spaces {@link Utils#separateEachLetterWithSpaces(String)} <br>
 *Check the strength of a password {@link Utils#verifyPasswordStrength(String, int, String, String, String, String)} <br>
 *Make a copyright {@link Utils#copyrights(int)}
 * @author Assammond Andre (andxeur)
 * @since  1.0
 */
public class Utils {

    /**
     * Make a copyright notice exemple :
     *
     *  <blockquote><pre>
     *      System.out.println(fr.coding.zip.Utils.copyrights(2022));
     *      -> Copyrights © 2022 - 2024
     *  </pre></blockquote>
     *
     *  <b>Note : </b>
     *  Enter directly the date on which you started your project,
     *  the current date is automatically obtained to make the copyright
     *
     * @param dateOfStartProject the date on which you started your project
     *
     * @return the copyright String
     */
    public static String copyrights(int dateOfStartProject) {
        return  String.format("Copyrights © %1$s - %2$d", dateOfStartProject, Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * Capitalize the first letter of a string
     *
     * <blockquote><pre>
     *     String str = "ello world";
     *     fr.coding.zip.Utils.capitalizeFirstLetter(str)
     *     -> Ello world
     * </pre></blockquote>
     *
     * @param inputString the string to capitalize the first letter
     *
     * @return the string with the first letter in upper case
     *
     * @see Utils#capitalizeFirstLetterOfEachWord(String)
     */
    public static String capitalizeFirstLetter(String inputString) {
        return inputString.substring(0, 1).toUpperCase() + inputString.substring(1);
    }

    /**
    * Extract initials from a string
     *
     * <blockquote><pre>
     *     String str = "Java Development Kit";
     *     -> J D K
     * </pre></blockquote>
     *
     * @param input The string to extract the initials
     *
     * @return The initials of the string
     *
    */
    public static String extractInitials(String input) {
        StringBuilder result = new StringBuilder();
        String[] words = input.split(" ");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(word.charAt(0));
            }
        }
        return result.toString();
    }

    /**
     * Put each first letter of each word in upper case
     *
     *  <blockquote><pre>
     *      String str = "ello world";
     *      -> Ello  World
     *  </pre></blockquote>
     *
     * @param input the text to capitalize each first letter of each word
     *
     * @return the string with each first letter in upper case
     */
    public static String capitalizeFirstLetterOfEachWord(String input) {

        int i = 0;

        StringBuilder result = new StringBuilder();
        String[] words = input.split(" ");

        for (String word : words) {
            ++i;
            if (!word.isEmpty()) {
                // if it is the last word of the sentence remove the space of end
                if (i == words.length){
                    result.append(word.substring(0, 1).toUpperCase() + word.substring(1));
                }else result.append(word.substring(0, 1).toUpperCase() + word.substring(1)+" ");
            }
        }

        return result.toString();
    }


    /**
     *
     * Separate each letter of a string with spaces
     *
     *  <blockquote><pre>
     *      String str = "Ello World";
     *      -> E l l o  W o r l d
     *  </pre></blockquote>
     *
     * @param input the string to separate each letter
     *
     * @return the string with each letter separated by a space
     */
    public static String separateEachLetterWithSpaces(String input) {
        return input.replace("", " ").trim();
    }


    /**
     * Verify the strength of a password
     *
     * <blockquote><pre>
     *      String password = "HelloWorld";
     *      int minimumCharacter = 8;
     *      String msgIfPasswordIsStrong = "Your password is strong";
     *      String msgIfPasswordIsWeak = "please add at least one special character";
     *      String msgIfPasswordIsShortThanMinimunCharacter = "Your password is shorter than minimum character";
     *      String msgIfPasswordIsEmpty = "Your password is empty";
     *
     *      -> please add at least one special character (-#&_%$)
     * </pre></blockquote>
     *
     * @param password the password
     * @param minimumCharacter the minimum character required of the password
     * @param msgIfPasswordIsStrong message if the password is strong
     * @param msgIfPasswordIsWeak message if the password is weak
     * @param msgIfPasswordIsShortThanMinimunCharacter message if the password is shorter than minimum character
     * @param msgIfPasswordIsEmpty message if the password is empty
     *
     * @return the one of this message if the password is strong or weak or shorter than minimum character required
     *
     * @see Utils#verifyPasswordStrength(String, int)
     */
    public static String verifyPasswordStrength(String password, int minimumCharacter,String msgIfPasswordIsStrong,String msgIfPasswordIsWeak,String msgIfPasswordIsShortThanMinimunCharacter,String msgIfPasswordIsEmpty) {

        if (password.length() >= minimumCharacter) {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
            Matcher matcher =  pattern.matcher(password.toString());

            if (matcher.find()) {
                return msgIfPasswordIsStrong;
            }else {
                return msgIfPasswordIsWeak+" (-#&_%$)";
            }

        }
        else if(password.isEmpty()){
            return msgIfPasswordIsEmpty;
        }
        else {
            return msgIfPasswordIsShortThanMinimunCharacter;
        }

    }

    /**
     * Verify the strength of a password  Example :
     *
     * <blockquote><pre>
     *      String password = "HelloWorld";
     *      int minimumCharacter = 8;
     *
     *      -> false
     * </pre></blockquote>
     *
     * @param password the password
     * @param minimumCharacter the minimum character required of the password

     * @return true if the password is strong or false if the password is weak or shorter than minimum character required
     */
    public static boolean verifyPasswordStrength(String password, int minimumCharacter) {

        if (password.length() >= minimumCharacter) {
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
            Matcher matcher =  pattern.matcher(password.toString());
            return matcher.find();
        }
        else {
            return false;
        }

    }
}
