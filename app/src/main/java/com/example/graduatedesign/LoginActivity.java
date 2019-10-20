package com.example.graduatedesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et1,et2;
    private TextView tv1,tv2;
    private Button bt1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reg);
        initViews();
    }

    private void initViews(){
        et1 = findViewById(R.id.lr_et1);
        et2 = findViewById(R.id.lr_et2);
        tv1 = findViewById(R.id.lr_tv1);
        tv2 = findViewById(R.id.lr_tv2);
        bt1 = findViewById(R.id.lr_bt1);

        bt1.setOnClickListener(this);

        et1.setText("18930913829");
        et2.setText("Dwzx170322");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lr_bt1:{
                Log.d("bt1","----------------------");
                loginhttp();
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
            }
        }
    }

    private void loginhttp(){
        String name = et1.getText().toString();
        String passw = et2.getText().toString();
        String url = "http://dwy.dwhhh.cn/api/login?name="+name+"&passw="+passw;
        Log.d("url",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("----------",e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                JSONObject json = JSONObject.parseObject(result);
                Log.d("++++++++++++++",json.getString("result"));
                Log.d("+++++++++++",result);
                if(json.getString("result").equals("success")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();

                        }
                    });
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"登录失败,检查账户密码",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
