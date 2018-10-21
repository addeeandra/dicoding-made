package me.inibukanadit.made;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private Button mBtnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_stop:
                stopJob();
                break;
        }
    }

    private static int JOB_ID = 100;
    private static int PERIOD_SCHEDULER = 86000;

    private void startJob() {
        ComponentName serviceComponent = new ComponentName(this, UpdateWidgetService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceComponent)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setPeriodic(PERIOD_SCHEDULER)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);

        Toast.makeText(this, "Job started", Toast.LENGTH_SHORT).show();
    }

    private void stopJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(JOB_ID);

        Toast.makeText(this, "Job stopped", Toast.LENGTH_SHORT).show();
    }

}
