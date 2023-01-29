package com.example.exm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class image {

    Context mContext;

    public image(Context mContext) {
        this.mContext = mContext;
    }

    public Bitmap getUserImage(String encodedImg)
    {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return BitmapFactory.decodeResource(image.this.mContext.getResources(), R.drawable.absence);
    }
}
