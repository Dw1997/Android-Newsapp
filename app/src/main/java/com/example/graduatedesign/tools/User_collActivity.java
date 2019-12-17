package com.example.graduatedesign.tools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.graduatedesign.R;
import com.example.graduatedesign.WebViewActivity;
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


public class User_collActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = "User_collActivity";
    private ImageButton ib1;
    private ListView lv1;
    private List<News> newsList = new ArrayList<News>();
    private NewsAdapter newsAdapter;
    private Handler handler = null;


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_conew);
        initview();

    }

    private void initview(){
        ib1 = findViewById(R.id.uc_ib);
        lv1 = findViewById(R.id.uc_lv1);

        ib1.setOnClickListener(this);


        newsList  = getnews();
        newsAdapter = new NewsAdapter(User_collActivity.this,R.layout.news_item,newsList);
        lv1.setAdapter(newsAdapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    newsList = getnews();
                    handler.sendMessage(handler.obtainMessage(0,newsList));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try{
            new Thread(runnable).start();
            handler = new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==0){
                        newsAdapter.notifyDataSetChanged();
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News per = newsList.get(i);
                Intent intent = new Intent(User_collActivity.this, WebViewActivity.class);
                intent.putExtra("id",per.getId());
                intent.putExtra("url",per.getUrl());
                Log.d(TAG,per.toString());
                startActivity(intent);
            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String uid = newsList.get(position).getId();
                delco(uid);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uc_ib:
                finish();
                break;
        }

    }

    private List<News> getnews(){
        List<News> listbb = new ArrayList<News>();
        String ph = SharePreTools.getPhone(User_collActivity.this);
        String url = "http://dwy.dwhhh.cn/api/uco?ph="+ph;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(User_collActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
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
                        String id = newd.getString("newsid");
                        String url = newd.getString("newsurl");
                        String date = newd.getString("newsdate");
                        String impa = newd.getString("newsimpa");
                        String title = newd.getString("newstitle");
                        String state = newd.getString("newsstate");
                        String typee = newd.getString("newstype");
                        News news = new News(id,url,date,impa,title,state,typee);
                        Log.d(TAG,news.toString());
                        listbb.add(news);
                    }
                }
            }
        });
        return listbb;
    }

    public void delco(String id){
        String ph = SharePreTools.getPhone(User_collActivity.this);
        String url = "http://dwy.dwhhh.cn/api/delcon?id="+id+"&ph="+ph;
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(User_collActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("--------",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(User_collActivity.this,"删除成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(User_collActivity.this,"addlog-fail",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
