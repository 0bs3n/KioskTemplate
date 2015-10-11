package net.androidengineer.kiosktemplate.kioskservices;

/**
 * Created by James Campbell for exclusive use by The Vape Queen. All rights reserved.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.androidengineer.kiosktemplate.MainActivity;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(myIntent);
    }
}
