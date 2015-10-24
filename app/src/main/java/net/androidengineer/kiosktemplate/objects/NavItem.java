package net.androidengineer.kiosktemplate.objects;

import android.graphics.Bitmap;

/**
 * Created by Human on 7/20/2015.
 */
public class NavItem {
    Bitmap bitmap;
    String name;

    public NavItem(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public NavItem(String name) {
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
