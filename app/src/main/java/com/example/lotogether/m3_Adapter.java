package com.example.lotogether;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class m3_Adapter extends BaseAdapter {

    private List<Map<String,Object>> list;
    private LayoutInflater inflater;
    m3_Adapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view1=inflater.inflate(R.layout.m3_item,null);

        TextView textView1=view1.findViewById(R.id.num_m3_i);
        TextView textView2=view1.findViewById(R.id.name_m3_i);


        Map<String,Object> map=list.get(i);
        textView1.setText((String) map.get("num"));
        textView2.setText((String) map.get("name"));


        return view1;
    }
}
