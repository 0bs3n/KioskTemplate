package net.androidengineer.kiosktemplate.adapters;

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
import net.androidengineer.kiosktemplate.objects.ProductItem;

import java.util.List;

//import android.view.LayoutInflater;

public class ProductAdapter extends BaseAdapter {
    Context context;
    List<ProductItem> productItems;
    //private LayoutInflater vi;

    public ProductAdapter(Context context, List<ProductItem> productItems) {
        this.context = context;
        this.productItems = productItems;
    }

    /**
     * Method get count of category list
     */
    @Override
    public int getCount() {
        return productItems.size();
    }

    /**
     * Method get item position
     */
    @Override
    public Object getItem(int position) {
        return productItems.get(position);
    }

    /**
     * Method get item id based on position
     */
    @Override
    public long getItemId(int position) {
        return productItems.indexOf(getItem(position));
    }

    /**
     * Method to get custom adapter view initialized
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_product, null);

        }
        TextView jNumber = (TextView) convertView.findViewById(R.id.textViewSKU);
        TextView jName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView jPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
        TextView jDescription = (TextView) convertView.findViewById(R.id.textViewDescription);


        ProductItem currentProduct = productItems.get(position);

        // Add received info to UI
        jName.setText(currentProduct.getName());
        jPrice.setText(currentProduct.getPrice());
        jNumber.setText(currentProduct.getSKU());
        jDescription.setText(currentProduct.getDescription());

        return convertView;
    }


}
