package net.androidengineer.kiosktemplate.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import net.androidengineer.kiosktemplate.R;
import net.androidengineer.kiosktemplate.navigation.JuiceNavAdapter;
import net.androidengineer.kiosktemplate.navigation.JuiceNavItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    NavigationDrawerCallbacks mCallbacks;
    public static DrawerLayout mDrawerLayout;
    View mFragmentContainerView;
    int mCurrentSelectedPosition = 0;
    boolean mFromSavedInstanceState;
    boolean mUserLearnedDrawer;
    ListView mDrawerArtesianListView;
    ListView mDrawerPremiumListView;
    List<JuiceNavItem> artesianNavJuice = new ArrayList<>();
    List<JuiceNavItem> premiumNavJuice = new ArrayList<>();
    String _mNavFragType;
    String _mNavFragBrand;
    private ActionBarDrawerToggle mDrawerToggle;

    public NavigationDrawerFragment() {
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, preferenceValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        assert v != null;

        mCallbacks = (NavigationDrawerCallbacks) getActivity();

        setupArtesianCategoryList();
        mDrawerArtesianListView = (ListView) v.findViewById(R.id.listviewArtesianCategories);
        mDrawerArtesianListView.setAdapter(new JuiceNavAdapter(artesianNavJuice, inflater));
        mDrawerArtesianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _mNavFragType = "Artesian";
                _mNavFragBrand = ((TextView) view.findViewById(R.id.textViewNavItem)).getText().toString();
                mCallbacks.onNavigationDrawerItemSelected(_mNavFragType, _mNavFragBrand);
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
        });

        setupPremiumBrandList();
        mDrawerPremiumListView = (ListView) v.findViewById(R.id.listviewPremiumBrands);
        mDrawerPremiumListView.setAdapter(new JuiceNavAdapter(premiumNavJuice, inflater));
        mDrawerPremiumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _mNavFragType = "Premium";
                _mNavFragBrand = ((TextView) view.findViewById(R.id.textViewNavItem)).getText().toString();
                mCallbacks.onNavigationDrawerItemSelected(_mNavFragType, _mNavFragBrand);
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
        });

        //setViewFlipper(v);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    private void setViewFlipper(View v) {
        ViewFlipper mViewFlipper = (ViewFlipper) v.findViewById(R.id.nav_header_flipper);
        ArrayList<String> arrayListImageNames = getBitmapList();
        for (int i = 0; i < arrayListImageNames.size(); i++) {
            Bitmap mBitmap = getBitmap(arrayListImageNames.get(i));
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageBitmap(mBitmap);
            mViewFlipper.addView(imageView);
        }
        System.gc();
        Runtime.getRuntime().gc();

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(3500);
        mViewFlipper.startFlipping();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, String.valueOf(true));
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener


        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    private void setupArtesianCategoryList() {
        artesianNavJuice.clear();
        String csvFile = getString(R.string.artesian_categories_file);
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] _artesianjuice = line.split(cvsSplitBy);
                artesianNavJuice.add(new JuiceNavItem(BitmapFactory.decodeResource(getResources(), R.drawable.logo_thumbnail), _artesianjuice[0]));
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

    private void setupPremiumBrandList() {
        premiumNavJuice.clear();
        String csvFile = getString(R.string.premium_brands_file);
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] _premiumjuice = line.split(cvsSplitBy);
                premiumNavJuice.add(new JuiceNavItem(BitmapFactory.decodeResource(getResources(), R.drawable.premium_thumbnail), _premiumjuice[0]));
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

    private Bitmap getBitmap(String filename) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(getString(R.string.download_directory_path) + filename, options);
        Bitmap scaledBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);

        System.gc();
        Runtime.getRuntime().gc();

        return scaledBitmap;
    }

    private ArrayList<String> getBitmapList() {
        ArrayList<String> arrayListBitmap = new ArrayList<>();
        String csvFile = Environment.getExternalStorageDirectory() + getString(R.string.bitmap_list_path);
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                arrayListBitmap.add(line);
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
        return arrayListBitmap;
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(String type, String brand);
    }

}




