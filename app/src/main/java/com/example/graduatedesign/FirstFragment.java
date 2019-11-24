package com.example.graduatedesign;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.graduatedesign.adapter.NewsAdapter;
import com.example.graduatedesign.beans.News;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment{
    private String TAG = "FirstFragmrnt";
    private ListView lv_nfi;
    public List<News> listn = new ArrayList<News>();
    NewsAdapter newsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.view_one,container,false);
        return messageLayout;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        getnews(1);
        lv_nfi = view.findViewById(R.id.lv_one);
        newsAdapter = new NewsAdapter(getActivity(),R.layout.news_item,listn);
        lv_nfi.setAdapter(newsAdapter);
        lv_nfi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News per = listn.get(i);
                Intent intent = new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("id",per.getId());
                intent.putExtra("url",per.getUrl());
                Log.d(TAG,per.toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void getnews(int page){
        String url = "http://dwy.dwhhh.cn/api/news?num="+page;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
                    Log.d(TAG,json.getString("result"));
                    JSONArray newl = json.getJSONArray("result");
                    for(int i=0;i<newl.size();i++){
                        String newst = newl.get(i).toString();
                        com.alibaba.fastjson.JSONObject newd = JSONObject.parseObject(newst);
                        String id = newd.getString("id");
                        String url = newd.getString("url");
                        String date = newd.getString("date");
                        String impa = newd.getString("impa");
                        String title = newd.getString("title");
                        String state = newd.getString("state");
                        News news = new News(id,url,date,impa,title,state);
                        Log.d(TAG,news.toString());
                        listn.add(news);
                    }
//                    newsAdapter.notifyDataSetChanged();
                    Log.d(TAG," "+listn.size());
                }

            }
        });
    }
}
