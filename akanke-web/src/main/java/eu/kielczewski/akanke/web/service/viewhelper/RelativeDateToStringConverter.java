package eu.kielczewski.akanke.web.service.viewhelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

class RelativeDateToStringConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelativeDateToStringConverter.class);
    private static final long MILLISECONDS_IN_SECOND = 1000;
    private static final long MINUTES_IN_HOUR = 60;
    private static final long DAYS_IN_MONTH = 30;
    private static final long HOURS_IN_DAY = 24;
    private static final long DAYS_IN_YEAR = 365;
    private static final long ONE_MINUTE = 60000;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_MONTH = 30 * ONE_DAY;
    private static final long ONE_YEAR = 12 * ONE_MONTH;

    public String convert(Date date) {
        LOGGER.debug("Converting {} to string", date);
        long delta = new Date().getTime() - date.getTime();
        if (delta < 2 * ONE_MINUTE) {
            return "just now";
        }
        if (delta < ONE_HOUR) {
            return toMinutes(delta) + " minutes ago";
        }
        if (delta < 2 * ONE_HOUR) {
            return "1 hour ago";
        }
        if (delta < ONE_DAY) {
            return toHours(delta) + " hours ago";
        }
        if (delta < 2 * ONE_DAY) {
            return "yesterday";
        }
        if (delta < ONE_MONTH) {
            return toDays(delta) + " days ago";
        }
        if (delta < ONE_YEAR) {
            long months = toMonths(delta);
            if (months <= 1) {
                return "one month ago";
            } else {
                return months + " months ago";
            }
        } else {
            long years = toYears(delta);
            if (years <= 1) {
                return "one year ago";
            } else {
                return years + " years ago";
            }
        }
    }

    private long toSeconds(long date) {
        return date / MILLISECONDS_IN_SECOND;
    }

    private long toMinutes(long date) {
        return toSeconds(date) / MINUTES_IN_HOUR;
    }

    private long toHours(long date) {
        return toMinutes(date) / MINUTES_IN_HOUR;
    }

    private long toDays(long date) {
        return toHours(date) / HOURS_IN_DAY;
    }

    private long toMonths(long date) {
        return toDays(date) / DAYS_IN_MONTH;
    }

    private long toYears(long date) {
        return toMonths(date) / DAYS_IN_YEAR;
    }

}
