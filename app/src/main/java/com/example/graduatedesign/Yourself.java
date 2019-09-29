package com.example.graduatedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.graduatedesign.myviews.CircleImageView;

public class Yourself extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView iv1;
    private TextView tv1,tv2;
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8;
    private ImageButton ib1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_inform);
        initView();
    }

    private void initView(){
        iv1 = findViewById(R.id.yf_iv);
        tv1 = findViewById(R.id.yf_name);
        tv2 = findViewById(R.id.yf_inf);

        bt1 = findViewById(R.id.yi_bt1);
        bt2 = findViewById(R.id.yi_bt2);
        bt3 = findViewById(R.id.yi_bt3);
        bt4 = findViewById(R.id.yi_bt4);
        bt5 = findViewById(R.id.yi_bt5);
        bt6 = findViewById(R.id.yi_bt6);
        bt7 = findViewById(R.id.yi_bt7);
        bt8 = findViewById(R.id.yi_bt8);

        bt8.setOnClickListener(this);

        ib1 = findViewById(R.id.yi_ib1);
        ib1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yi_ib1:
                finish();
                break;
            case R.id.yi_bt8:
                startActivity(new Intent(Yourself.this,LoginActivity.class));
                break;

        }
    }
}
