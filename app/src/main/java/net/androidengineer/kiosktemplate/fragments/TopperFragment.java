package net.androidengineer.kiosktemplate.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.androidengineer.kiosktemplate.R;


public class TopperFragment extends Fragment {

    private OnFragmentInteractionListener mListenerFragmentTopper;
    public static ImageView imageViewTopper;
    public static TextView textViewTopper;
    public Bitmap mBitmap;
    final Handler handler = new Handler();

    private String juiceType;
    private String juiceBrand;

    public TopperFragment newInstance(String juiceType, String juiceBrand) {
        TopperFragment fragment = new TopperFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TopperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topper, container, false);

        imageViewTopper = (ImageView)view.findViewById(R.id.imageViewMain);
        setInitialLogo();

        textViewTopper = (TextView)view.findViewById(R.id.textViewMain);
        setInitialText();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListenerFragmentTopper = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListenerFragmentTopper = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentTopperInteraction(String textTopper);
    }

    private void setInitialLogo(){
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Download/logo.png");
        imageViewTopper.setImageBitmap(bitmap);
    }

    public void setImageViewTopper(Bitmap bitmap){
        imageViewTopper.setImageBitmap(bitmap);
    }

    private void setInitialText(){
        textViewTopper.setText(getString(R.string.app_name));
    }

    public void setText(String juiceBrand){
        textViewTopper.setText(juiceBrand);
    }




}
