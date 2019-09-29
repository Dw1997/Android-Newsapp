package com.example.graduatedesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lr_bt1:{
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
            }
        }
    }
}
