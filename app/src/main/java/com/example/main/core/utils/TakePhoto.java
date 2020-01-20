package com.example.main.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

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
        return image;
    }

    public void dispatchTakePictureIntent(Context context, Activity activity, int requestCode, String imageFileName) {
        Intent takePictureIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(imageFileName);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                mCurrentPhotoUri = FileProvider.getUriForFile(context,
                        Constant.APPLICATION_ID + ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                activity.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    public String getmCurrentPhotoName(){
        return mCurrentPhotoName;
    }

    public Uri getmCurrentPhotoUri(){
        return mCurrentPhotoUri;
    }
}
