package com.b2.projectgroep.ti14_applicatie.DiplomaClasses;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by dionb on 8-6-2017.
 */

public class Image {
    static Bitmap image = null;
    static Matrix matrix = null;
    public static Bitmap getImage() {
        return image;
    }

    public static void setImage(Bitmap img) {
        image = img;
    }

    public static Matrix getMatrix() {
        return matrix;
    }

    public static void setMatrix(Matrix matrix) {
        Image.matrix = matrix;
    }

    public static void deleteImage() {
        image = null;
    }
}
