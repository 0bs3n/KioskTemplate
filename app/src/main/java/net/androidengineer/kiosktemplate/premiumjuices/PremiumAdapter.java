package net.androidengineer.kiosktemplate.premiumjuices;

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

public class PremiumAdapter extends BaseAdapter {

    Context context;
    private List<PremiumJuice> juiceItems;

    public PremiumAdapter(Context context, List<PremiumJuice> juiceItems) {
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
            convertView = mInflater.inflate(R.layout.item_premium_juice, null);
        }

        TextView jName = (TextView) convertView.findViewById(R.id.textViewPremiumName);
        TextView jVG = (TextView) convertView.findViewById(R.id.textViewPremiumVG);
        TextView jPG = (TextView) convertView.findViewById(R.id.textViewPremiumPG);
        TextView jDescription = (TextView) convertView.findViewById(R.id.textViewPremiumDescription);

        PremiumJuice currentJuice = juiceItems.get(position);

        // Add received info to UI
        jName.setText(currentJuice.getPjName());
        jVG.setText(currentJuice.getPjVGratio());
        jPG.setText(currentJuice.getPjPGratio());
        jDescription.setText(currentJuice.getPjDescription());

        return convertView;
    }

    public void refreshDeviceList(List<PremiumJuice> list) {
        this.juiceItems = list;
        notifyDataSetChanged();
    }


}
