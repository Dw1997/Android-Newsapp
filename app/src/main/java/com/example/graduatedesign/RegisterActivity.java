package com.example.graduatedesign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton br_exit;
    private EditText et_ph,et_na,et_pa,et_ma;
    private Button bt_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        initView();
    }

    private void initView(){
        et_ph = findViewById(R.id.re_et1);
        et_na = findViewById(R.id.re_et2);
        et_pa = findViewById(R.id.re_et3);
        et_ma = findViewById(R.id.re_et4);

        br_exit = findViewById(R.id.reg_ib1);
        bt_re = findViewById(R.id.re_bt2);

        br_exit.setOnClickListener(this);
        bt_re.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reg_ib1:
                finish();
                break;
            case R.id.re_bt2:
                register();
                break;
        }
    }

    private void register(){
        String ph = et_ph.getText().toString();
        String na = et_na.getText().toString();
        String pa = et_pa.getText().toString();
        String ma = et_ma.getText().toString();
        if(ph.length()!=11){
            Toast.makeText(RegisterActivity.this,"手机号为11位",Toast.LENGTH_LONG).show();
            return;
        }
        String url = "http://dwy.dwhhh.cn/api/register?userphone="+ph+"&username="+na+"&usermail="+ma+"&userpass="+pa;
        Log.d("register",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("data").equals("success")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
