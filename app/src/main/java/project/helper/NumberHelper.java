package project.helper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberHelper {
    public static String ThousandSeperator(double value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###");
        return formatter.format(value);
    }

    public static String ThousandSeperator(long value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###");
        return formatter.format(value);
    }

    public static String ThousandSeperator(float value) {
        DecimalFormat decim = new DecimalFormat("#,###.##");
        return decim.format(value);
    }
}
