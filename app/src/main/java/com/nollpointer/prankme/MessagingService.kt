package com.nollpointer.prankme

import android.app.Notification
import android.content.Intent
import android.os.IBinder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.content.Context.NOTIFICATION_SERVICE
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.net.Uri
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.File


class MessagingService: FirebaseMessagingService(){

    var sound = 10

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        if (p0!!.data.isNotEmpty()) {
            sound = p0!!.data.get("SOUND")!!.toInt()
            sendNotification("JHello")
        }

        Log.wtf("TAG","$sound")
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)
        val channelId = "channel1234531391"
        val defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator  + "//" + this.packageName + File.separator + R.raw.martiangun)
        //val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Log.wtf("13",defaultSoundUri.toString())
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_test)
                .setContentTitle("BOIIII")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title01",
                    NotificationManager.IMPORTANCE_HIGH)
            channel.setSound(defaultSoundUri, AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
            channel.enableVibration(true)
            channel.enableLights(true)
            notificationBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            notificationManager.createNotificationChannel(channel)
            Log.wtf("ASD",channel.toString())
        }

        //notificationBuilder.
        val notification = notificationBuilder.build()
        Log.wtf("ASD",notification.toString())
        notification.sound = defaultSoundUri
        notification.audioStreamType = AudioManager.STREAM_ALARM;
        //notification.defaults = Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE

        notificationManager.notify(0 /* ID of notification */, notification)
    }
}