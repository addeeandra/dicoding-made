package me.inibukanadit.made;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.widget.RemoteViews;

public class UpdateWidgetService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.random_number_widget);
        ComponentName widget = new ComponentName(this, RandomNumberWidget.class);

        String lastUpdate = "Random : " + NumberGenerator.Generate(100);

        views.setTextViewText(R.id.appwidget_text, lastUpdate);

        manager.updateAppWidget(widget, views);

        jobFinished(params, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
