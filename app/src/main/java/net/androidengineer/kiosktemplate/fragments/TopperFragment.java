package net.androidengineer.kiosktemplate.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.androidengineer.kiosktemplate.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class TopperFragment extends Fragment {

    public static ImageView imageViewTopper;
    public static TextView textViewTopper;
    private OnFragmentInteractionListener mListenerFragmentTopper;

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


        imageViewTopper = (ImageView) view.findViewById(R.id.imageViewMain);
        setInitialLogo();


        textViewTopper = (TextView) view.findViewById(R.id.textViewMain);
        setInitialText();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListenerFragmentTopper = null;
    }

    private void setInitialLogo() {
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/Download/logo.png");
        imageViewTopper.setImageBitmap(bitmap);
    }

    public void setImageViewTopper(Bitmap bitmap) {
        imageViewTopper.setImageBitmap(bitmap);
    }

    private void setInitialText() {
        textViewTopper.setText(readTextFile(getString(R.string.information_file)));
    }

    /*
     * Read a text file.
     */
    public String readTextFile(String actualFile) {

        String contents = String.valueOf("");

        try {

            // Get the text file
            File file = new File(actualFile);

            // check if file is not empty
            if (file.exists() && file.length() != 0) {

                // read the file to get contents
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    // store the text file line to contents variable
                    contents += (line + "\n");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contents;
    }

    public void setText(String juiceBrand) {
        textViewTopper.setText(juiceBrand);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentTopperInteraction(String textTopper);
    }


}
