package com.example.main.core.utils;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class TakePhoto {

    private String mCurrentPhotoName;
    private Uri mCurrentPhotoUri;

    private File createImageFile(String imageFileName) throws IOException {
        File storageDir = Constant.storageDir;

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        Log.d("PathImage", image.getAbsolutePath());
        String mCurrentPhotoPath = image.getAbsolutePath();
//        mCurrentPhotoPath = mCurrentPhotoPath.split("/".
        return null;
    }
}
