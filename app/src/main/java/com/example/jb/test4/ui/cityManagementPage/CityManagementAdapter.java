package com.example.jb.test4.ui.cityManagementPage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jb.test4.util.LoadIconUtil;
import com.example.jb.test4.R;
import com.example.jb.test4.db.City;

import java.util.ArrayList;
import java.util.List;

public class CityManagementAdapter extends RecyclerView.Adapter<CityManagementAdapter.ViewHolder> {

    private List<City> lists;
    private List<Integer> positionList;
    private List<City> listsTemp;
    private boolean isEdit;
    private boolean isEditDetermine;
    private boolean isCancle;
    private List<Long> deleteIdTemp;
    private List<Long> hasDeleteId;

    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView street_tv;
        TextView district_tv;
        TextView temperture_tv;
        TextView aqi_tv;
        TextView humidity_tv;
        TextView wind_tv;
        TextView maxNMin_tv;
        ImageView icon_iv;
        ImageView location_tv;
        LinearLayout right_layout;
        View line;
        LinearLayout bottom_layout;
        ImageView delete_iv;
        ImageView handle_iv;
        LinearLayout cityItem_layout;



        public ViewHolder(View itemView) {
            super(itemView);
            street_tv = itemView.findViewById(R.id.cityItem_street_tv);
            district_tv = itemView.findViewById(R.id.cityItem_districtNcty_tv);
            temperture_tv = itemView.findViewById(R.id.cityItem_temperture_tv);
            aqi_tv = itemView.findViewById(R.id.cityItem_aqi_tv);
            humidity_tv = itemView.findViewById(R.id.cityItem_humidity_tv);
            wind_tv = itemView.findViewById(R.id.cityItem_windLevel_tv);
            maxNMin_tv = itemView.findViewById(R.id.cityItem_maxNmin_tv);
            icon_iv = itemView.findViewById(R.id.cityItem_icon_iv);
            location_tv = itemView.findViewById(R.id.cityItem_location_im);
            right_layout = itemView.findViewById(R.id.cityItem_right);
            line = itemView.findViewById(R.id.cityItem_line);
            bottom_layout = itemView.findViewById(R.id.cityItem_bottom);
            delete_iv = itemView.findViewById(R.id.cityItem_delete_iv);
            handle_iv = itemView.findViewById(R.id.cityItem_handle_iv);
            cityItem_layout = itemView.findViewById(R.id.cityItem_layout);
        }
    }

    public CityManagementAdapter(List<City> lists) {
        this.lists = lists;
        isEdit = false;
        positionList = new ArrayList<>();
        listsTemp = new ArrayList<>();
        deleteIdTemp = new ArrayList<>();
        hasDeleteId = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = lists.get(position);
        holder.street_tv.setText(city.getStreet());
        holder.district_tv.setText(city.getDistrict() + "," + city.getCity());
        holder.temperture_tv.setText(city.getTemperture() + "℃");
        holder.aqi_tv.setText(city.getAqiQuality());
        holder.humidity_tv.setText("湿度" + city.getHumidity());
        holder.wind_tv.setText(city.getWindDirect() + city.getWindLevel());
        holder.maxNMin_tv.setText(city.getMaxTm() + "/" + city.getMinTm() + "℃");
        holder.icon_iv.setImageResource(LoadIconUtil.skycon(city.getWeatherText()));

        //列表子项点击跳转到相应的Fragment;
        holder.cityItem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(position);
            }
        });

        if (!city.isLocation()) {
            holder.location_tv.setVisibility(View.GONE);
            holder.street_tv.setText(city.getDistrict());
            holder.district_tv.setText(city.getDistrict() + "," + city.getCity());
        } else {
            holder.street_tv.setText(city.getStreet());
            holder.district_tv.setText(city.getDistrict() + "," + city.getCity());
        }
        if (city.getDistrict().equals(city.getCity())) {
            if (city.getCity().equals(city.getProvince())) {
                holder.district_tv.setText("中国");
            } else {
                holder.district_tv.setText(city.getProvince());
            }
        }


        if (isEdit){
            editLayout(holder);
            holder.delete_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除的子项数据和位置暂存；
                    listsTemp.add(city);
                    deleteIdTemp.add(city.getId());
                    hasDeleteId.add(city.getId());
                    positionList.add(position);
                    lists.remove(position);

                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });
        }
        if (isCancle){
            normalLayout(holder);
            positionList.clear();
            listsTemp.clear();
            hasDeleteId.removeAll(deleteIdTemp);
            deleteIdTemp.clear();

        }

        if (isEditDetermine){
            normalLayout(holder);
            positionList.clear();
            listsTemp.clear();
            deleteIdTemp.clear();
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setEdit() {
        this.isCancle = false;
        this.isEditDetermine = false;
        this.isEdit = true;
    }

    public void setEditCancle(){
        this.isEdit = false;
        this.isEditDetermine = false;
        this.isCancle = true;
    }

    public void setEditDetermine(){
        this.isEdit = false;
        this.isEditDetermine = true;
    }

    public List<Long> getdeleteIdTemp() {
        return deleteIdTemp;
    }
    public List<Integer> getDeletePositionTemp(){ return positionList;}
    public List<Long> getHasDeleteId(){return hasDeleteId;}
    public List<City> getListsTemp(){return listsTemp;}

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    private void editLayout(ViewHolder holder){
        holder.cityItem_layout.setClickable(false);
        holder.right_layout.setVisibility(View.GONE);
        holder.line.setVisibility(View.GONE);
        holder.bottom_layout.setVisibility(View.GONE);
        holder.delete_iv.setVisibility(View.VISIBLE);
        holder.handle_iv.setVisibility(View.VISIBLE);
    }
    private void normalLayout(ViewHolder holder){
        holder.cityItem_layout.setClickable(true);
        holder.right_layout.setVisibility(View.VISIBLE);
        holder.line.setVisibility(View.VISIBLE);
        holder.bottom_layout.setVisibility(View.VISIBLE);
        holder.delete_iv.setVisibility(View.GONE);
        holder.handle_iv.setVisibility(View.GONE);
    }


    public interface OnItemClickListener{
        public void onItemClickListener(int position);
    }

    public int getListSize(){
        return lists.size();
    }

}

