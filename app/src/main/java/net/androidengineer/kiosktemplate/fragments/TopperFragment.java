package net.androidengineer.kiosktemplate.fragments;

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
        mListenerFragmentTopper = (OnFragmentInteractionListener) getActivity();

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
    public void onDetach() {
        super.onDetach();
        mListenerFragmentTopper = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentTopperInteraction(String textTopper);
    }

    private void setInitialLogo(){
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Download/logo.png");
        imageViewTopper.setImageBitmap(bitmap);
    }

    public void setImageViewTopper(Bitmap bitmap){
        imageViewTopper.setImageBitmap(bitmap);
    }

    private void setInitialText(){
        String csvFile = getString(R.string.information_file);
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

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
        textViewTopper.setText(line);
    }

    public void setText(String juiceBrand){
        textViewTopper.setText(juiceBrand);
    }


}
