package net.androidengineer.kiosktemplate.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.androidengineer.kiosktemplate.R;
import net.androidengineer.kiosktemplate.adapters.ArtesianAdapter;
import net.androidengineer.kiosktemplate.adapters.PremiumAdapter;
import net.androidengineer.kiosktemplate.objects.ArtesianBlend;
import net.androidengineer.kiosktemplate.objects.PremiumJuice;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends ListFragment {
    public static RelativeLayout relativeLayout;
    String mJuiceType;
    ArtesianAdapter artesianAdapter;
    PremiumAdapter premiumAdapter;
    OnFragmentInteractionListener mListener;
    private List<ArtesianBlend> mArtesianItemList;
    private ArrayList<PremiumJuice> mPremiumItemList;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null, false);
        mListener = (OnFragmentInteractionListener) getActivity();
        relativeLayout = (RelativeLayout) view.findViewById(R.id.listViewContainer);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setupArtesianList(String artesian, List<ArtesianBlend> artesianBlends) {
        relativeLayout.setVisibility(View.VISIBLE);
        mJuiceType = artesian;
        mArtesianItemList = artesianBlends;
        artesianAdapter = new ArtesianAdapter(getActivity(), mArtesianItemList);
        setListAdapter(artesianAdapter);
    }

    public void setupPremiumList(String premium, ArrayList<PremiumJuice> premiumJuices) {
        relativeLayout.setVisibility(View.VISIBLE);
        mJuiceType = premium;
        mPremiumItemList = premiumJuices;
        premiumAdapter = new PremiumAdapter(getActivity(), mPremiumItemList);
        setListAdapter(premiumAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentItemInteraction(String string);
    }

}
