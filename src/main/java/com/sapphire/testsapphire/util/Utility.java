package com.sapphire.testsapphire.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Utility {

    public static Integer getDifferenceBetweenDate(Timestamp firstLoginTime, Timestamp currentServerTime) {
        long milliseconds = currentServerTime.getTime() - firstLoginTime.getTime();
        int seconds = (int) milliseconds / 1000;
        return seconds;
    }

    public static Timestamp getCurrentTimestamp() {
        Timestamp currentTimestamp = null;
        try {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            currentTimestamp = new Timestamp(now.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTimestamp;
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

    public static Pageable createPageRequest(Map<String, String> params) {
        Pageable pageable = null;
        String page = params.get("page");
        String itemsPerPage = params.get("itemsPerPage");
        String sortBy = params.get("sortBy");
        String direction = params.get("direction");

        if (sortBy != null && !sortBy.equalsIgnoreCase("") && !sortBy.equalsIgnoreCase("undefined")) {
            if (direction != null && !direction.equalsIgnoreCase("") && !sortBy.equalsIgnoreCase("undefined")) {
                if (direction.equalsIgnoreCase("desc")) {
                    pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage), Sort.by(sortBy).descending());
                } else {
                    pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage), Sort.by(sortBy));
                }
            } else {
                pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage), Sort.by(sortBy));
            }
        } else {
            pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(itemsPerPage));
        }
        return pageable;
    }

}
