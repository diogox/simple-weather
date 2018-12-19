package com.diogox.simpleweather.MenuRight;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diogox.simpleweather.Api.Models.Database.Cities.City;
import com.diogox.simpleweather.MainActivity;
import com.diogox.simpleweather.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class DrawerCityAdapter extends RecyclerView.Adapter<DrawerCityAdapter.DrawerCityViewHolder> {

    public Context context;
    private List<City> cityList;
    private ItemClickListener onItemClickListener;

    public DrawerCityAdapter(Context context, List<City> cityList, ItemClickListener onItemClickListener) {
        this.context = context;
        this.cityList = cityList;
        this.onItemClickListener = onItemClickListener;
    }

    public class DrawerCityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_city) TextView nameCity;
        @BindView(R.id.cityListItemParent) View itemParent;

        public DrawerCityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final City city, final ItemClickListener listener) {

            nameCity.setText(
                    city.getName()
            );

            itemParent.setOnClickListener(view -> Toast.makeText(context, city.getName(), Toast.LENGTH_LONG).show());
            itemParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(city);
                }
            });
        }
    }

    public void setData(List<City> newData) {
        this.cityList = newData;
        notifyDataSetChanged();
    }

    @Override
    public DrawerCityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // Get layout inflater from context
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // Inflate layout
        View drawerCityView = layoutInflater.inflate(R.layout.item_recycler_view_drawer_city_list, viewGroup, false);

        // Return a new holder instance
        return new DrawerCityViewHolder(drawerCityView);
    }

    @Override
    public void onBindViewHolder(DrawerCityViewHolder drawerCityViewHolder, int position) {

        drawerCityViewHolder.bind(cityList.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public interface ItemClickListener {
        void onItemClick(City city);
    }
}
