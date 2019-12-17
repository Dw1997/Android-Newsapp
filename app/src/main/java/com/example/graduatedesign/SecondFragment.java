package com.example.graduatedesign;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.example.graduatedesign.tools.SharePreTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondFragment extends Fragment {
    private String TAG = "SecondFragment";
    private ListView lv_t2;
    private List<News> listn = new ArrayList<News>();
    NewsAdapter topad;
    private Handler handler=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.view_two,container,false);
        lv_t2 = messageLayout.findViewById(R.id.lv_two);
        listn = getnews();
        topad = new NewsAdapter(getActivity(),R.layout.news_item,listn);
        lv_t2.setAdapter(topad);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    listn = getnews();
                    handler.sendMessage(handler.obtainMessage(0,listn));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try{
            new Thread(runnable).start();
            handler = new Handler(){
                public void handleMessage(Message msg){
                    if (msg.what==0){
                        topad.notifyDataSetChanged();
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }

        lv_t2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    private List<News> getnews() {
        List<News> listbb = new ArrayList<News>();
        String typee = SharePreTools.getType(getActivity());
        String url = "";
        if(typee.equals("0"))
            url = "http://dwy.dwhhh.cn/api/coat?tp=1";
        if(typee.equals("1"))
            url = "http://dwy.dwhhh.cn/api/top20";
        Log.d(TAG, url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if (json.getString("result").length() > 5) {
                    Log.d(TAG, json.getString("result"));
                    JSONArray newl = json.getJSONArray("result");
                    for (int i = 0; i < newl.size(); i++) {
                        String newst = newl.get(i).toString();
                        com.alibaba.fastjson.JSONObject newd = JSONObject.parseObject(newst);
                        String id = newd.getString("newsid");
                        String url = newd.getString("newsurl");
                        String date = newd.getString("newsdate");
                        String impa = newd.getString("newsimpa");
                        String title = newd.getString("newstitle");
                        String state = newd.getString("newsstate");
                        String typee = newd.getString("newstype");
                        News news = new News(id,url,date,impa,title,state,typee);
                        Log.d(TAG, news.toString());
                        listbb.add(news);
                    }
                }
            }
        });
        return listbb;
    }
}
