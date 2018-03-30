package com.example.admin.threadingproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Admin on 3/29/2018.
 */

public class LoopWorker implements Runnable {
    public final long WORKUNIT = 1000000;

    private double workLeft;
    private int workerID;
    private Handler handler;

    public LoopWorker(int workerID, double initWorkLeft, Handler handler)
    {
        this.workLeft = initWorkLeft;
        this.workerID = workerID;
        this.handler = handler;
    }

    @Override
    public void run()
    {
        for (long i = 0; i < workLeft * (double)WORKUNIT; i++)
            if (i % (WORKUNIT / 4) == 0)
                updateProgress((double)i / (workLeft * (double)WORKUNIT));

        updateProgress(1.0);
    }
    private void updateProgress(double progress)
    {
        Message message = new Message();
        Bundle bundler = new Bundle();
        bundler.putInt("workerID", workerID);
        bundler.putDouble("progress", progress);
        message.setData(bundler);
        handler.sendMessage(message);
    }
}
