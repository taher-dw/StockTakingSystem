package com.company.graduationproject.stocktakingsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by Nizam on 9/21/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<DB.StockTakingTransactions> values;
    Context c;
    InventoryTableActivity inventoryTableA;

    public RecyclerViewAdapter(Context context, List<DB.StockTakingTransactions> v, InventoryTableActivity ita) {

        values = v;
        c = context;
        inventoryTableA = ita;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.recycler_view_row, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("updateMode", "1");
                    returnIntent.putExtra("itemID", viewHolder.tvItemName.getTag().toString().trim());
                    returnIntent.putExtra("quantity", viewHolder.tvItemQuantity.getText().toString().trim());
                    returnIntent.putExtra("unitID", viewHolder.tvInventoryUnit.getTag().toString().trim());
                    inventoryTableA.setResult(Activity.RESULT_OK, returnIntent);
                    inventoryTableA.finish();
                } catch (Exception e) {
                    Util.logException(c , e);
                    //  Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
                }



            }
        });


        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvItemName.setTag("" + values.get(position).itemID);
        holder.tvItemName.setText("" + values.get(position).itemName);
        holder.tvItemName.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.tvItemQuantity.setText("" + values.get(position).quantity);
        holder.tvItemQuantity.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.tvInventoryUnit.setTag("" + values.get(position).unitID);
        holder.tvInventoryUnit.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.tvInventoryUnit.setText("" + values.get(position).unitName);

    }

    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvItemName;
        public TextView tvItemQuantity;
        public TextView tvInventoryUnit;

        public ViewHolder(View v) {
            super(v);
            tvItemName = (TextView) v.findViewById(R.id.tvInventoryTableItemName);
            tvItemQuantity = (TextView) v.findViewById(R.id.tvInventoryTableItemQuantity);
            tvInventoryUnit = (TextView) v.findViewById(R.id.tvInventoryTableUnit);

        }
    }
}
