package com.company.graduationproject.stocktakingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by Nizam on 9/21/2017.
 */

public class AutoCompleteAdapter3 extends ArrayAdapter<String> {

    Context c;
    ArrayList<String> values;



    public AutoCompleteAdapter3(Context context, int resource , ArrayList<String> requiredvalues) {
        super(context, resource);
        c=context;
        values=requiredvalues;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.auto_complete_custom_row, parent, false);

            }
            ArrayList<String> test = values;
            TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvRequiredValueAndID);

            if (txtCustomer != null) {
                txtCustomer.setTag(values.get(position).split("#")[0]);
                txtCustomer.setText(values.get(position).split("#")[1]);

            }
        }
        catch(Exception e){
            Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }


    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint != null) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = values;
                filterResults.count = values.size();
                return filterResults;
            } else {
                return new FilterResults();
            }


        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {

            if (results != null && results.count > 0) {


                for(int i=0;i<values.size();i++){
                    add(values.get(i));
                    notifyDataSetChanged();
                }

            }
            else {
                clear();
                notifyDataSetChanged();
                //notifyDataSetInvalidated();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

}

