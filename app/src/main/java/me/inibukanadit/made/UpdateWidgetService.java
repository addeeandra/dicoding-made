package me.inibukanadit.made;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class UpdateWidgetService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
