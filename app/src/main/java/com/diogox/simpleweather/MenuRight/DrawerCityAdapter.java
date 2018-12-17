package com.diogox.simpleweather.MenuRight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.R;

import java.util.List;

public class DrawerCityAdapter extends RecyclerView.Adapter<DrawerCityAdapter.DrawerCityViewHolder> {


    public Context context;
    public List<City> cityList;

    public DrawerCityAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
    }


    public class DrawerCityViewHolder extends RecyclerView.ViewHolder {

        public TextView nameCity;

        public DrawerCityViewHolder(View itemView) {
            super(itemView);

            nameCity = itemView.findViewById(R.id.name_city);
        }

    }


    @Override
    public DrawerCityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // Get layout inflater from context
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // Inflate layout
        View drawerCityView = layoutInflater.inflate(R.layout.item_recycler_view_drawer_city_list, viewGroup, false);

        // Return a new holder instance
        return new DrawerCityAdapter.DrawerCityViewHolder(drawerCityView);
    }

    @Override
    public void onBindViewHolder(DrawerCityViewHolder drawerCityViewHolder, int position) {

        City city = cityList.get(position);

        TextView name = drawerCityViewHolder.nameCity;
        name.setText(
                city.getName()
        );
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

}
