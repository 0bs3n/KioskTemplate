package net.androidengineer.kiosktemplate.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.androidengineer.kiosktemplate.R;
import net.androidengineer.kiosktemplate.adapters.ProductAdapter;
import net.androidengineer.kiosktemplate.objects.ProductItem;

import java.util.ArrayList;


public class ItemFragment extends ListFragment {
    public static RelativeLayout relativeLayout;
    String section;
    ProductAdapter productAdapter;
    OnFragmentInteractionListener mListener;

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

    public void setupProductList(String section, ArrayList<ProductItem> productItems) {
        relativeLayout.setVisibility(View.VISIBLE);
        this.section = section;
        ArrayList<ProductItem> productItemArrayList = productItems;
        productAdapter = new ProductAdapter(getActivity(), productItemArrayList);
        setListAdapter(productAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentItemInteraction(String string);
    }

}
