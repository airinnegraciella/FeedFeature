package com.example.main.core.utils;

import android.os.Environment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constant {
    public static final String APPLICATION_ID = "com.anagata.hris";
    public static final String SP_APP = "spApp";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_USER_ID = "spUserId";
    public static final String SP_EMPLOYEE_ID = "spEmployeeId";
    public static final String SP_IS_LOGGED_IN = "spIsLoggedIn";
    private static final String HOST = "http://hris.anagata.co.id";

    public static final int GALLERY_REQUEST_CODE = 9996;
    public static final int CAMERA_REQUEST_CODE = 9995;

    //Image
    public static final String IMAGE_TYPE_EMPLOYEE = "employees/";
    public static final String IMAGE_TYPE_COMPANY_INFO = "company_info/";
    public static final String IMAGE_TYPE_FEED = "feed/";
    public static final String IMAGE_TYPE_CLAIM = "claim/";
    private static final String IMAGE_ASSET_PATH_SERVER = "/assets/images/";

    public static String getHOST() {
        return HOST;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    private static final String BASE_URL = "http://hris.anagata.co.id/indexapi.php/";

    public static String convertDateTimeToFullDateDay(String date) {
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

    public static String getImageAssetPath(String imageType, String image) {
        return HOST + IMAGE_ASSET_PATH_SERVER + imageType + image;
    }

    //Photo Directory
    public static File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(), "Camera");

}
