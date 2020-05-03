package com.example.covid19.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.covid19.R;
import com.example.covid19.model.Country;
import com.example.covid19.ui.CustomItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public ImageView flag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.country_name);
            flag = itemView.findViewById(R.id.flag);
        }
    }

    public static List<Country> list;
    public static List<Country> filtered_list;
    private Context context;
    CustomItemClickListener listener;
    public SearchAdapter(List<Country> list, Context context, CustomItemClickListener listener) {
        this.list = list;
        this.filtered_list = list;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vr = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_layout, parent, false);
        final SearchAdapter.ViewHolder view_holder = new SearchAdapter.ViewHolder(vr);

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, view_holder.getPosition());
            }
        });

        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapter.ViewHolder holder, final int position) {
        holder.tv_name.setText(filtered_list.get(position).getCountry());


            Glide
                    .with(context)
                    .load("https://www.countryflags.io/" + filtered_list.get(position).getSlug() +"/flat/64.png" )
                    .centerCrop()
                    .placeholder(R.drawable.coronavirus)
                    .into(holder.flag);
    }

    @Override
    public int getItemCount() {
        return filtered_list.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filtered_list = list;
                } else {
                    List<Country> filteredList = new ArrayList<>();
                    for (Country row : list) {
                        if (row.getCountry().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filtered_list = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered_list;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtered_list = (ArrayList<Country>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

}

