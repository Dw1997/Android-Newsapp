package com.example.graduatedesign;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.graduatedesign.myviews.CircleImageView;
import com.example.graduatedesign.tools.SharePreTools;
import com.example.graduatedesign.tools.User_collActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Yourself extends AppCompatActivity implements View.OnClickListener{

    private static final int EXTERNAL_STORAGE_REQ_CODE = 10;
    private CircleImageView iv1;
    private TextView tv1,tv2;
    private Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt_up;
    private ImageButton ib1;
    private ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_inform);
        initView();
        int permission = ActivityCompat.checkSelfPermission(Yourself.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Yourself.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_REQ_CODE);
        }

    }

    private void initView(){
        iv1 = findViewById(R.id.yf_iv);
        tv1 = findViewById(R.id.yf_name);
        tv2 = findViewById(R.id.yf_inf);
        pb = findViewById(R.id.yi_pb);

        bt1 = findViewById(R.id.yi_bt1);
        bt2 = findViewById(R.id.yi_bt2);
        bt3 = findViewById(R.id.yi_bt3);
        bt4 = findViewById(R.id.yi_bt4);
        bt5 = findViewById(R.id.yi_bt5);
        bt6 = findViewById(R.id.yi_bt6);
        bt7 = findViewById(R.id.yi_bt7);
        bt8 = findViewById(R.id.yi_bt8);
        bt_up = findViewById(R.id.yi_btup);

        bt1.setVisibility(View.GONE);
        bt2.setVisibility(View.GONE);
        bt4.setVisibility(View.GONE);
        bt5.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);

        bt8.setOnClickListener(this);

        ib1 = findViewById(R.id.yi_ib1);
        ib1.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt_up.setOnClickListener(this);

        String name =SharePreTools.getname(Yourself.this);
        Date date = new Date();
        String da = " "+date;
        tv1.setText(name);
        tv2.setText(da);
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
            case R.id.yi_bt3:
                startActivity(new Intent(Yourself.this, User_collActivity.class));
                break;
            case R.id.yi_btup:
                {
                pb.setVisibility(View.VISIBLE);
                updateApk("http://file.dwhhh.cn/dwapp.apk");
                }
                break;

        }
    }

    String mypath;
    private void updateApk(String url) {
        Log.d("update-url",url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long allsize = response.body().contentLength();
                InputStream is = response.body().byteStream();
                int len = 0;
                long sum = 0;
                File file = new File(Environment.getExternalStorageDirectory(), "dw.apk");
                mypath = file.getAbsolutePath();
                Log.d("update-apk","-==========");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                while((len = is.read(buf))!= -1) {
                    fos.write(buf,0,len);
                    sum += len;
                    int progress = (int) (sum * 1.0f / allsize * 100);
                    pb.setProgress(progress);
                }
                fos.flush();
                fos.close();
                is.close();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        installApk(Yourself.this, mypath);
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException ex) {

            }
        });
    }

    private void installApk(Context context, String apkpath) {
//        try{
            Uri uri  =null;
            File file = new File(apkpath);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {//7.0 Android N

            //com.xxx.xxx.fileprovider为上述manifest中provider所配置相同

            uri = FileProvider.getUriForFile(context, "com.example.graduatedesign.fileprovider", new File(apkpath));



            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//7.0以后，系统要求授予临时uri读取权限，安装完毕以后，系统会自动收回权限，该过程没有用户交互

        } else {//7.0以下

            uri = Uri.fromFile(new File(apkpath));

            intent.setAction(Intent.ACTION_VIEW);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
//            if(Build.VERSION.SDK_INT >= (Build.VERSION_CODES.LOLLIPOP +3)) {
//            Toast.makeText(Yourself.this, "Android N", Toast.LENGTH_LONG).show();
//            }else {
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
//            }
            context.startActivity(intent);
//        }catch (Exception e) {
//            Toast.makeText(context, "failed file parsing", Toast.LENGTH_LONG).show();
//        }
    }


}
