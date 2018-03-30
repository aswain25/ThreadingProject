package com.example.admin.threadingproject;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Handler.Callback {
    Random r = new Random();
    Handler handler;


    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ProgressBar progressBar3;
    ProgressBar progressBar4;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(this);

        progressBar1 = findViewById(R.id.progressBase1);
        progressBar2 = findViewById(R.id.progressBase2);
        progressBar3 = findViewById(R.id.progressBase3);
        progressBar4 = findViewById(R.id.progressBase4);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        progressBar1.setMax(Integer.MAX_VALUE);
        progressBar2.setMax(Integer.MAX_VALUE);
        progressBar3.setMax(Integer.MAX_VALUE);
        progressBar4.setMax(Integer.MAX_VALUE);


    }

    public void start_Threads_clicked(View view)
    {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            Runnable worker = new LoopWorker(i, getRandDouble(10.0, 100.0), handler);
            executor.execute(worker);
        }
    }

    double getRandDouble(double min, double max)
    {
        double randScaler = (double)r.nextInt() / Integer.MAX_VALUE;
        return randScaler * (max - min) + min;
    }

    @Override
    public boolean handleMessage(Message message) {
        int workerID = message.getData().getInt("workerID");
        double progress = message.getData().getDouble("progress");
        DecimalFormat format = new DecimalFormat("#.##");

        switch (workerID)
        {
            case 0:
                progressBar1.setProgress((int)(progress * Integer.MAX_VALUE));
                tv1.setText(format.format(progress * 100) + "%");
                break;
            case 1:
                progressBar2.setProgress((int)(progress * Integer.MAX_VALUE));
                tv2.setText(format.format(progress * 100) + "%");
                break;
            case 2:
                progressBar3.setProgress((int)(progress * Integer.MAX_VALUE));
                tv3.setText(format.format(progress * 100) + "%");
                break;
            case 3:
                progressBar4.setProgress((int)(progress * Integer.MAX_VALUE));
                tv4.setText(format.format(progress * 100) + "%");
                break;
        }
        return false;
    }
}