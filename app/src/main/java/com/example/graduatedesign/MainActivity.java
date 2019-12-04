package com.example.graduatedesign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.graduatedesign.tools.SharePreTools;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "MainActivity";
    private TextView tv_in,tv_out,tv_s;


    FrameLayout newscontent;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    FragmentManager manager;

    private ImageButton im1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setTabSelection(0);
    }


    private void initViews() {
        tv_in = findViewById(R.id.tv_one);
        tv_out = findViewById(R.id.tv_two);
        tv_s = findViewById(R.id.tv_three);
        im1 = findViewById(R.id.ac_ib1);

        String type = SharePreTools.getType(MainActivity.this);
        if(type.equals("0"))
        {
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
            tv_in.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_out.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_s.setBackgroundColor(Color.parseColor("#FFFFDD"));
        }
        if(page==1){
            tv_out.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_in.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_s.setBackgroundColor(Color.parseColor("#FFFFDD"));
        }
        if(page==2){
            tv_s.setBackgroundColor(Color.parseColor("#AABB66"));
            tv_out.setBackgroundColor(Color.parseColor("#FFFFDD"));
            tv_in.setBackgroundColor(Color.parseColor("#FFFFDD"));
        }
    }

}
