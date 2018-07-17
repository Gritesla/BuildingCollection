package com.hdsoft.buildingcollection.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hdsoft.buildingcollection.R;
import com.hdsoft.buildingcollection.entity.BuildingEntity;

import java.util.List;

public class FormListAdapter extends BaseAdapter {

    private List<BuildingEntity> dataList;
    private LayoutInflater inflater;
    private ListView lv;
    private String flag;
    private Context ctx;

    public FormListAdapter(List<BuildingEntity> list, Context ctx, ListView listView, String flag) {
        this.dataList = list;
        this.ctx = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.lv = listView;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        int backgroundId;
        if (null == convertView)
            view = inflater.inflate(R.layout.activity_formlist_item, null);
        else
            view = convertView;
        BuildingEntity entity = dataList.get(position);
        TextView tvUnitName = view.findViewById(R.id.tvUnitName);
        tvUnitName.setText(entity.getUnitName());
//        if (lv.isItemChecked(position)) {
//            backgroundId = R.color.buttonColor;
//        } else {
//            backgroundId = R.color.normal;
//
//        }
//        // Drawable background = ctx.getResources().getDrawable(backgroundId);
//        tvUnitName.setBackgroundColor(backgroundId);
        //Log.d("getView", "getView: "+entity.getUnitName());

        return view;
    }
}
