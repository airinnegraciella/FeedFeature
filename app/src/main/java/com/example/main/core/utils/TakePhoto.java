package com.example.main.core.utils;

import android.net.Uri;

import java.io.File;

public class TakePhoto {

    private String mCurrentPhotoName;
    private Uri mCurrentPhotoUri;

    private File createImageFile(String imageFileName){
        File storageDir = Constant.storageDir;

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
//
//        File image = File.createTempFile(
//                imageFileName,
//                ".jpg",
//        )
        return null;
    }
}
