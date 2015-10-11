package net.androidengineer.kiosktemplate.artesianblends;

/**
 * Created by James Campbell for exclusive use by The Vape Queen. All rights reserved.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.androidengineer.kiosktemplate.R;

import java.util.List;

//import android.view.LayoutInflater;

public class ArtesianAdapter extends BaseAdapter {
    Context context;
    List<ArtesianBlend> juiceItems;
    //private LayoutInflater vi;

    public ArtesianAdapter(Context context, List<ArtesianBlend> juiceItems) {
        this.context = context;
        this.juiceItems = juiceItems;
    }

    /**
     * Method get count of category list
     */
    @Override
    public int getCount() {
        return juiceItems.size();
    }

    /**
     * Method get item position
     */
    @Override
    public Object getItem(int position) {
        return juiceItems.get(position);
    }

    /**
     * Method get item id based on position
     */
    @Override
    public long getItemId(int position) {
        return juiceItems.indexOf(getItem(position));
    }

    /**
     * Method to get custom adapter view initialized
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_artesian_blend, null);

        }
        TextView jNumber = (TextView) convertView.findViewById(R.id.textViewHouseNumber);
        TextView jName = (TextView) convertView.findViewById(R.id.textViewHouseName);
        TextView jVG = (TextView) convertView.findViewById(R.id.textViewHouseVG);
        TextView jPG = (TextView) convertView.findViewById(R.id.textViewHousePG);
        TextView jDescription = (TextView) convertView.findViewById(R.id.textViewHouseDescription);

        ArtesianBlend currentJuice = juiceItems.get(position);

        // Add received info to UI
        jNumber.setText(currentJuice.getVqNumber());
        jName.setText(currentJuice.getVqName());
        jVG.setText(currentJuice.getVqVGratio());
        jPG.setText(currentJuice.getVqPGratio());
        jDescription.setText(currentJuice.getVqDescription());

        return convertView;
    }

    public void refreshArtesianList(List<ArtesianBlend> list) {
        this.juiceItems = list;
        notifyDataSetChanged();
    }




}
