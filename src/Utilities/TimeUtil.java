package Utilities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

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
