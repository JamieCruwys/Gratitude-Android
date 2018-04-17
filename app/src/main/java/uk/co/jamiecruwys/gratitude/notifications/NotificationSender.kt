package uk.co.jamiecruwys.gratitude.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.RemoteInput
import android.support.v4.app.TaskStackBuilder
import uk.co.jamiecruwys.gratitude.activity.GratitudeActivity
import uk.co.jamiecruwys.gratitude.R
import uk.co.jamiecruwys.gratitude.receivers.NotificationReplyReceiver

/**
 * Local notification sender
 */
object NotificationSender : NotificationProviderContract
{
	object Companion
	{
		/**
		 * The notification channel of which notifications will be sent in for [Build.VERSION_CODES.O] and above
		 */
		const val REMINDERS_NOTIFICATION_CHANNEL_ID = "gratitude_reminders_notification_channel"

		/**
		 * The id of a reminder notification. Because the same id is used,
		 * there will only be one notification visible at any time
		 */
		const val REMINDER_NOTIFICATION_ID = 987

		/**
		 * Request code used to launch [GratitudeActivity] via a [PendingIntent]
		 */
		const val REQUEST_CODE_LAUNCH_GRATITUDE_ACTIVITY = 652

		/**
		 * Request code used when the user directly replies to the reminder notification
		 */
		const val REQUEST_CODE_REPLY_TO_REMINDER_NOTIFICATION = 777

		/**
		 * The key used to get the text a user entered when directly replying to a reminder notification
		 */
		const val RESULT_KEY_REMINDER_REPLY = "reminder_entry_via_notification"

		/**
		 * Intent action for when you press the reply button on a reminder notification
		 */
		const val REMINDER_NOTIFICATION_REPLY_ACTION = "action_reply_to_reminder_notification"
	}

	@RequiresApi(Build.VERSION_CODES.O)
	override fun createNotificationChannel(context: Context)
	{
		val name = context.getString(R.string.reminder_notification_channel_name)
		val description = context.getString(R.string.reminder_notification_channel_description)
		val importance = NotificationManager.IMPORTANCE_HIGH

		val channel = NotificationChannel(Companion.REMINDERS_NOTIFICATION_CHANNEL_ID, name, importance)
		channel.description = description
		channel.enableLights(true)
		channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
		channel.lightColor = Color.RED
		channel.enableVibration(true)
		channel.setShowBadge(true)
		channel.setBypassDnd(true)

		val notificationManagerAPI26 = (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
		notificationManagerAPI26.createNotificationChannel(channel)
	}

	override fun sendReminderNotification(context: Context)
	{
		val notificationBuilder = NotificationCompat.Builder(context, Companion.REMINDERS_NOTIFICATION_CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_heart)
				.setContentTitle(context.getString(R.string.reminder_notification_title))
				.setContentText(context.getString(R.string.reminder_notification_text))
				.setPriority(NotificationCompat.PRIORITY_HIGH)
				.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
				.setCategory(NotificationCompat.CATEGORY_REMINDER)
				.setContentIntent(getReminderNotificationPendingIntent(context))
				.setAutoCancel(true)

		/**
		 * Android 7.0 (Nougat), API level 24 added functionality so users can reply directly inside of a notification
		 * (they can enter text which will then be routed to the notification’s parent app) using inline reply.
		 *
		 * Add the direct reply action only when we are targeting this version or above
		 */
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			notificationBuilder.addAction(getReminderNotificationReplyAction(context))
		}

		NotificationManagerCompat.from(context).notify(Companion.REMINDER_NOTIFICATION_ID, notificationBuilder.build())
	}

	private fun getReminderNotificationPendingIntent(context: Context): PendingIntent?
	{
		val stackBuilder = TaskStackBuilder.create(context)
		stackBuilder.addParentStack(GratitudeActivity::class.java)
		stackBuilder.addNextIntent(Intent(context, GratitudeActivity::class.java))
		return stackBuilder.getPendingIntent(Companion.REQUEST_CODE_LAUNCH_GRATITUDE_ACTIVITY, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/**
	 * Android 7.0 (Nougat), API level 24 is required for direct replies to notifications
	 */
	@RequiresApi(Build.VERSION_CODES.N)
	private fun getReminderNotificationReplyAction(context: Context): NotificationCompat.Action
	{
		val remoteInput = RemoteInput.Builder(Companion.RESULT_KEY_REMINDER_REPLY)
				.setLabel(context.getString(R.string.reminder_notification_reply_action_text_hint))
				.build()

		return NotificationCompat.Action.Builder(R.drawable.ic_send,
				context.getString(R.string.reminder_notification_reply_action_label), getReminderNotificationReplyIntent(context))
				.addRemoteInput(remoteInput)
				.build()
	}

	/**
	 * Android 7.0 (Nougat), API level 24 is required for direct replies to notifications
	 */
	@RequiresApi(Build.VERSION_CODES.N)
	private fun getReminderNotificationReplyIntent(context: Context): PendingIntent
	{
		val intent = Intent(context, NotificationReplyReceiver::class.java)
		intent.action = Companion.REMINDER_NOTIFICATION_REPLY_ACTION
		return PendingIntent.getBroadcast(context, Companion.REQUEST_CODE_REPLY_TO_REMINDER_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT)
	}

	override fun sendReplyConfirmationNotification(context: Context)
	{
		val notification = NotificationCompat.Builder(context, Companion.REMINDERS_NOTIFICATION_CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_heart)
				.setContentTitle(context.getString(R.string.reminder_notification_reply_confirmation_title))
				.setContentText(context.getString(R.string.reminder_notification_reply_confirmation_text))
				.build()

		NotificationManagerCompat.from(context).notify(Companion.REMINDER_NOTIFICATION_ID, notification)
	}
}