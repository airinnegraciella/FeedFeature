package com.example.feedfeature.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constant {
    public static final String SP_APP = "spApp";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_EMPLOYEE_ID = "spEmployeeId";
    public static final String SP_IS_LOGGED_IN = "spIsLoggedIn";
    private static final String IMAGE_ASSET_PATH_SERVER = "/assets/images/";
    public static final String IMAGE_TYPE_EMPLOYEE = "employees/";
    public static final String IMAGE_TYPE_FEED = "feed/";
    private static final String HOST = "http://hris.anagata.co.id";

    public static String convertDateTimeToFullDateDay(String date){
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        try {
            Date newDate = spf.parse(date);
            spf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);
            return spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String getImageAssetPath(String imageType, String image){
        return HOST + IMAGE_ASSET_PATH_SERVER + imageType + image;
    }
}
