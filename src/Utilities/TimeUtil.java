package Utilities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class for handling time-related operations.
 *
 * @author William Deutsch
 */
public class TimeUtil {

    /**
     * Checks if a given LocalDateTime falls within defined business hours.
     * This method converts the provided LocalDateTime to Eastern Time and checks if it falls within the business hours
     * of 8 AM to 10 PM.
     *
     * @param localDateTime The LocalDateTime to be checked.
     * @return true if the provided time falls within business hours, false otherwise.
     */
    public static boolean isValidBuisnessHour(LocalDateTime localDateTime) {
        //Get Systems Default Time
        ZoneId systemZoneId = ZoneId.systemDefault();

        // Convert local time to ET
        ZonedDateTime localZonedDateTime = localDateTime.atZone(systemZoneId);
        ZonedDateTime easternZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        // Define Business Hours
        LocalTime businessStart = LocalTime.of(8,0); // 8am ET
        LocalTime businessEnd = LocalTime.of(22,00); // 10pm ET

        // Check if the Time is within Business Hours
        return !easternZonedDateTime.toLocalTime().isBefore(businessStart) &&
                !easternZonedDateTime.toLocalTime().isAfter(businessEnd);


    }

}
