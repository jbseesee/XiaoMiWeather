package com.example.jb.test4.ui.addCityPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jb.test4.R;
import com.example.jb.test4.db.PopularCity;
import com.example.jb.test4.util.Util;

import java.util.List;

public class MyLayoutAdapter extends ArrayAdapter<PopularCity> {

    private int resoureId;


    private OnItemClickListener onItemClickListener;
    private int c;//用于addFragment打开一次只能点击一次添加城市。。。


    public MyLayoutAdapter(@NonNull Context context, int resource, @NonNull List<PopularCity> popularCityList) {
        super(context, resource, popularCityList);
        resoureId = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PopularCity popularCity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resoureId,parent,false);
        RelativeLayout layout = view.findViewById(R.id.popularCity_item_layout);
        TextView textView = view.findViewById(R.id.popularCity_item_tv);
        ImageView imageView = view.findViewById(R.id.popularCity_item_iv);
        textView.setText(popularCity.getCity());
        //子项点击事件；
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.isNetworkConnected()) {
                    if (c == 0) {
                        if (popularCity.getCity().equals("定位")) {
                            if (imageView.getVisibility() == View.GONE) {
                                textView.setText("定位中");
                            }
                            imageView.setVisibility(View.VISIBLE);
                        }
                        onItemClickListener.onItemClickListener(position, v);
                        if (textView.getCurrentTextColor() == 0xff000000) {
                            c++;
                        }
                        textView.setTextColor(0xff35abfe);
                    }
                } else {
                    onItemClickListener.onItemClickListener(position, v);
                }

            }
        });
        //热门地区的天气是否已经添加，已添加的字体设置为蓝色；
        if (popularCity.getCity().equals("定位") && popularCity.isAdded()) {
            textView.setTextColor(0xff35abfe);
            imageView.setVisibility(View.VISIBLE);
        }else if (popularCity.isAdded()) {
            textView.setTextColor(0xff35abfe);
        }


        return view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClickListener(int position, View view);
    }

}
