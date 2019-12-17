package com.example.graduatedesign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.graduatedesign.tools.SharePreTools;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private final static String TAG = "MainActivity";
    private TextView tv_in,tv_out,tv_s;


    FrameLayout newscontent;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    FragmentManager manager;

    private ImageButton im1;

    private ArrayAdapter adapter;
    private List<String> typelist = new ArrayList<String>();
    Spinner spt;
    MyClick myClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setTabSelection(0);
    }


    private void initViews() {
        typelist.add("建桥要闻");
        typelist.add("菁菁校园");
        typelist.add("院部动态");
        tv_in = findViewById(R.id.tv_one);
        tv_out = findViewById(R.id.tv_two);
        tv_s = findViewById(R.id.tv_three);
        im1 = findViewById(R.id.ac_ib1);
        spt = findViewById(R.id.sp_t);
        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,typelist);
        spt.setAdapter(adapter);
        spt.setOnItemSelectedListener(this);
        String type = SharePreTools.getType(MainActivity.this);
        if(type.equals("0"))
        {
            spt.setVisibility(View.GONE);
            tv_in.setVisibility(View.VISIBLE);
            tv_in.setText("收藏");
            tv_out.setText("赞");
            tv_s.setText("举报");
        }

        tv_in.setOnClickListener(this);
        tv_out.setOnClickListener(this);
        tv_s.setOnClickListener(this);
        im1.setOnClickListener(this);

        manager = getFragmentManager();
        newscontent = findViewById(R.id.newscontent);
    }

    private void setTabSelection(int page){
        FragmentTransaction trans = manager.beginTransaction();
        hideFragment(trans);
        switch (page){
            case 0:
                if(firstFragment==null){
                    firstFragment = new FirstFragment();
                    trans.add(R.id.newscontent,firstFragment);
                }else{
                    trans.show(firstFragment);
                }
                break;
            case 1:
                if(secondFragment==null){
                    secondFragment = new SecondFragment();
                    trans.add(R.id.newscontent,secondFragment);
                }else{
                    trans.show(secondFragment);
                }
                break;
            case 2:
                if (thirdFragment == null) {
                    thirdFragment = new ThirdFragment();
                    trans.add(R.id.newscontent,thirdFragment);
                }else{
                    trans.show(thirdFragment);
                }

        }
        changecolor(page);
        trans.commit();

    }

    private void hideFragment(android.app.FragmentTransaction t) {
        if(firstFragment != null) {
            t.hide(firstFragment);
        }
        if(secondFragment != null) {
            t.hide(secondFragment);
        }
        if(thirdFragment != null) {
            t.hide(thirdFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_t:
                setTabSelection(0);
                break;
            case R.id.tv_one:
                setTabSelection(0);
                break;
            case R.id.tv_two:
                setTabSelection(1);
                break;
            case R.id.tv_three:
                setTabSelection(2);
                break;
            case R.id.ac_ib1:
                startActivity(new Intent(MainActivity.this,Yourself.class));
                break;
        }
    }

    public void changecolor(int page){
        if(page==0){
            spt.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_in.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_out.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_s.setBackgroundColor(Color.parseColor("#FFFFDD"));
        }
        if(page==1){
            spt.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_out.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_in.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_s.setBackgroundColor(Color.parseColor("#FFFFDD"));
        }
        if(page==2){
            spt.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_s.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_out.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_in.setBackgroundColor(Color.parseColor("#FFFFDD"));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MainActivity.this.sendBroadcast(new Intent("type_change"));
        setTabSelection(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface MyClick{
        void click_cn();
    }

//    public void gettypee(){
//        typelist.clear();
//        String url = "http://dwy.dwhhh.cn/api/newstp";
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        Request request = new Request.Builder().url(url).get().build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String res = response.body().string();
//                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
//                if(json.getString("result").length()>5){
//                    Log.d(TAG,json.getString("result"));
//                    JSONArray newl = json.getJSONArray("result");
//                    for(int i=0;i<newl.size();i++){
//                        String newst = newl.get(i).toString();
//                        com.alibaba.fastjson.JSONObject newd = JSONObject.parseObject(newst);
//                        String id = newd.getString("newstpid");
//                        String url = newd.getString("newstpcn");
//                        Types news = new Types(id,url);
//                        Log.d(TAG,news.toString());
//                        typelist.add(news.getTp_cn());
//                    }
//                }
//            }
//        });
//    }
}
