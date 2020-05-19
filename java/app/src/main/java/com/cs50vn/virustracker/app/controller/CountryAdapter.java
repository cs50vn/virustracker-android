package com.cs50vn.virustracker.app.controller;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppUtils;

import java.util.ArrayList;
import java.util.LinkedList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private ImageView iv;
        private TextView tv1;
        private TextView tv2;
        private TextView tv3;
        private TextView tv4;


        public ViewHolder(View view) {
            super(view);

            tv = view.findViewById(R.id.component_country_id);
            iv = view.findViewById(R.id.component_country_flag);
            tv1 = view.findViewById(R.id.component_country_name);
            tv2 = view.findViewById(R.id.component_country_cases);
            tv3 = view.findViewById(R.id.component_country_new);
            tv4 = view.findViewById(R.id.component_country_deaths);
        }
    }

    private Context ctx;
    private LinkedList<Country> list;

    public CountryAdapter(Context ctx, LinkedList<Country> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_country_recyclerview_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLog.WriteLog(PLog.MAIN_TAG, "country clicked");
                //Go to country detail
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null) {
            Country item = list.get(position);

            TextView tv = holder.tv;
            tv.setText(String.valueOf(position + 1));

            ImageView iv = holder.iv;
            iv.setImageResource(R.drawable.it);

            TextView tv1 = holder.tv1;
            tv1.setText(item.getName());

            TextView tv2 = holder.tv2;
            tv2.setText(AppUtils.formatNumber(item.getItemList().get(0).getTotalCases()));

            TextView tv3 = holder.tv3;
            tv3.setText(AppUtils.formatNumber(item.getItemList().get(0).getNewCases()));

            TextView tv4 = holder.tv4;
            tv4.setText(AppUtils.formatNumber(item.getItemList().get(0).getTotalDeaths()));
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }
}
