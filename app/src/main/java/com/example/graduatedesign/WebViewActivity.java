package com.example.graduatedesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.graduatedesign.tools.SharePreTools;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "WEBviewActivity";
    WebView webView = null;
    private ImageButton ib1;
    private Button bt_co,bt_go,bt_ta,bt_del;
    private LinearLayout lr1;
    private String id;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.new_layout);
        initview();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String url = intent.getStringExtra("url");
        Log.d(TAG,url);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        addlog(id);
    }

    private void initview(){
        webView = findViewById(R.id.wv_wv);
        ib1 = findViewById(R.id.wv_ib);
        bt_co = findViewById(R.id.wv_bt1);
        bt_go = findViewById(R.id.wv_bt2);
        bt_ta = findViewById(R.id.wv_bt3);
        bt_del = findViewById(R.id.wv_bt4);
        lr1 = findViewById(R.id.wv_lr1);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);//support zoom
        webSettings.setUseWideViewPort(true);// 这个很关键
        webSettings.setLoadWithOverviewMode(true);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d(TAG, "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }

        ib1.setOnClickListener(this);
        bt_co = findViewById(R.id.wv_bt1);
        bt_go = findViewById(R.id.wv_bt2);
        bt_ta = findViewById(R.id.wv_bt3);

        if(SharePreTools.getType(WebViewActivity.this).equals("0")){
            bt_co.setVisibility(View.GONE);
            bt_ta.setVisibility(View.GONE);
            bt_go.setVisibility(View.GONE);
            bt_del.setVisibility(View.VISIBLE);
        }

        bt_co.setOnClickListener(this);
        bt_go.setOnClickListener(this);
        bt_ta.setOnClickListener(this);
        bt_del.setOnClickListener(this);


    }

    public void addlog(String id){
        String user = SharePreTools.getPhone(WebViewActivity.this);
        String url = "http://dwy.dwhhh.cn/api/addlog?user="+user+"&id="+id;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebViewActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(WebViewActivity.this,"addlog-success",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WebViewActivity.this,"addlog-fail",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wv_ib:
                finish();
                break;
            case R.id.wv_bt1:
                addlog2("0");
                break;
            case R.id.wv_bt2:
                addlog2("1");
                break;
            case R.id.wv_bt3:
                addlog2("2");
                break;
            case R.id.wv_bt4:
                delnew();
                break;
        }
    }


    public void addlog2(String tp){
        String pid = SharePreTools.getPhone(WebViewActivity.this);
        String url = "http://dwy.dwhhh.cn/api/addlog2?nid="+id+"&pid="+pid+"&tp="+tp;
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebViewActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
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
                            if(tp.equals("0"))
                                Toast.makeText(WebViewActivity.this,"收藏-success",Toast.LENGTH_LONG).show();
                            if(tp.equals("1"))
                                Toast.makeText(WebViewActivity.this,"赞-success",Toast.LENGTH_LONG).show();
                            if(tp.equals("2"))
                                Toast.makeText(WebViewActivity.this,"举报-success",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WebViewActivity.this,"addlog-fail",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }

    private void delnew(){
        String url = "http://dwy.dwhhh.cn/api/deln?id="+id;

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebViewActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(WebViewActivity.this,"删除成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WebViewActivity.this,"addlog-fail",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

    }
}
