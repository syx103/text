package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private Button start;
    private Button canl;
    boolean filedown;
    private TextView chengdu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        canl = findViewById(R.id.canl);
        chengdu = findViewById(R.id.chengdu);
        chengdu.setText("未下载");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                      filedown = true;
                      OkHttpClient client = new OkHttpClient();
                       Request request = new Request.Builder().url("https://qd.myapp.com/myapp/qqteam/pcqq/PCQQ2019.exe").build();
                       InputStream inputStream = null;
                       OutputStream outputStream = null;
                        try {
                            Response response = client.newCall(request).execute();
                            final long  len = response.body().contentLength();//文件大小
                            ResponseBody responseBody = response.body();
                            inputStream = responseBody.byteStream();
                            final File file = new File(getExternalCacheDir(),"PCQQ2019.exe");
                            if (file.exists()) {
                                file.delete();
                            }
                            file.createNewFile();
                            outputStream = new FileOutputStream(file);
                            int lenth;
                            int sum = 0;
                            byte[] bytes = new byte[1024 * 10];
                            while ((lenth = inputStream.read(bytes)) != -1) {
                                if(!filedown) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            chengdu.setText("取消下载，已删除文件");
                                        }
                                    });
                                    break;
                                }
                                else {
                                    outputStream.write(bytes, 0, lenth);
                                    sum += lenth;
                                    final int finalSum = sum;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String s = "已下载" + (int) ((finalSum + 0.00) / len * 100) + "%";
                                            chengdu.setText(s);
                                        }
                                    });
                                }
                            }
                            if (!filedown) {
                                file.delete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });
        canl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filedown = false;
            }
        });
    }
}
