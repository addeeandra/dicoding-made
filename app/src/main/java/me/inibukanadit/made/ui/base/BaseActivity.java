package me.inibukanadit.made.ui.base;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.inibukanadit.made.utils.NetworkUtils;

public class BaseActivity extends AppCompatActivity implements MvpView {

    protected void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int message) {
        onError(getString(message));
    }

    @Override
    public void onError(String message) {
        showSnackbar(message);
    }

    @Override
    public void showMessage(int message) {
        showMessage(getString(message));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            // TODO toast error with default message from string resource
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }
}
