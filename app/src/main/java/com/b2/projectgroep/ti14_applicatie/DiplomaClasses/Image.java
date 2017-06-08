package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.graphics.Bitmap;

/**
 * Created by dionb on 8-6-2017.
 */

public class Image {
    static Bitmap image = null;
    public static Bitmap getImage() {
        return image;
    }

    public static void setImage(Bitmap img) {
        image = img;
    }

    public static void deleteImage() {
        image = null;
    }
}
