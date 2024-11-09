package fr.coding.zip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * The {@code fr.coding.zip.DateAndTime} class provides date and time processing methods such as:
 * Calculate the duration of a post from its published date and time to the current date and time {@link DateAndTime#calculatePostDuration(String, String, boolean, String, String, String, String, String, String, String)} <br>
 * get the number of minutes elapsed since a specific time and the current time {@link DateAndTime#getMinutes(String)}<br>
 * get current date {@link DateAndTime#getCurrentDate()} <br>
 * get current time {@link DateAndTime#getCurrentTime()} <br>
 *
 * @author Assammond Andre (andxeur)
 * @since  1.0
 */
public class DateAndTime {

    private static int year;
    private static int currentMonth;
    private static int currentDay;
    private static int currentHour;
    private static int currentMinute;
    private static int currentSecond;
    private static String formattedCurrentMinute, formattedCurrentDay, formattedCurrentHour, formattedCurrentSecond, formattedCurrentMonth;

    private static  void updateCurrentDateAndTime(){

         Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);

        currentMonth = calendar.get(Calendar.MONTH) + 1; // Note: Month starts from 0
        formattedCurrentMonth = String.format("%02d", currentMonth);

        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        formattedCurrentDay = String.format("%02d", currentDay);

        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        formattedCurrentHour = String.format("%02d", currentHour);

        currentMinute = calendar.get(Calendar.MINUTE);
        formattedCurrentMinute = String.format("%02d", currentMinute);

        currentSecond = calendar.get(Calendar.SECOND);
        formattedCurrentSecond = String.format("%02d", currentSecond);

    }

    /**
     * Get the current date Example :
     *
     * <blockquote><pre>
     *     fr.coding.zip.DateAndTime.getCurrentDate();
     *     -> 31/12/2024
     * </pre></blockquote>
     *
     * @return the current date
     */
    public static String getCurrentDate(){
        updateCurrentDateAndTime();
        return formattedCurrentDay + "/" + formattedCurrentMonth + "/" + year;
    }

    /**
     * Get the current time Example :
     *
     * <blockquote><pre>
     *     fr.coding.zip.DateAndTime.getCurrentTime();
     *     -> 12:24:03
     * </pre></blockquote>
     *
     * @return the current time
     */
    public static String getCurrentTime(){
        updateCurrentDateAndTime();
        return  formattedCurrentHour + ":" + formattedCurrentMinute + ":" + formattedCurrentSecond;
    }


    /**
     * Get the difference in minutes between the current time and the publication time Example :
     *
     * <blockquote><pre>
     *     fr.coding.zip.DateAndTime.getMinutes("10:00:00");
     *     -> 60
     * </pre></blockquote>
     *
     * @param hours the publication time in the format {@code HH:mm:ss}
     *
     * @return the difference in minutes
     */
    public static long getMinutes(String hours){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime heureDuPosteFormatter = LocalTime.parse(hours, formatter);
        LocalTime heureActuelle = LocalTime.now();
        Duration difference = Duration.between(heureDuPosteFormatter, heureActuelle);
        return difference.toMinutes();
    }

    private static String calculatePostDurationInDaysMonthsYears(String publicationDate,String msg,String msgIfPostIsRecent,String daysInYourLanguage,String monthsInYourLanguage,String yearsInYourLanguage) {

        int years = 0, months = 0, days = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate publicationDateFormatter = LocalDate.parse(publicationDate, formatter);
        LocalDate currentDate = LocalDate.now();
        Period difference = Period.between(publicationDateFormatter , currentDate );

        years = difference.getYears();
        months = difference.getMonths();
        days = difference.getDays();

        if (years  > 0) {
            //si le nombre d'années est supérieur à 0, cela signifie que la date de publication
            //est antérieure à la date actuelle d'au moins un an. Dans ce cas, on retourne la chaîne de caractères
            return msg +" "+ + years  + " "+ yearsInYourLanguage;
        } else if (months > 0) {
            //si le nombre de mois est supérieur à 0, cela signifie que la date de publication est antérieure
            //à la date actuelle d'au moins un mois, mais pas d'un an entier.
            return msg +" " + months +" "+ monthsInYourLanguage;
        } else if (days > 0) {
            return msg +" "+ + days +" "+ daysInYourLanguage;
        } else {
            return msgIfPostIsRecent;
        }

    }

    private static String calculatePostDurationInHoursOrMinutes(String hoursPublication,String msg,String msgIfPostIsRecent,String hoursInYourLanguage,String minuteInYourLanguage) {

        long minutes = getMinutes(hoursPublication);

        if (minutes >= 60) {
            long hours = minutes / 60;
            return msg +" " + hours + " "+ hoursInYourLanguage;
        } else {

            if (minutes <2) {
                return msgIfPostIsRecent ;
            }
            return msg +" " + minutes + " "+ minuteInYourLanguage;
        }

    }

    private static String calculatePostDurationInHoursOrMinutesAdvanceMode(String hoursPublication, String msg, String msgIfPostIsRecent, String hoursInYourLanguage, String minuteInYourLanguage) {

        long minutes = getMinutes(hoursPublication);

        if (minutes >= 60) {

            long hours = minutes / 60;
            //si il ya une difference de 10h en l'heures du poste et l'heure actuel
            if (hours >= 10) {
                String[] time = hoursPublication.split(":");
                return msg +" "+ String.format("%01d", Integer.parseInt(time[0])) +" "+hoursInYourLanguage+" "+ time[1]+" "+ minuteInYourLanguage;
            }else return msg+ hours + " "+ hoursInYourLanguage;

        } else {

            if (minutes <2) {
                return msgIfPostIsRecent ;
            }
            return msg + " " + minutes + " "+minuteInYourLanguage;
        }

    }

    /**
     * Calculate the duration of a post based on its date and time Example  :<br>
     *let's consider 07/11/24 as the current date and the information on the publication date of your article below:
     *
     * <blockquote><pre>
     *      String postDate = "01/01/2023";
     *      String postTime = "00:00:00";
     *      String msgIfPostIsNotRecent = "Post at";
     *      String msgIfPostIsRecent = "Post is recent";
     *      String daysInYourLanguage = "days";
     *      String monthsInYourLanguage = "months";
     *      String yearsInYourLanguage = "years";
     *      String hoursInYourLanguage = "hours";
     *      String minuteInYourLanguage = "minutes";
     *      fr.coding.zip.DateAndTime.calculatePostDuration(postDate, postTime, false, msgIfPostIsNotRecent, msgIfPostIsRecent, daysInYourLanguage, monthsInYourLanguage, yearsInYourLanguage, hoursInYourLanguage, minuteInYourLanguage);
     *
     *      -> Post at 1 years
     * </pre></blockquote>
     *
     * @param postDate the date of the post in the format{@code dd/MM/yyyy}
     * @param postTime the time of the post in the format{@code HH:mm:ss}
     * @param advanceMode true if you want to use the advanced mode
     * @param msgIfPostIsNotRecent the message to display if the post is not recent
     * @param msgIfPostIsRecent the message to display if the post is recent
     * @param daysInYourLanguage the days in your language{@code days or jours} in French
     * @param monthsInYourLanguage the months in your language ex: {@code "months" or "mois"} in French
     * @param yearsInYourLanguage the years in your language ex: {@code "years" or "annees"} in French
     * @param hoursInYourLanguage the hours in your language ex: {@code "hours" or "heures"} in French
     * @param minuteInYourLanguage the minutes in your language ex: {@code minutes or "minutes"} in French
     *
     * @return the duration of the post
     */
    public static String calculatePostDuration(String postDate, String postTime,boolean advanceMode,String msgIfPostIsNotRecent,String msgIfPostIsRecent,String daysInYourLanguage,String monthsInYourLanguage,String yearsInYourLanguage,String hoursInYourLanguage,String minuteInYourLanguage) {

        if (postDate.equals(DateAndTime.getCurrentDate())) {
            if (advanceMode) {
                return DateAndTime.calculatePostDurationInHoursOrMinutesAdvanceMode(postTime,msgIfPostIsNotRecent,msgIfPostIsRecent,hoursInYourLanguage,minuteInYourLanguage);
            }else return DateAndTime.calculatePostDurationInHoursOrMinutes(postTime,msgIfPostIsNotRecent,msgIfPostIsRecent,hoursInYourLanguage,minuteInYourLanguage);
        } else {
            return DateAndTime.calculatePostDurationInDaysMonthsYears(postDate,msgIfPostIsNotRecent,msgIfPostIsRecent,daysInYourLanguage,monthsInYourLanguage,yearsInYourLanguage);
        }

    }


}
