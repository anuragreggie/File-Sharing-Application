import java.util.regex.Pattern;

/**
 * Holds the exit string
 */
public abstract class Configuration {

    public static final String exitString = "exit";

    // Regex from: https://mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
    public static final String IPV4_PATTERN =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    public static final Pattern pattern = Pattern.compile(Configuration.IPV4_PATTERN);

}