package net.androidengineer.kiosktemplate.objects;

import android.graphics.Bitmap;

public class NavItem {
    Bitmap bitmap;
    String name;

    public NavItem(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
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

}
