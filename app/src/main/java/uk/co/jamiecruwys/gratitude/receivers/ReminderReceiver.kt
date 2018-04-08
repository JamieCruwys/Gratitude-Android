package uk.co.jamiecruwys.gratitude.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import uk.co.jamiecruwys.gratitude.notifications.NotificationSender

/**
 * Broadcast receiver that is triggered when a reminder needs to be sent to the user
 */
class ReminderReceiver : BroadcastReceiver()
{
	override fun onReceive(context: Context, intent: Intent?) = NotificationSender.sendReminderNotification(context)
}