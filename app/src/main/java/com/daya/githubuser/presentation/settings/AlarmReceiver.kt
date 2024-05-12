package com.daya.githubuser.presentation.settings

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.daya.githubuser.R
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver  : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context?) {
        val channelId = "channel_1"
        val channelName = " alarmManager channel"
        if (context == null) return

        val notificationManagerCompat = context.getSystemService<NotificationManager>()
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_alarm_24)
            .setContentTitle(context.getString(R.string.notif_title))
            .setContentText(context.getString(R.string.notif_content_body))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }
            builder.setChannelId(channelId)
            notificationManagerCompat?.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat?.notify(ID_REPEATING,notification)
    }

    companion object {
        private const val ID_REPEATING = 313

        fun setRepeatingAlarm(context: Context?, time: String) {
            if (context == null) return
            if (isDateInvalid(time)) return
            if (isAlarmSet(context)) return

            val alarmManager = context.getSystemService<AlarmManager>()
            val intent = buildIntent(context)

            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
                set(Calendar.MINUTE, timeArray[1].toInt())
                set(Calendar.SECOND, 0)
            }

            val pendingIntent = buildOrGetPendingIntent(context,intent,flags = 0)
            if (pendingIntent != null) {
                alarmManager?.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            }
        }

         fun isAlarmSet(context: Context): Boolean {
            val intent = buildIntent(context)
            return buildOrGetPendingIntent(context,intent) != null
        }

        private fun isDateInvalid(date: String, format: String = "HH:mm"): Boolean {
            return try {
                val df = SimpleDateFormat(format, Locale.getDefault())
                df.isLenient = false
                df.parse(date)
                false
            } catch (e: Exception) {
                true
            }
        }

        fun cancelRepating(context : Context?){
            if (context == null) return
            if (!isAlarmSet(context)) return
            val alarmManager = context.getSystemService<AlarmManager>()
            val intent = buildIntent(context)
            val pendingIntent = buildOrGetPendingIntent(context,intent)
            if (pendingIntent != null && alarmManager != null){
                alarmManager.cancel(pendingIntent)
            }
        }

        private fun buildIntent(context : Context): Intent {
            return Intent(context, AlarmReceiver::class.java)
        }

        private fun buildOrGetPendingIntent(context: Context?,intent: Intent,flags : Int = PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE ): PendingIntent? {
            return PendingIntent.getBroadcast(context, ID_REPEATING,intent,flags)
        }
    }




}