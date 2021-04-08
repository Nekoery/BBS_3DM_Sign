package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>();

    public static String millis2String(final long millis, final DateFormat format) {
        return format.format(new Date(millis));
    }

    public static SimpleDateFormat getSimpleFormat() {
        return getDateFormat("yyyy-MM-dd HH:mm:ss") ;
    }

    public static SimpleDateFormat getFormat(String pattern){
        return getDateFormat(pattern);
    }

    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        } else {
            simpleDateFormat.applyPattern(pattern);
        }
        return simpleDateFormat;
    }
}
