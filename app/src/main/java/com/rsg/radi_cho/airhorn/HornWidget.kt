package com.rsg.radi_cho.airhorn

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.RemoteViews



class HornWidget : AppWidgetProvider() {
    var player: MediaPlayer? = null

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val remoteViews: RemoteViews
        val watchWidget: ComponentName

        remoteViews = RemoteViews(context.packageName, R.layout.horn_widget)
        watchWidget = ComponentName(context, HornWidget::class.java)

        remoteViews.setOnClickPendingIntent(R.id.horn_button, getPendingSelfIntent(context, SYNC_CLICKED))
        appWidgetManager.updateAppWidget(watchWidget, remoteViews)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (SYNC_CLICKED == intent.action) {
            player = MediaPlayer.create(context, R.raw.horn)
            player?.setOnCompletionListener( { mp -> mp.release() })
            player?.start()
        }
    }

    protected fun getPendingSelfIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    companion object {
        private val SYNC_CLICKED = "automaticWidgetSyncButtonClick"
    }
}
