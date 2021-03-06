package fi.dy.masa.enderutilities.util;


public class EUStringUtils
{
    /**
     * Formats the number in value into a floored, postfixed form for display.
     * Supported formats are: 0..1000, 1.0k..9.9k, 10k..999k, 1M..nM
     * @param value
     * @return
     */
    public static String formatNumberFloorWithPostfix(int value)
    {
        if (value >= 1000000000)
        {
            return String.format("%dG", value / 1000000000);
        }

        if (value >= 1000000)
        {
            return String.format("%dM", value / 1000000);
        }

        if (value >= 10000)
        {
            return String.format("%dk", value / 1000);
        }

        if (value > 1000)
        {
            return String.format("%.1fk", ((float)value) / 1000);
        }

        return String.valueOf(value);
    }

    /**
     * Formats the number in value with thousand separator "," for display.
     * @param value
     * @return
     */
    public static String formatNumberWithKSeparators(int value)
    {
        StringBuilder sb = new StringBuilder(16);

        String number = String.valueOf(value);
        int len = number.length();
        int end = len % 3, i = 0;

        sb.append(number.substring(0, end));

        for (i = end; i <= (len - 3); i += 3)
        {
            if (i > 0)
            {
                sb.append(",");
            }

            sb.append(number.substring(i, i + 3));
        }

        return sb.toString();
    }

    /**
     * Formats the string in str into the initial letters of each word, imploded with "."
     * @param str
     * @return
     */
    public static String getInitialsWithDots(String str)
    {
        StringBuilder sb = new StringBuilder(64);
        String[] split = str.split(" ");

        for (String s : split)
        {
            sb.append(s.substring(0,  1)).append(".");
        }

        return sb.toString();
    }
}
