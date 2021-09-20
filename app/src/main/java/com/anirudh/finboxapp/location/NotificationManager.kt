package com.anirudh.finboxapp.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.WorkManager
import com.anirudh.finboxapp.HomeActivity
import com.anirudh.finboxapp.R

class NotificationManager {
    companion object {
        private const val NOTIFICATION_TAG = "Location"

        fun notify(context: Context, title: String, text: String) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
                notifyPre(context, title, text);
            } else {
                notifyO(context, title, text);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun notifyO(context: Context, title: String?, text: String?) {
            val channelId: String = createLocationChannel(context)
            WorkManager.getInstance(context).cancelAllWorkByTag("Location")

            val pi = PendingIntent.getActivity(
                context,
                100,
                Intent(context, HomeActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification: Notification = Notification.Builder(context, channelId)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_location)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pi)
                .setAutoCancel(false).build()
            notify(context, notification)
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun createLocationChannel(ctx: Context): String {
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelName: CharSequence = ctx.getString(R.string.channel_id)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(
                ctx.getString(R.string.channel_id), channelName, importance
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
            return ctx.getString(R.string.channel_id)
        }

        private fun notifyPre(
            context: Context,
            title: String?, text: String?
        ) {
            val res: Resources = context.resources
            WorkManager.getInstance(context).cancelAllWorkByTag("Location")

            val pi = PendingIntent.getActivity(
                context,
                100,
                Intent(context, HomeActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(context)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_stat_location)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pi)
                    .setAutoCancel(false)
            notify(context, builder.build())
        }

        private fun notify(context: Context, notification: Notification) {
            val nm = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(NOTIFICATION_TAG, 0, notification)
        }


        fun cancel(context: Context) {
            val nm = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.cancel(NOTIFICATION_TAG, 0)
        }

    }
}