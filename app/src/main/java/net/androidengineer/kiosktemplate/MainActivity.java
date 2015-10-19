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

import net.androidengineer.kiosktemplate.artesianblends.ArtesianBlend;
import net.androidengineer.kiosktemplate.fragments.ItemFragment;
import net.androidengineer.kiosktemplate.fragments.NavigationDrawerFragment;
import net.androidengineer.kiosktemplate.fragments.TopperFragment;
import net.androidengineer.kiosktemplate.premiumjuices.PremiumJuice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by James Campbell for exclusive use by The Vape Queen. All rights reserved.
 */
public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TopperFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener {

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    private final String TAG = "KioskMenu";
    NavigationDrawerFragment mNavigationDrawerFragment;
    TopperFragment mTopperFragment;
    ItemFragment mItemFragment;
    Handler handler;
    Runnable runnable;

    private ArrayList<ArtesianBlend> artesianBlendArrayList = new ArrayList<>();
    private ArrayList<PremiumJuice> premiumJuiceArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupKioskState();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        setExternalFolders();
        setExternalFiles();
        setContentFragments(toolbar, animation);

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
            setItemFragmentArtesianList(juiceType, juiceBrand);
        } else if (juiceType.equals("Premium")) {
            setItemFragmentPremiumList(juiceType, juiceBrand);
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

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setContentFragments(Toolbar toolbar, final Animation animation) {
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
                mTopperFragment.setInitialText();
                mTopperFragment.setInitialLogo();
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

    private void setupArtesianBrandList(String brand) {
        artesianBlendArrayList.clear();
        String csvFile = "/sdcard/Download/artesian_juices.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] _artesianblend = line.split(cvsSplitBy);
                if (_artesianblend[5].equals(brand)) {
                    artesianBlendArrayList.add(new ArtesianBlend(_artesianblend[0], _artesianblend[1], _artesianblend[2], _artesianblend[3], _artesianblend[4], _artesianblend[5]));
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

    private void setupPremiumBrandList(String brand) {
        premiumJuiceArrayList.clear();
        String csvFile = "/sdcard/Download/premium_juices.csv";
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] _premiumjuice = line.split(cvsSplitBy);
                if (_premiumjuice[5].equals(brand)) {
                    premiumJuiceArrayList.add(new PremiumJuice(_premiumjuice[0], _premiumjuice[1], _premiumjuice[2], _premiumjuice[3], _premiumjuice[4], _premiumjuice[5]));
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

    private void setExternalFolders() {
        File mainfolder = new File(Environment.getExternalStorageDirectory(), TAG);
        if (!mainfolder.exists()) {
            mainfolder.mkdirs();
        }
        File imagesfolder = new File(Environment.getExternalStorageDirectory() + "/" + TAG, "Images");
        if (!imagesfolder.exists()) {
            imagesfolder.mkdirs();
        }
        File filesfolder = new File(Environment.getExternalStorageDirectory() + "/" + TAG, "Files");
        if (!filesfolder.exists()) {
            filesfolder.mkdirs();
        }
    }

    private void setItemFragmentArtesianList(String juiceType, String juiceBrand) {
        mTopperFragment.setText(juiceBrand);
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Download/logo.png");
        mTopperFragment.setImageViewTopper(bitmap);
        //Pass to List Fragment
        setupArtesianBrandList(juiceBrand);
        mItemFragment.setupArtesianList(juiceType, artesianBlendArrayList);
    }

    private void setItemFragmentPremiumList(String juiceType, String juiceBrand) {
        mTopperFragment.setText(juiceBrand);
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Download/" + juiceBrand.toLowerCase().replaceAll(" ", "") + ".bmp");
        mTopperFragment.setImageViewTopper(bitmap);
        //Pass to List Fragment
        setupPremiumBrandList(juiceBrand);
        mItemFragment.setupPremiumList(juiceType, premiumJuiceArrayList);
    }

    private void setExternalFiles() {
        InputStream inputStream = getResources().openRawResource(R.raw.navigation_header_images);
        CSVFile csvFile = new CSVFile(inputStream);
        ArrayList<String> dataList = csvFile.readSimpleList();
        // save csv file on SDCard
        try {
            FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory() + getString(R.string.bitmap_list_path));
            for (int i = 0; i < dataList.size(); i++) {
                writer.append(dataList.get(i) + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputStream = getResources().openRawResource(R.raw.artesian_categories);
        csvFile = new CSVFile(inputStream);
        dataList = csvFile.readSimpleList();
        // save csv file on SDCard
        try {
            FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory() + getString(R.string.artesian_categories_file));
            for (int i = 0; i < dataList.size(); i++) {
                writer.append(dataList.get(i) + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputStream = getResources().openRawResource(R.raw.artesian_juices);
        csvFile = new CSVFile(inputStream);
        artesianBlendArrayList.clear();
        artesianBlendArrayList = csvFile.readCategory1Array();
        // save csv file on SDCard
        try {
            FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory() + getString(R.string.artesian_juice_file));
            for (int i = 0; i < artesianBlendArrayList.size(); i++) {
                writer.append(artesianBlendArrayList.get(i).getVqNumber() + ","
                                + artesianBlendArrayList.get(i).getVqName() + ","
                                + artesianBlendArrayList.get(i).getVqVGratio() + ","
                                + artesianBlendArrayList.get(i).getVqDescription() + ","
                                + artesianBlendArrayList.get(i).getVqCategory() + "\n"
                );
            }
            writer.flush();
            writer.close();
            artesianBlendArrayList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputStream = getResources().openRawResource(R.raw.premium_brands);
        csvFile = new CSVFile(inputStream);
        dataList = csvFile.readSimpleList();
        // save csv file on SDCard
        try {
            FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory() + getString(R.string.premium_brands_file));
            for (int i = 0; i < dataList.size(); i++) {
                writer.append(dataList.get(i) + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputStream = getResources().openRawResource(R.raw.premium_juices);
        csvFile = new CSVFile(inputStream);
        premiumJuiceArrayList.clear();
        premiumJuiceArrayList = csvFile.readCategory2Array();
        // save csv file on SDCard
        try {
            FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory() + getString(R.string.premium_juice_file));
            for (int i = 0; i < premiumJuiceArrayList.size(); i++) {
                writer.append(premiumJuiceArrayList.get(i).getPjImageFilePath() + ","
                                + premiumJuiceArrayList.get(i).getPjName() + ","
                                + premiumJuiceArrayList.get(i).getPjVGratio() + ","
                                + premiumJuiceArrayList.get(i).getPjPGratio() + ","
                                + premiumJuiceArrayList.get(i).getPjManufacturer() + "\n"
                );
            }
            writer.flush();
            writer.close();
            premiumJuiceArrayList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopHandler() {
        handler.removeCallbacks(runnable);
    }

    public void startHandler() {
        handler.postDelayed(runnable, 60000);
    }

}
