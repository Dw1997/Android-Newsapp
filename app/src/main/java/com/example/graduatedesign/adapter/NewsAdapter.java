package com.example.graduatedesign.adapter;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.graduatedesign.R;
import com.example.graduatedesign.beans.News;

import java.util.List;

public class NewsAdapter extends ArrayAdapter {
    private String TAG = "NewsAdapter";
    private int resouceId;
    List<News> listn;
    News news;
    Context mContext;

    public NewsAdapter(Context context,int res,List<News> listnews){
        super(context,res);
        this.resouceId = res;
        this.listn = listnews;
        mContext = context;
    }


    public NewsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount(){
        return listn.size();
    }

    @Override
    public Object getItem(int position){
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View conertView, ViewGroup parent){
        View view;
        viewHolder holder;
        news = listn.get(position);
        if(conertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.news_item,null);
            holder = new viewHolder();
            holder.title = view.findViewById(R.id.news_title);
            holder.date = view.findViewById(R.id.news_date);
            holder.img = view.findViewById(R.id.news_img);
            view.setTag(holder);
        }else {
            view = conertView;
            holder = (viewHolder) view.getTag();
        }

        Log.d(TAG,news.getTitle());
        holder.title.setText(news.getTitle());
        holder.date.setText(news.getDate());
        return view;
    }

    class viewHolder{
        TextView date,title;
        ImageView img;
    }

    public void getimg(String url,final ImageView imv){


    }
}
