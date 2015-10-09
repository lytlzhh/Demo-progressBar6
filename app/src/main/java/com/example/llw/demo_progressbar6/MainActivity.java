package com.example.llw.demo_progressbar6;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button btn;
    private int prent = 0;
    private int sum = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progree);


        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                sum = (int) msg.obj;
                if (msg.what == 0x111) {
                    progressBar.setProgress(sum);
                } else {
                    Toast.makeText(MainActivity.this, "耗时操作已经完成", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/?tn=10018801_hao")));
                }
            }
        };

        final Thread tt = new Thread() {
            @Override
            public void run() {

                while (true) {
                    prent = dowork();
                    Message message = new Message();
                    if (prent < 100) {
                        message.what = 0x111;
                        message.obj = prent;
                        handler.sendMessage(message);
                    } else {
                        message.what = 0x110;
                        message.obj = prent;
                        handler.sendMessage(message);
                    }
                }
            }

            public int dowork() {
                prent += Math.random()*2;
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return prent;
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt.start();
            }
        });
    }


}
