package me.inibukanadit.made.main.reminder;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderPreference {

    private SharedPreferences mSharedPreference;
    private String KEY_REMINDER_DAILY = "REMINDER_DAILY";
    private String KEY_REMINDER_RELEASE = "REMINDER_RELEASE";

    public ReminderPreference(Context context) {
        mSharedPreference = context.getSharedPreferences("reminder", Context.MODE_PRIVATE);
    }

    public boolean isReminderDailyActive() {
        return mSharedPreference.getBoolean(KEY_REMINDER_DAILY, false);
    }

    public boolean isReminderReleaseActive() {
        return mSharedPreference.getBoolean(KEY_REMINDER_RELEASE, false);
    }

    public void setReminderDaily(boolean active) {
        mSharedPreference
                .edit()
                .putBoolean(KEY_REMINDER_DAILY, active)
                .apply();
    }

    public void setReminderRelease(boolean active) {
        mSharedPreference
                .edit()
                .putBoolean(KEY_REMINDER_RELEASE, active)
                .apply();
    }

    public boolean toggleReminderDaily() {
        boolean newActive = !isReminderDailyActive();
        setReminderDaily(newActive);
        return newActive;
    }

    public boolean toggleReminderRelease() {
        boolean newActive = !isReminderReleaseActive();
        setReminderRelease(newActive);
        return newActive;
    }

}
