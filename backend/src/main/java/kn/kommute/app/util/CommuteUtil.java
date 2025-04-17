package kn.kommute.app.util;

public class CommuteUtil {

    // Validate if the number of spaces is 2 or more
    public static boolean isVehicleNumberOfSeatsValid(int spaces) {
        return spaces > 1; // Spaces must be greater than one.
    }
}
