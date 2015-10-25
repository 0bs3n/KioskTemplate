package net.androidengineer.kiosktemplate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import net.androidengineer.kiosktemplate.fragments.ItemFragment;
import net.androidengineer.kiosktemplate.fragments.NavigationDrawerFragment;
import net.androidengineer.kiosktemplate.fragments.TopperFragment;
import net.androidengineer.kiosktemplate.objects.ProductItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by James Campbell. All rights reserved.
 */

//TODO: Custom Nav List Icons
//TODO: Template Logo
//TODO: Information Content Refinement
//TODO: Section Images

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TopperFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener {

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    NavigationDrawerFragment mNavigationDrawerFragment;
    TopperFragment mTopperFragment;
    ItemFragment mItemFragment;
    Handler handler;
    Runnable runnable;

    private ArrayList<ProductItem> productItemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupKioskState();
        setContentView(R.layout.activity_main);
        setContentFragments();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();//stop first and then start
        startHandler();
    }

    @Override
    public void onBackPressed() {
        // nothing to do here
        // … kiosk mode app
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startLockTask();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(String juiceType, String juiceBrand) {
        TopperFragment.imageViewTopper.clearAnimation();
        TopperFragment.textViewTopper.clearAnimation();
        if (juiceType.equals("Artesian")) {
            setItemFragment(juiceType, juiceBrand);
        } else if (juiceType.equals("Premium")) {
            setItemFragment(juiceType, juiceBrand);
        } else {
            //Nothing To See Here. Move Along.
        }
    }

    @Override
    public void onFragmentTopperInteraction(String textTopper) {

    }

    @Override
    public void onFragmentItemInteraction(String string) {

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void setContentFragments() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mTopperFragment = (TopperFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_topper);
        TopperFragment.imageViewTopper.setAnimation(animation);
        TopperFragment.textViewTopper.setAnimation(animation);

        TopperFragment.imageViewTopper.startAnimation(animation);
        TopperFragment.textViewTopper.startAnimation(animation);

        mItemFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_item);

        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                NavigationDrawerFragment.mDrawerLayout.closeDrawers();
                TopperFragment.imageViewTopper.startAnimation(animation);
                TopperFragment.textViewTopper.startAnimation(animation);
                mTopperFragment.setRefreshedText();
                mTopperFragment.setRefreshLogo();
                ItemFragment.relativeLayout.setVisibility(View.INVISIBLE);

            }
        };
        startHandler();
    }

    private void setupKioskState() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
    }

    private void setupProductListView(String brand) {
        productItemArrayList.clear();
        String csvFile = null;
        csvFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                + getString(R.string.products_file);
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] result = line.split(cvsSplitBy);
                if (result[5].equals(brand)) {
                    productItemArrayList.add(new ProductItem(result[0],
                            result[1],
                            result[2],
                            result[3],
                            result[4],
                            result[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setItemFragment(String juiceType, String juiceBrand) {
        mTopperFragment.setText(juiceBrand);
        Bitmap bitmap = BitmapFactory.decodeFile(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        + getString(R.string.images_directory_path) + juiceBrand.toLowerCase().replaceAll(" ", "") + ".bmp"
        );
        mTopperFragment.setImageViewTopper(bitmap);
        //Pass to List Fragment
        setupProductListView(juiceBrand);
        mItemFragment.setupProductList(juiceType, productItemArrayList);
    }

    public void stopHandler() {
        handler.removeCallbacks(runnable);
    }

    public void startHandler() {
        handler.postDelayed(runnable, 60000);
    }

}
