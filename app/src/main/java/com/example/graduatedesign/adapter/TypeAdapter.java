package com.example.graduatedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.graduatedesign.R;
import com.example.graduatedesign.beans.Types;

import java.util.List;

public class TypeAdapter extends BaseAdapter {

    private List<Types> types;
    private Context mcontext;

    public TypeAdapter(Context context,List<Types> tlist){
        this.mcontext = context;
        this.types = tlist;
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Object getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(R.layout.typa_item,null);
        if(convertView!=null){
            TextView tv1 = (TextView) convertView.findViewById(R.id.tp_tv1);
            tv1.setText(types.get(position).getTp_cn());
        }
        return null;
    }
}
