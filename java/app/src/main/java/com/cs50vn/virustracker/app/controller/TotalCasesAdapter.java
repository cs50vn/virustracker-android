package com.cs50vn.virustracker.app.controller;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppUtils;

import java.util.ArrayList;
import java.util.LinkedList;

public class TotalCasesAdapter extends RecyclerView.Adapter<TotalCasesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private TextView tv1;

        public ViewHolder(View view) {
            super(view);

            tv = view.findViewById(R.id.component_item);
            tv1 = view.findViewById(R.id.component_percent);
        }
    }

    private Context ctx;
    private LinkedList<Continent> list;
    private ArrayList<Integer> colors;

    public TotalCasesAdapter(Context ctx, LinkedList<Continent> list, ArrayList<Integer> colors) {
        this.ctx = ctx;
        this.list = list;
        this.colors = colors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_totalcases_recyclerview_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PLog.WriteLog(PLog.MAIN_TAG, "news clicked");
                //AppRepository.getInstance().ctx.switchNewsFragment();
            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null) {

            Continent item = list.get(position);

            TextView tv = holder.tv;


            Drawable left = ctx.getResources().getDrawable(R.drawable.rect);
            //left.setTint(colors.get(position));
            //Compat with lower API 29
            left.setColorFilter(colors.get(position), PorterDuff.Mode.SRC_ATOP);
            //left.setColorFilter(colors.get(position));
            tv.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
            tv.setText(item.getName());

            TextView tv1 = holder.tv1;
            tv1.setText(AppUtils.convertPercent(item, list));
        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }
}
