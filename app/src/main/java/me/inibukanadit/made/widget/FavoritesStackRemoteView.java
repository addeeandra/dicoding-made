package me.inibukanadit.made.widget;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.inibukanadit.made.R;
import me.inibukanadit.sharedmodule.db.DatabaseContract;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;

import static me.inibukanadit.sharedmodule.db.DatabaseContract.CONTENT_URI;

public class FavoritesStackRemoteView implements RemoteViewsService.RemoteViewsFactory {

    private List<String> mFavoriteMovies = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public FavoritesStackRemoteView(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
        );
    }

    @Override
    public void onCreate() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String poster = cursor.getString(cursor.getColumnIndex(DatabaseContract.FavoritesColumn.POSTER));
                mFavoriteMovies.add(poster);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mFavoriteMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.favorites_widget_item);

        try {
            String posterUrl = MovieDbApi.getPosterUrl(mFavoriteMovies.get(position));
            Bitmap imgPoster = Picasso.get().load(posterUrl).get();
            view.setImageViewBitmap(R.id.iv_favorites_widget_preview, imgPoster);

            Bundle extras = new Bundle();
            extras.putInt(FavoritesWidget.EXTRA_ITEM, position);

            Intent fillIntent = new Intent();
            fillIntent.putExtras(extras);

            view.setOnClickFillInIntent(R.id.iv_favorites_widget_preview, fillIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
