package com.example.graduatedesign;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePaActivity extends Activity implements View.OnClickListener {

    private ImageButton cp_ex;
    private Button cp_ch;
    private EditText et_ph,et_o,et_n;

    @Override
    protected  void onCreate(Bundle savaInstanceState){
        super.onCreate(savaInstanceState);
        setContentView(R.layout.change_passw);
        initView();
    }

    private void initView(){
        cp_ex = findViewById(R.id.cp_ib1);
        cp_ch = findViewById(R.id.cp_bt2);
        et_ph = findViewById(R.id.cp_et1);
        et_o = findViewById(R.id.cp_et2);
        et_n = findViewById(R.id.cp_et3);

        cp_ex.setOnClickListener(this);
        cp_ch.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cp_ib1:
                finish();
                break;
            case R.id.cp_bt2:
                changepass();
                break;
        }
    }

    private void changepass(){
        String ph = et_ph.getText().toString();
        String op = et_o.getText().toString();
        String np = et_n.getText().toString();
        String url = "http://dwy.dwhhh.cn/api/changep?phone="+ph+"&oldp="+op+"&newp="+np;
        Log.d("CHangepassw",url);
        if(ph.length()!=11){
            Toast.makeText(ChangePaActivity.this,"手机号应为11位",Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("changepass-------",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangePaActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(ChangePaActivity.this,"更改密码成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePaActivity.this,"更改密码失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
