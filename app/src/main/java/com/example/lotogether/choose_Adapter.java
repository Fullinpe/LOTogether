package com.example.lotogether;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class choose_Adapter extends BaseAdapter {

    private List<Map<String,Object>> list;
    private LayoutInflater inflater;

    choose_Adapter(Context context) {
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
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view1=inflater.inflate(R.layout.choose_item,null);

        TextView textView1=view1.findViewById(R.id.s_id_choose);
        final TextView textView2=view1.findViewById(R.id.name_choose);
        final CheckBox checkBox=view1.findViewById(R.id.checkBox_choose);

        Map<String,Object> map=list.get(i);
        textView1.setText((String) map.get("num"));
        textView2.setText((String) map.get("name"));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mFragment.cheched[i]=checkBox.isChecked();
            }
        });

        return view1;
    }
}
