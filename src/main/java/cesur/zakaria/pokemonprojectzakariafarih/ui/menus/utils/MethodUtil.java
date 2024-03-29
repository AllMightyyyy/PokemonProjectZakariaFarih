package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.utils;

/**
 * Utility class for checking password strength.
 */
public class MethodUtil {

    /**
     * Checks the strength of a password.
     * @param password The password to check.
     * @return An integer representing the password strength:
     *         - 1: Weak
     *         - 2: Moderate
     *         - 3: Strong
     */
    public static int checkPasswordStrength(String password) {
        int score = 0;
        if (password.length() >= 8) {
            score++;
        }
        boolean hasUppercase = !password.equals(password.toLowerCase());
        if (hasUppercase) {
            score++;
        }
        boolean hasLowercase = !password.equals(password.toUpperCase());
        if (hasLowercase) {
            score++;
        }
        boolean hasDigit = password.matches(".*\\d.*");
        if (hasDigit) {
            score++;
        }
        boolean hasSpecialChar = !password.matches("[A-Za-z0-9]*");
        if (hasSpecialChar) {
            score++;
        }
        if (score < 3) {
            return 1; // Weak
        } else if (score < 5) {
            return 2; // Moderate
        } else {
            return 3; // Strong
        }
    }
}
