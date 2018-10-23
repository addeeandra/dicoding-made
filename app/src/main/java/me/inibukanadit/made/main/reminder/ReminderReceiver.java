package me.inibukanadit.made.main.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.inibukanadit.made.R;
import me.inibukanadit.made.main.MainActivity;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.remote.model.Movie;

public class ReminderReceiver extends BroadcastReceiver {

    public static final String TYPE_DAILY = "DailyReminder";
    public static final String TYPE_RELEASE = "ReleaseReminder";

    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private static final int REMINDER_ID_DAILY = 500;
    private static final int REMINDER_ID_RELEASE = 501;

    private static final String REMINDER_CHANNEL_DAILY = "DAILY_REMINDER_CHANNEL";
    private static final String REMINDER_CHANNEL_RELEASE = "RELEASE_REMINDER_CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = type.equalsIgnoreCase(TYPE_RELEASE) ? "New movie release!" : "Reminder";
        int notifId = type.equalsIgnoreCase(TYPE_RELEASE) ? REMINDER_ID_RELEASE : REMINDER_ID_DAILY;
        String channel = type.equalsIgnoreCase(TYPE_RELEASE) ? REMINDER_CHANNEL_RELEASE : REMINDER_CHANNEL_DAILY;

        if (type.equalsIgnoreCase(TYPE_RELEASE)) {
            showReleaseNotification(context, title, notifId, channel);
        } else {
            showReminderNotification(context, title, message, notifId, channel);
        }
    }

    /**
     * I take example from this module
     * https://www.dicoding.com/academies/14/tutorials/189
     *
     * @param context
     * @param title
     * @param message
     * @param notificationId
     * @param channel
     */
    private void showReminderNotification(
            Context context,
            String title,
            String message,
            int notificationId,
            String channel
    ) {
        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSound(notifSound);

        notifManager.notify(notificationId, notifBuilder.build());
    }

    @SuppressLint("CheckResult")
    private void showReleaseNotification(
            final Context context,
            final String title,
            final int notificationId,
            final String channel
    ) {
        // TODO uncomment to check by current date
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        final String currentTime = sdf.format(calendar.getTime());

        new MovieDbApi()
                .nowPlaying()
                .flatMapIterable(new Function<List<Movie>, Iterable<Movie>>() {
                    @Override
                    public Iterable<Movie> apply(List<Movie> movies) {
                        return movies;
                    }
                })
//                .filter(new Predicate<Movie>() {
//                    @Override
//                    public boolean test(Movie movie) {
//                        return movie
//                                .getRawReleaseDate()
//                                .equalsIgnoreCase(currentTime);
//                    }
//                })
                .firstOrError()
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        showReminderNotification(
                                context,
                                title,
                                "'" + movie.getTitle() + "' currently on playing today!",
                                notificationId,
                                channel
                        );
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public void setRepeatingReminder(
            Context context,
            String type,
            String message
    ) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] times = type.equalsIgnoreCase(TYPE_RELEASE) ? "08:00".split(":") : "07:00".split(":");
        int requestCode = type.equalsIgnoreCase(TYPE_RELEASE) ? REMINDER_ID_RELEASE : REMINDER_ID_DAILY;

        Calendar calendar = Calendar.getInstance();

        int timesValue = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        int currentTimesValue = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

        if (currentTimesValue >= timesValue) {
            calendar.add(Calendar.HOUR_OF_DAY, 24);
        }

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context, requestCode, intent, 0);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

    public void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_RELEASE) ? REMINDER_ID_RELEASE : REMINDER_ID_DAILY;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}
