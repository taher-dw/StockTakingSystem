package com.company.graduationproject.stocktakingsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Nizam on 9/21/2017.
 */

public class UndefinedBarcodeRecyclerViewAdapter extends RecyclerView.Adapter<UndefinedBarcodeRecyclerViewAdapter.ViewHolder> {

    List<DB.UndefinedStockTakingTransactions> values;
    Context c;
    InventoryTableActivity inventoryTableA;
    int colorsStatus[];
    String []itemsUnits;


    public UndefinedBarcodeRecyclerViewAdapter(Context context, List<DB.UndefinedStockTakingTransactions> v, InventoryTableActivity ita) {

        values = v;
        c = context;
        inventoryTableA = ita;
    }

    public UndefinedBarcodeRecyclerViewAdapter(Context applicationContext, List<DB.UndefinedStockTakingTransactions> ustts, int[] colors,String[] units ,InventoryTableActivity inventoryTableActivity) {
        values = ustts;
        c = applicationContext;
        inventoryTableA = inventoryTableActivity;
        colorsStatus = colors;
        itemsUnits = units;

    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.undefined_barcode_recyclerview_row, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("updateMode", "2");
                    returnIntent.putExtra("colorStatus", viewHolder.tvUndefinedItemBarcode.getTag().toString().trim().split("#")[0]);
                    //returnIntent.putExtra("undefinedItemBarcode", viewHolder.tvUndefinedItemBarcode.getTag().toString().trim().split("#")[1]);
                    returnIntent.putExtra("undefinedItemBarcode", viewHolder.tvUndefinedBarcodeQuantity.getTag().toString().trim());


                    //returnIntent.putExtra("undefinedItemBarcode", viewHolder.tvUndefinedItemBarcode.getText().toString().trim());
                    returnIntent.putExtra("undefinedBarcodeQuantity", viewHolder.tvUndefinedBarcodeQuantity.getText().toString().trim());
                    inventoryTableA.setResult(Activity.RESULT_OK, returnIntent);
                    inventoryTableA.finish();
                } catch (Exception e) {
                    Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
                }
                //    Toast.makeText(c, viewHolder.tvItemName.getText().toString(), Toast.LENGTH_SHORT).show();


            }
        });


        return viewHolder;
    }

    public void onBindViewHolder(UndefinedBarcodeRecyclerViewAdapter.ViewHolder holder, int position) {


        //holder.tvUndefinedItemBarcode.setText("" + values.get(position).itemBarcode.split("#")[0]);
        //holder.tvUndefinedItemBarcode.setTag(colorsStatus[position]+"#"+values.get(position).itemBarcode.split("#")[1]);
        holder.tvUndefinedItemBarcode.setText("" + values.get(position).undefinedBarcodeName.split("#")[0]);
        holder.tvUndefinedItemBarcode.setTag(colorsStatus[position]+"#"+values.get(position).undefinedBarcodeName.split("#")[1]);

        holder.tvUndefinedBarcodeQuantity.setText("" + values.get(position).quantity);
        holder.tvUndefinedBarcodeQuantity.setTag("" + values.get(position).itemBarcode);
        holder.tvUndefinedBarcodeUnit.setText(itemsUnits[position]);
        if(colorsStatus[position]==1){
            holder.tvUndefinedItemBarcode.setTextColor(Color.WHITE);
            holder.tvUndefinedItemBarcode.setBackgroundColor(Color.parseColor("#31c831"));// original was 228B22
            holder.tvUndefinedBarcodeQuantity.setTextColor(Color.WHITE);
            holder.tvUndefinedBarcodeQuantity.setBackgroundColor(Color.parseColor("#31c831"));
            holder.tvUndefinedBarcodeUnit.setTextColor(Color.WHITE);
            holder.tvUndefinedBarcodeUnit.setBackgroundColor(Color.parseColor("#31c831"));


        }
        else {
            holder.tvUndefinedItemBarcode.setTextColor(Color.WHITE);
            holder.tvUndefinedItemBarcode.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.tvUndefinedBarcodeQuantity.setTextColor(Color.WHITE);
            holder.tvUndefinedBarcodeQuantity.setBackgroundColor(Color.parseColor("#FF0000"));
            holder.tvUndefinedBarcodeUnit.setTextColor(Color.WHITE);
            holder.tvUndefinedBarcodeUnit.setBackgroundColor(Color.parseColor("#FF0000"));


        }

    }

    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvUndefinedItemBarcode;
        public TextView tvUndefinedBarcodeQuantity;
        public TextView tvUndefinedBarcodeUnit;


        public ViewHolder(View v) {
            super(v);
            tvUndefinedItemBarcode = (TextView) v.findViewById(R.id.tvUndefinedInventoryTableItemBarcode);
            tvUndefinedBarcodeQuantity = (TextView) v.findViewById(R.id.tvUndefinedInventoryTableItemQuantity);
            tvUndefinedBarcodeUnit = (TextView) v.findViewById(R.id.tvUndefinedInventoryTableItemUnit);

        }
    }
}

