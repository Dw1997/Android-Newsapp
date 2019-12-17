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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

public class ThirdFragment extends Fragment implements View.OnClickListener{
    private String TAG = "ThirdFragmrnt";
    private Spinner sp1;
    private EditText et1;
    private ImageButton ib1;
    private ListView lv1;
    private List<String> typelist = new ArrayList<String>();
    private ArrayAdapter adapter;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<News>();
    private Handler handler = null;
    private LinearLayout lr3;

    String typee = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View messageLayout = inflater.inflate(R.layout.view_three, container, false);
        lr3 = messageLayout.findViewById(R.id.th_lr3);
        typee = SharePreTools.getType(getActivity());
        if(typee.equals("1")){
            sp1 = messageLayout.findViewById(R.id.vt_sp1);
            et1 = messageLayout.findViewById(R.id.vt_et1);
            ib1 = messageLayout.findViewById(R.id.vt_bt1);
            typelist.add("关键词");
            typelist.add("日期");
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,typelist);
            sp1.setAdapter(adapter);
            ib1.setOnClickListener(this);
        }

        if(typee.equals("0")){
            lr3.setVisibility(View.GONE);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);
                        getnews();
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
            getnews();
        }

//        newsList = getnews();
        newsAdapter = new NewsAdapter(getActivity(),R.layout.news_item,newsList);
        lv1 = messageLayout.findViewById(R.id.lv_three);
        lv1.setAdapter(newsAdapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News per = newsList.get(i);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getnews() {
//        List<News> listoo = new ArrayList<News>();
        String url = "";
        if(typee.equals("1")){
            int tp = sp1.getSelectedItemPosition()+1;
            String it = et1.getText().toString();
            url = "http://dwy.dwhhh.cn/api/search?tp="+tp+"&it="+it;
        }

        if(typee.equals("0"))
            url = "http://dwy.dwhhh.cn/api/coat?tp=2";
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
                    newsList.clear();
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
                        newsList.add(news);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vt_bt1:
                refrechnews();
                break;
        }
    }

    public void refrechnews(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
//                    List<News> listbb = new ArrayList<News>();
//                    listbb.clear();
                    getnews();

//                    Log.d(TAG,""+listbb.size());
//                    newsList.clear();
//                    for(News b:listbb){
//                        Log.d(TAG,b.toString()+"======");
//                        newsList.add(b);
//                    }
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
    }
}
