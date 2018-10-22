package me.inibukanadit.made.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoritesWidgetService extends RemoteViewsService  {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavoritesStackRemoteView(getApplicationContext(), intent);
    }
}
