package com.carpediemsolution.hotelsapp.utils;

import android.graphics.Bitmap;
import com.squareup.picasso.Transformation;

/**
 * Created by Юлия on 25.07.2017.
 */

public class ImageTransform implements Transformation {

    //to crop red frame 1px
    @Override
    public Bitmap transform(Bitmap source) {

        int x = source.getWidth() - 2;
        int y = source.getHeight() - 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, 1, 1, x, y);


        if (!squaredBitmap.equals(source)) {
            source.recycle();
        }
        return squaredBitmap;
    }

    @Override
    public String key() {
        return "rectangle";
    }
}