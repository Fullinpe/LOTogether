package com.example.lotogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class mAdapter extends BaseAdapter {

    List<Map<String,Object>> list;
    LayoutInflater inflater;
    public mAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setList(List<Map<String, Object>> list) {
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
        View view1=inflater.inflate(R.layout.xue_item,null);

        ImageView imageView=view1.findViewById(R.id.icon_author);
        final TextView textView1=view1.findViewById(R.id.textView6);
        TextView textView2=view1.findViewById(R.id.textView7);
        TextView textView3=view1.findViewById(R.id.textView8);
        TextView textView4=view1.findViewById(R.id.textView9);
        final ImageButton imageButton=view1.findViewById(R.id.imageButton);

        Map<String,Object> map=list.get(i);
        imageView.setImageResource((Integer) map.get("icon_author"));
        textView1.setText((String) map.get("author"));
        textView2.setText((String) map.get("essay"));
        textView3.setText((String) map.get("date"));
        textView4.setText((String) map.get("thumbs"));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView1.setText("Fullinpe");
                imageButton.setImageResource(R.mipmap.ic_launcher);
            }
        });
        return view1;
    }
}
