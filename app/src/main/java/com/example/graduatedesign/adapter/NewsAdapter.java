package com.example.graduatedesign.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.graduatedesign.R;
import com.example.graduatedesign.beans.News;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsAdapter extends ArrayAdapter {


    private String TAG = "NewsAdapter";
    private int resouceId;
    List<News> listn;
    News news;
    Context mContext;
    private LruCache<String, BitmapDrawable> mImageCache;

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
//        getimg(news.getImpa(),holder.img);
        Glide.with(mContext).load(news.getImpa()).placeholder(R.drawable.image).diskCacheStrategy(DiskCacheStrategy.NONE).fitCenter().into(holder.img);

        return view;
    }

    class viewHolder{
        TextView date,title;
        ImageView img;
    }

    public void getimg(String url,ImageView imv){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(),"图片加载失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                imv.setImageBitmap(bitmap);
            }
        });
    }



}
