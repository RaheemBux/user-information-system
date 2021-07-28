package com.sapphire.testsapphire.util;

import com.sapphire.testsapphire.entity.UserEntity;
import com.sapphire.testsapphire.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    static Logger logger = LoggerFactory.getLogger(CommonUtil.class);


    static UserEntity user;

    @Autowired
    static UserService userService;

    public static void setUser(String userName) {
        if (userName != null) {

            UserEntity userN = userService.findByUserName(userName);
            if (userN != null) {
                user = userN;
            } else {
                user = null;
            }

        }
    }

    public UserEntity getUser() {
        return CommonUtil.user;
    }

    public static Double findSmallestNumberBetween3(Double first, Double second, Double third) {
        double[] numbers = {first, second, third};
        double smallest = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < smallest) {
                smallest = numbers[i];
            }
        }
        return smallest;
    }

    public static Boolean stringContainsOnlyNumbers(String text) {
        return text.matches("[0-9]+");
    }

    public static Long daysBetweenTwoDates(Date firstDate, Date secondDate) {
        long difference = (firstDate.getTime() - secondDate.getTime()) / 86400000;
        return Math.abs(difference);
    }

    public static Long daysBetweenTwoSQLDates(java.sql.Date firstDate, java.sql.Date secondDate) {
        long difference = (firstDate.getTime() - secondDate.getTime()) / 86400000;
        return Math.abs(difference);
    }

    public static Date parseDate(String date) throws Exception {
        return new SimpleDateFormat("MM/dd/yyyy").parse(date);
    }

    public static Double dashPercentOfValue(Double percentage, Double value) throws Exception {
        if (percentage != null && value != null) {
            return (value / 100) * percentage;
        }
        return 0.0;
    }

    public static String changeDateToString(Date date) {
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (date != null) {
            String formatedDate = sdf.format(date);

            return formatedDate;
        }
        return null;
    }

    public static Date getCurrentDate(Date date) {
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (date != null) {
            String formatedDate = sdf.format(date);

            return changeDateStringToDate(formatedDate);
        }
        return null;
    }


    public static Date changeDateStringToDate(String date) {
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date parsedDate = null;
        if (date != null) {
            try {
                parsedDate = sdf.parse(date);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception:" + e);
            }
        }
        return parsedDate;
    }

    public static java.sql.Date changeDateStringToSqlDate(String date) {
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date parsedDate = null;
        java.sql.Date sqlDate = null;
        try {
            parsedDate = sdf.parse(date);
            sqlDate = new java.sql.Date(parsedDate.getTime());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception:" + e);
        }
        return sqlDate;
    }

    public static Timestamp getCurrentTimestamp() {
        Timestamp currentTimestamp = null;
        try {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            currentTimestamp = new Timestamp(now.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception:" + e);
        }
        return currentTimestamp;
    }

    public static Timestamp getCurrentTimestamp(String minutes) {
        Timestamp currentTimestamp = null;
        try {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            currentTimestamp = new Timestamp(now.getTime() + Integer.parseInt(minutes) * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception:" + e);
        }
        return currentTimestamp;
    }


    public static String changeTimestampToString(Timestamp timestamp) {
        Date date = timestamp;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String date1 = sdf.format(date);
        return date1;
    }


    public static Timestamp changeTimestampStringToTimestamp(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        Timestamp timestamp1 = null;
        try {
            Date parsedDate = dateFormat.parse(timestamp);
            timestamp1 = new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception:" + e);
        }
        return timestamp1;

    }

    public static void getTaxformReport(Integer taxformId) {

    }

    public static Double calculateAge(Date birthDate, Date currentDate) {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        //create calendar object for current day
        /*long currentTime = System.currentTimeMillis();*/
        Calendar now = Calendar.getInstance();
        /*now.setTimeInMillis(currentDate.get);*/
        now.setTime(currentDate);
        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        //Create new Age object
        /*return new Age(days, months, years);*/
        return Double.parseDouble(years + "");
    }

    public static Double allowedTaxableIncomePercentageOnPensionFunds(Double age) {
        if (age != null) {
            if (age.compareTo(40.0) <= 0) {
                return 20.0;
            }
            if (age.compareTo(40.0) > 0) {
                if (age.compareTo(41.0) == 0) {
                    return 22.0;
                }
                if (age.compareTo(42.0) == 0) {
                    return 24.0;
                }
                if (age.compareTo(43.0) == 0) {
                    return 26.0;
                }
                if (age.compareTo(44.0) == 0) {
                    return 28.0;
                }
                if (age.compareTo(45.0) == 0) {
                    return 30.0;
                }
                if (age.compareTo(45.0) > 0) {
                    return 30.0;
                }
            }
        }
        return 0.0;
    }

    public static Throwable getRootCause(Throwable throwable) {
        if (throwable.getCause() != null)
            return getRootCause(throwable.getCause());

        return throwable;
    }

    public static synchronized java.sql.Date addDays(java.sql.Date date, Long days) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        sqlDate.setTime(sqlDate.getTime() + days * 1000 * 60 * 60 * 24);
        return sqlDate;

    }

    public static boolean checkBetween(Date dateToCheck, Date startDate, Date endDate) {
        return dateToCheck.compareTo(startDate) >= 0 && dateToCheck.compareTo(endDate) <= 0;
    }

    public static Timestamp getCurrentTimestampWithExtendedSeconds() {
        int sec = 90;
        long retryDate = System.currentTimeMillis();
        Timestamp original = new Timestamp(retryDate);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(original.getTime());
        cal.add(Calendar.SECOND, sec);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Integer getDifferenceBetweenDate(Timestamp firstLoginTime, Timestamp currentServerTime) {
        long milliseconds = currentServerTime.getTime() - firstLoginTime.getTime();
        int seconds = (int) milliseconds / 1000;
        return seconds;
    }

    public static String getYearFromTimestamp(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        int year1 = calendar.get(calendar.YEAR);
        String year = String.valueOf(year1);
        return year;
    }

    public static Timestamp convertStringDateToTimestamp(String date) throws ParseException {
        SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = datetime.parse(date);
        return new Timestamp(parse.getTime());
    }
    public static String doubleToString(Double d) {
        if (d == null)
            return null;
        if (d.isNaN() || d.isInfinite())
            return d.toString();

        // Pre Java 8, a value of 0 would yield "0.0" below
        if (d.doubleValue() == 0)
            return "0";
        return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
    }
}
