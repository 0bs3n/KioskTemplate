package net.androidengineer.kiosktemplate.adapters;
/**
 * Created by James Campbell for exclusive use by The Vape Queen. All rights reserved.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.androidengineer.kiosktemplate.R;
import net.androidengineer.kiosktemplate.objects.JuiceNavItem;

import java.util.List;

public class JuiceNavAdapter extends BaseAdapter {

    private List<JuiceNavItem> juiceNavItems;
    private LayoutInflater vi;

    public JuiceNavAdapter(List<JuiceNavItem> juiceNavItems, LayoutInflater inflater) {
        this.juiceNavItems = juiceNavItems;
        vi = inflater;
    }

    /**
     * Method get count of category list
     */
    @Override
    public int getCount() {
        return juiceNavItems.size();
    }

    /**
     * Method get item position
     */
    @Override
    public Object getItem(int position) {
        return juiceNavItems.get(position);
    }

    /**
     * Method get item id based on position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Method to get custom adapter view initialized
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem itemAdapter;

        if (convertView == null) {
            convertView = vi.inflate(R.layout.item_navigation, null);
            itemAdapter = new ViewItem();
            itemAdapter.jImage = (ImageView) convertView.findViewById(R.id.imageViewNavItem);
            itemAdapter.jName = (TextView) convertView
                    .findViewById(R.id.textViewNavItem);
            convertView.setTag(itemAdapter);
        } else {
            itemAdapter = (ViewItem) convertView.getTag();
        }
        JuiceNavItem currentJuice = juiceNavItems.get(position);
        // Add received info to UI
        itemAdapter.jImage.setImageBitmap(currentJuice.getBitmap());
        itemAdapter.jName.setText(currentJuice.getName());

        return convertView;
    }

    public void refreshDeviceList(List<JuiceNavItem> list) {
        this.juiceNavItems = list;
        notifyDataSetChanged();
    }

    private class ViewItem {
        ImageView jImage;
        TextView jName;
    }


}






