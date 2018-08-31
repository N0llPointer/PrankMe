package com.nollpointer.prankme


import android.animation.ObjectAnimator
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.nollpointer.prankme.view.ChatAdapter
import java.io.File
import kotlin.math.abs

class ChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val act = activity as AppCompatActivity
        act.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val mainView = inflater.inflate(R.layout.chat_view, container, false)
        val listView: ListView = mainView.findViewById(R.id.chat_list_view)
        //listView.adapter = ChatAdapter(act, arrayOf("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"))
        val button: Button = mainView.findViewById(R.id.chat_pick_sound)
        val mediaPlayer = MediaPlayer.create(act,R.raw.vk_click)
        val testButton: TextView = mainView.findViewById(R.id.chat_pick_sound)
        button.setOnClickListener{
            //mediaPlayer.start()
            //testButton.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f)
            animate(testButton,1f)
            listView.smoothScrollToPosition(14)
        }
        mainView.findViewById<ImageButton>(R.id.chat_send_message).setOnClickListener{
            sendNotification("KEKEKEKEKE")
        }
        return mainView
    }

    fun animate(view: View?, endWeight: Float){
        val viewWrapper = PickSoundWrapper(view as View)
        val startWeight = abs(1-endWeight)
        val animator= ObjectAnimator.ofFloat(viewWrapper,"weight",startWeight,endWeight)
        animator.duration = 280
        animator.start()

    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(activity, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)
        val channelId = "channel1234531391"
        val defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator  + "//" + activity!!.packageName + File.separator + R.raw.martiangun)
        //val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Log.wtf("13",defaultSoundUri.toString())
        val notificationBuilder = NotificationCompat.Builder(activity as AppCompatActivity, channelId)
                .setSmallIcon(R.drawable.ic_test)
                .setContentTitle("BOIIII")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)

        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title01",
                    NotificationManager.IMPORTANCE_HIGH)
            channel.setSound(defaultSoundUri,AudioAttributes.Builder()
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
