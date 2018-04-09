package uk.co.jamiecruwys.gratitude.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import uk.co.jamiecruwys.gratitude.R
import uk.co.jamiecruwys.gratitude.notifications.NotificationSender
import uk.co.jamiecruwys.gratitude.receivers.ReminderReceiver
import uk.co.jamiecruwys.gratitude.toCalendar
import java.util.*

/**
 * Sends reminders
 */
class ReminderSender(val context: Context): ReminderProviderContract
{
	private var alarmTime: Calendar? = null
	private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
	private val pendingIntent = PendingIntent.getBroadcast(context, NotificationSender.Companion.REQUEST_CODE_LAUNCH_GRATITUDE_ACTIVITY, Intent(context, ReminderReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

	init { updateAlarmTime() }

	override fun startReminding()
	{
		updateAlarmTime()
		val time = alarmTime
		time ?: return
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
	}

	override fun stopReminding()
	{
		alarmManager.cancel(pendingIntent)
	}

	private fun updateAlarmTime()
	{
		val remindersTimePreferenceKey = context.getString(R.string.settings_reminders_time_key)
		alarmTime = PreferenceManager.getDefaultSharedPreferences(context).getString(remindersTimePreferenceKey, null).toCalendar()
	}
}