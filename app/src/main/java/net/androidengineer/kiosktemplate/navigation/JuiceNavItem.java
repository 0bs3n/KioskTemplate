package net.androidengineer.kiosktemplate.navigation;

import android.graphics.Bitmap;

/**
 * Created by Human on 7/20/2015.
 */
public class JuiceNavItem {
    Bitmap bitmap;
    String name;

    public JuiceNavItem(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public JuiceNavItem(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
