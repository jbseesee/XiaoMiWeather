package com.example.jb.test4.ui.addCityPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jb.test4.R;
import com.example.jb.test4.db.CityList;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<CityList> {

    private int resourceId;
    private List<CityList> resultList;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<CityList> objects) {
        super(context, resource, objects);
        resourceId = resource;
        resultList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CityList cityList = getItem(position);
        String district = cityList.getDistrict();
        String cityName = cityList.getCity();
        String province = cityList.getProvince();
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view = convertView;
        }
        TextView tv = view.findViewById(R.id.tv);
        TextView hasExistedT = view.findViewById(R.id.hasExisted_tv);
        hasExistedT.setVisibility(View.GONE);
        tv.setText(district + "-" + cityName + "," + province + "," + "中国");

        if (cityList.isAdded()) {
            hasExistedT.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
